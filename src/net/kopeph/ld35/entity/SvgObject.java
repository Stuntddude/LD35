package net.kopeph.ld35.entity;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;

public class SvgObject extends Entity {
	private static final float SCALE = 100;

	private final PShape shape;
	private final float ax, ay, ahx, ahy, arad;
	private final float bx, by, bhx, bhy, brad;
	private final boolean rot;
	private final int bar1, bar2, bars;
	private final Vec2[] points;

	//???????
	private Fixture currentFixture;
	private float x, y, hx, hy, rad;

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

		boolean rotating = Boolean.parseBoolean(numbers[10]);

		bar1 = Integer.parseInt(numbers[11]);
		bar2 = Integer.parseInt(numbers[12]);
		bars = Integer.parseInt(numbers[13]);

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

		rot = rotating;

		//load unscaled vertices for later use
		String[] coords = game.loadStrings(parts[0] + ".txt");
		points = new Vec2[coords.length];
		for (int i = 0; i < coords.length; ++i) {
			String[] pair = coords[i].split(",");
			points[i] = new Vec2(Float.parseFloat(pair[0]),
			                     Float.parseFloat(pair[1]));
		}

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.KINEMATIC;
		bodyDef.position.set(ax, ay);
		bodyDef.angle = arad;


		FixtureDef fixture = getFixtureDef(ax, ay, ahx, ahy, arad);

		body = game.world.createBody(bodyDef);
		currentFixture = body.createFixture(fixture);
	}

	private FixtureDef getFixtureDef(float _x, float _y, float _hx, float _hy, float _rad) {
		//update current transform properties (for rendering)
		x = _x;
		y = _y;
		hx = _hx;
		hy = _hy;
		rad = _rad;

		//create a set of scaled coordinates
		Vec2[] vertices = new Vec2[points.length];
		for (int i = 0; i < points.length; ++i)
			vertices[i] = new Vec2((points[i].x - 0.5f)*hx*2,
			                       (points[i].y - 0.5f)*hy*2);

		//create shape
		PolygonShape polygon = new PolygonShape();
		polygon.set(vertices, vertices.length);

		//create fixture
		FixtureDef fixture = new FixtureDef();
		fixture.shape = polygon;
		fixture.density = 0.5f;
		fixture.friction = 1.0f;
		fixture.restitution = 0.0f;

		return fixture;
	}

	private int lastBeat = -1;

	@Override
	public void draw() {
		float time = game.elapsedNanos/1e9f;
		int rawBeat = PApplet.round(time/game.beatInterval);
		int beat = rawBeat % bars;

		float diff = time/game.beatInterval - rawBeat;
		float f = PApplet.norm(diff, -0.5f, 0.0f); //normalized lerp factor for transformation

		//if we are in the middle of a transition
		if (f > 0.0f && f < 1.0f && (beat == bar1 || beat == bar2)) {
			//I don't know if I have to replace the shape or the whole fixture
			//so I'm playing it "safe" and replacing the whole fixture
			FixtureDef fixture;

			if (beat == bar1) {
				fixture = getFixtureDef(PApplet.lerp(ax,   bx,   f),
				                        PApplet.lerp(ay,   by,   f),
				                        PApplet.lerp(ahx,  bhx,  f),
				                        PApplet.lerp(ahy,  bhy,  f),
				                        PApplet.lerp(arad, brad, f));

			} else {
				fixture = getFixtureDef(PApplet.lerp(bx,   ax,   f),
				                        PApplet.lerp(by,   ay,   f),
				                        PApplet.lerp(bhx,  ahx,  f),
				                        PApplet.lerp(bhy,  ahy,  f),
				                        PApplet.lerp(brad, arad, f));
			}

			//replace current fixture with new one
			body.destroyFixture(currentFixture);
			currentFixture = body.createFixture(fixture);

			//move body to its new location
			body.setTransform(new Vec2(x, y), rad);
		}

		lastBeat = beat;

		game.pushMatrix();
		game.shapeMode(PConstants.CENTER);
		game.translate(x, y);
		game.rotate(rad);
		game.shape(shape, 0, 0, hx*2, hy*2);
		game.popMatrix();
	}
}
