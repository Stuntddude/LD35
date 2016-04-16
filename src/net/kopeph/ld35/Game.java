package net.kopeph.ld35;

import processing.core.PApplet;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

public class Game extends PApplet {
	public static final Game game = new Game();

	private static final World world = new World(new Vec2(0, -9.8f), false);

	private Body playerBody;
	private Body platBody;

	@Override
	public void settings() {
		size(640, 480, P3D);
		smooth(8);
	}

	@Override
	public void setup() {
		frameRate(60);
		noStroke();

		//box4d shittt
		//player
		BodyDef playerDef = new BodyDef();
		playerDef.type = BodyType.DYNAMIC;
		playerDef.position.set(10, 10);

		CircleShape playerShape = new CircleShape();
		playerShape.m_radius = 0.5f;

		FixtureDef playerFixture = new FixtureDef();
		playerFixture.shape = playerShape;
		playerFixture.density = 0.5f;
		playerFixture.friction = 0.3f;
		playerFixture.restitution = 0.5f;

		playerBody = world.createBody(playerDef);
		playerBody.createFixture(playerFixture);

		//test platform
		BodyDef platDef = new BodyDef();
		platDef.type = BodyType.STATIC;
		platDef.position.set(10.5f, 1);

		PolygonShape platShape = new PolygonShape();
		platShape.setAsBox(1.0f, 0.5f);

		FixtureDef platFixture = new FixtureDef();
		platFixture.shape = playerShape;
		platFixture.density = 0.5f;
		platFixture.friction = 0.3f;
		platFixture.restitution = 0.5f;

		platBody = world.createBody(platDef);
		platBody.createFixture(platFixture);
	}

	@Override
	public void draw() {
		float timeStep = 1.0f / 60.0f;
		int velocityIterations = 6;
		int positionIterations = 3;

		world.step(timeStep, velocityIterations, positionIterations);
		Vec2 position = playerBody.getPosition();
		Vec2 plat = platBody.getPosition();

		background(0);
		ellipseMode(CENTER);
		ellipse(10*position.x, 10*position.y, 5, 5);
		rectMode(CENTER);
		rect(10*plat.x, 10*plat.y, 10, 10);
	}

	@Override
	public void keyPressed() {
		//
	}

	@Override
	public void keyReleased() {
		//
	}

	public static void main(String[] args) {
		PApplet.runSketch(new String[] { Game.class.getName() }, game);
	}
}
