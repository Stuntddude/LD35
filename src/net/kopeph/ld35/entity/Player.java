package net.kopeph.ld35.entity;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PConstants;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import net.kopeph.ld35.Game;

public class Player extends Entity {
	private static final float RADIUS = 0.25f;

	public List<LocationInfo> recorded = new ArrayList<>();

	public Player(float x, float y) {
		BodyDef playerDef = new BodyDef();
		playerDef.type = BodyType.DYNAMIC;
//		playerDef.angularDamping = 2.0f;
		playerDef.fixedRotation = true;
		playerDef.position.set(x, y);

		CircleShape playerShape = new CircleShape();
		playerShape.m_radius = RADIUS;

		FixtureDef playerFixture = new FixtureDef();
		playerFixture.shape = playerShape;
		playerFixture.density = 0.5f;
		playerFixture.friction = 0.3f;
		playerFixture.restitution = 0.1f;

		body = Game.world.createBody(playerDef);
		body.createFixture(playerFixture);
	}

	@Override
	public void draw() {
		Vec2 position = body.getPosition();
		game.fill(0xFFFFFFFF); //white
		game.ellipseMode(PConstants.RADIUS);
		game.ellipse(position.x, position.y, RADIUS, RADIUS);

		//debug
		float angle = body.getAngle();
		game.stroke(0xFFFF0000); //red
		game.strokeWeight(0.1f);
		game.line(position.x, position.y, position.x + PApplet.cos(angle)*RADIUS, position.y + PApplet.sin(angle)*RADIUS);
		game.noStroke();

		LocationInfo li = new LocationInfo();
		li.millis = game.millis();
		li.x = position.x;
		li.y = position.y;
		recorded.add(li);
	}

	public static class LocationInfo {
		public long millis;
		public float x, y;

		@Override
		public String toString() {
			return String.format("%.3f: (%.3f, %.3f)", millis / 1000F, x, y);
		}
	}
}
