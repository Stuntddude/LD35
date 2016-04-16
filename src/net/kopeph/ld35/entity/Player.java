package net.kopeph.ld35.entity;

import processing.core.PApplet;
import processing.core.PConstants;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.contacts.Contact;

public class Player extends Entity {
	private static final float RADIUS = 0.25f;
	private static final float MAX_VEL = 4.0f;

	//if >0, the player is standing on a surface, and therefore can jump
	private int sensorCollisions;

	public Player(float x, float y) {
		//define physics body
		BodyDef playerDef = new BodyDef();
		playerDef.type = BodyType.DYNAMIC;
		playerDef.fixedRotation = true;
		playerDef.position.set(x, y);

		//create main (collision) fixture
		CircleShape playerShape = new CircleShape();
		playerShape.m_radius = RADIUS;

		FixtureDef playerFixture = new FixtureDef();
		playerFixture.shape = playerShape;
		playerFixture.density = 0.5f;
		playerFixture.friction = 0.4f;
		playerFixture.restitution = 0.01f;

		//create a sensor fixture at the bottom of the player
		//used to detect whether the player is currently grounded, for sick jumpz
		PolygonShape sensorShape = new PolygonShape();
		sensorShape.setAsBox(RADIUS/4, RADIUS/8, new Vec2(0, -RADIUS), 0);

		FixtureDef sensorFixture = new FixtureDef();
		sensorFixture.shape = sensorShape;
		sensorFixture.isSensor = true;

		//make!
		body = game.world.createBody(playerDef);
		body.createFixture(playerFixture);
		body.createFixture(sensorFixture);

		//collision handler for sensor
		game.world.setContactListener(new ContactListener() {
			@Override
			public void beginContact(Contact contact) {
				sensorCollisions++;
			}

			@Override
			public void endContact(Contact contact) {
				sensorCollisions--;
			}

			@Override
			public void preSolve(Contact contact, Manifold manifold) {
				//no-op
			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				//no-op
			}
		});
	}

	public void move(boolean left, boolean right) {
		if (left && !right && body.getLinearVelocity().x > -MAX_VEL)
			body.applyForce(new Vec2(-2.5f, 0), body.getPosition());
		else if (right && body.getLinearVelocity().x < MAX_VEL)
			body.applyForce(new Vec2( 2.5f, 0), body.getPosition());
	}

	public void jump() {
		if (sensorCollisions > 0) //if player is on ground
			body.setLinearVelocity(new Vec2(body.getLinearVelocity().x, 6.0f));
	}

	@Override
	public void draw() {
		Vec2 position = body.getPosition();
		game.fill(0xFFFFFFFF); //white
		game.ellipseMode(PConstants.RADIUS);
		game.ellipse(position.x, position.y, RADIUS, RADIUS);

		//debug angle
		float angle = body.getAngle();
		game.stroke(0xFFFF0000); //red
		game.strokeWeight(0.1f);
		game.line(position.x, position.y, position.x + PApplet.cos(angle)*RADIUS, position.y + PApplet.sin(angle)*RADIUS);
		game.noStroke();

		//debug sensor
		game.fill(0xAA7F7FFF); //transparent light blue
		game.rectMode(PConstants.RADIUS);
		game.rect(position.x, position.y - RADIUS, RADIUS/4, RADIUS/8);
	}
}
