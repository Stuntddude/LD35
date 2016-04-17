package net.kopeph.ld35.entity;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class SvgObject extends Entity {
	private static final float SCALE = 100;

	private final PShape shape;
	private final float ax, ay, ahx, ahy, arad;
	private final float bx, by, bhx, bhy, brad;
//	private final boolean rot;

	public SvgObject(String line) {
		String[] parts = line.split(":");
		shape = game.loadShape(parts[0]);
		String[] numbers = parts[1].split(",");

		float aleft    = Float.parseFloat(numbers[0])/SCALE;
		float atop     = Float.parseFloat(numbers[1])/SCALE;
		float awidth   = Float.parseFloat(numbers[2])/SCALE;
		float aheight  = Float.parseFloat(numbers[3])/SCALE;
		float adegrees = Float.parseFloat(numbers[4]);

		float bleft    = Float.parseFloat(numbers[5])/SCALE;
		float btop     = Float.parseFloat(numbers[6])/SCALE;
		float bwidth   = Float.parseFloat(numbers[7])/SCALE;
		float bheight  = Float.parseFloat(numbers[8])/SCALE;
		float bdegrees = Float.parseFloat(numbers[9]);

//		boolean rotating = Boolean.parseBoolean(numbers[11]);

		ahx  = awidth/2;
		ahy  = aheight/2;
		ax   = aleft + ahx;
		ay   = atop  + ahy;
		arad = PApplet.radians(adegrees);

		bhx  = bwidth/2;
		bhy  = bheight/2;
		bx   = bleft + bhx;
		by   = btop  + bhy;
		brad = PApplet.radians(bdegrees);

//		rot = rotating;

		//load vertices
		String[] coords = game.loadStrings(parts[0] + ".txt");
		Vec2[] vertices = new Vec2[coords.length];
		for (int i = 0; i < coords.length; ++i) {
			String[] pair = coords[i].split(",");
			vertices[i] = new Vec2((Float.parseFloat(pair[0]) - 0.5f)*ahx*2,
			                       (Float.parseFloat(pair[1]) - 0.5f)*ahy*2);
		}

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.KINEMATIC;
		bodyDef.position.set(ax, ay);
		bodyDef.angle = arad;

		PolygonShape polygon = new PolygonShape();
		polygon.set(vertices, vertices.length);

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
		game.pushMatrix();
		game.shapeMode(PConstants.CENTER);
		game.translate(ax, ay);
		game.rotate(arad);
		game.shape(shape, 0, 0, ahx*2, ahy*2);
		game.popMatrix();
	}
}
