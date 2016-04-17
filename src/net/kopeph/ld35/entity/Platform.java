package net.kopeph.ld35.entity;

import processing.core.PConstants;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

/** for testing purposes */
public class Platform extends Entity {
	private final float hx, hy;

	/**
	 * @param hx half of the width
	 * @param hy half of the height
	 */
	public Platform(float x, float y, float hx, float hy) {
		this.hx = hx;
		this.hy = hy;

		BodyDef platDef = new BodyDef();
		platDef.type = BodyType.STATIC;
		platDef.position.set(x, y);

		PolygonShape platShape = new PolygonShape();
		platShape.setAsBox(hx, hy);

		FixtureDef platFixture = new FixtureDef();
		platFixture.shape = platShape;
		platFixture.density = 0.5f;
		platFixture.friction = 1.0f;
		platFixture.restitution = 0.0f;

		body = game.world.createBody(platDef);
		body.createFixture(platFixture);
	}

	@Override
	public void draw() {
		Vec2 position = body.getPosition();
		game.fill(0xFF000000); //white
		game.rectMode(PConstants.RADIUS);
		game.rect(position.x, position.y, hx, hy);
	}
}
