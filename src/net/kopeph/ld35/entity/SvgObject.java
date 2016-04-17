package net.kopeph.ld35.entity;
import processing.core.PConstants;
import processing.core.PShape;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class SvgObject extends Entity {
	private static final float SCALE = 100;

	private PShape shape;
	private float x, y, hx, hy;

	public SvgObject(String line) {
		String[] parts = line.split(":");
		shape = game.loadShape(parts[0]);
		String[] numbers = parts[1].split(",");
		float left = Float.parseFloat(numbers[0])/SCALE;
		float top = Float.parseFloat(numbers[1])/SCALE;
		float width = Float.parseFloat(numbers[2])/SCALE;
		float height = Float.parseFloat(numbers[3])/SCALE;
		hx = width/2;
		hy = height/2;
		x = left + hx;
		y = top + hy;

		//load vertices
		String[] coords = game.loadStrings(parts[0] + ".txt");
		Vec2[] vertices = new Vec2[coords.length];
		for (int i = 0; i < coords.length; ++i) {
			String[] pair = coords[i].split(",");
			vertices[i] = new Vec2((Float.parseFloat(pair[0]) - 0.5f)*hx*2,
			                       (Float.parseFloat(pair[1]) - 0.5f)*hy*2);
		}

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.KINEMATIC;
		bodyDef.position.set(x, y);

		PolygonShape polygon = new PolygonShape();
		polygon.set(vertices, vertices.length); //vertices.length);

		FixtureDef fixture = new FixtureDef();
		fixture.shape = polygon;
		fixture.density = 0.5f;
		fixture.friction = 1.0f;
		fixture.restitution = 0.0f;

		body = game.world.createBody(bodyDef);
		body.createFixture(fixture);
	}

	@Override
	public void draw() {
		game.shapeMode(PConstants.CENTER);
		game.shape(shape, x, y, hx*2, hy*2);
	}
}
