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

import net.kopeph.ld35.util.Lists;

public class SvgObject extends Entity {
	private static final float SCALE = 100;

	private final PShape shape;
	private final float ax, ay, ahx, ahy, arad;
	private final float bx, by, bhx, bhy, brad;
	private final int bar1, bar2, bars;
	private final Vec2[] points;

	private final boolean rotates, moves, scales, solid, dingbat;

	private Fixture currentFixture;
	private float hx, hy;

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

		rotates = Boolean.parseBoolean(numbers[10]);
		moves = aleft != bleft || atop != btop || adegrees != bdegrees;
		scales = awidth != bwidth || aheight != bheight;
		solid = Lists.contains(Lists.solids, parts[0]);
		dingbat = Lists.contains(Lists.dingbats, parts[0]);

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
		if (bar1 == 0)
			bodyDef.position.set(bx, by);
		else
			bodyDef.position.set(ax, ay);
		bodyDef.angle = bar1 == 0? brad : arad;
		bodyDef.angularVelocity = rotates? game.random(-1.0f, 1.0f) : 0.0f;

		FixtureDef fixture = getFixtureDef(ahx, ahy);

		fixture.isSensor = !solid;

		body = game.world.createBody(bodyDef);
		currentFixture = body.createFixture(fixture);
	}

	private FixtureDef getFixtureDef(float _hx, float _hy) {
		hx = _hx;
		hy = _hy;

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
		fixture.density = 0.3f;
		fixture.friction = moves? 10.0f : 0.5f; //like a lot
		fixture.restitution = 0.0f;

		return fixture;
	}

	private boolean movedLastFrame;

	//TODO: add behavior for coming to rest at exact end points
	//is that already covered? I'm not sure
	public void move() {
		if (!moves)
			return; //no need

		float time = game.elapsedNanos/1e9f;
		int rawBeat = PApplet.ceil(time/game.beatInterval);
		int beat = rawBeat % bars;

		float diff = time/game.beatInterval - rawBeat;
		float f = PApplet.norm(diff, -1.0f, 0.0f); //normalized lerp factor for transformation

		//if we are in the middle of a transition
		if (f > 0.0f && f < 1.0f && (beat == bar1 || beat == bar2)) {
			if (scales) {
				//I don't know if I have to replace the shape or the whole fixture
				//so I'm playing it "safe" and replacing the whole fixture
				FixtureDef fixture;

				if (beat == bar1) {
					fixture = getFixtureDef(PApplet.lerp(bhx,  ahx,  f),
					                        PApplet.lerp(bhy,  ahy,  f));
				} else {
					fixture = getFixtureDef(PApplet.lerp(ahx,  bhx,  f),
					                        PApplet.lerp(ahy,  bhy,  f));
				}

				//replace current fixture with new one
				body.destroyFixture(currentFixture);
				currentFixture = body.createFixture(fixture);
			}

			//if we're just beginning to move,
			//set the velocity of the object so it will reach its destination in time
			if (!movedLastFrame && moves) {
				//calculate delta to the desired final position
				float dt = rawBeat*game.beatInterval - time; //in seconds
				float dx = beat == bar1? bx - ax : ax - bx;
				float dy = beat == bar1? by - ay : ay - by;
				float drad = beat == bar1? brad - arad : arad - brad;

				body.setLinearVelocity(new Vec2(dx/dt, dy/dt)); //...I think?
				body.setAngularVelocity(drad/dt);

				movedLastFrame = true;
			}
		} else if (movedLastFrame && moves) {
			//here is where we stop the object's movement
			body.setLinearVelocity(new Vec2(0, 0));
			body.setAngularVelocity(0);
			currentFixture.setFriction(0.5f); //dynamic friction may be broken now, I'm not sure

			//as long as I am using round() or ceil() to find the beat, this *should* work
			if (beat - 1 == bar1 || beat == bar1)
				body.setTransform(new Vec2(bx, by), brad);
			else if (beat - 1 == bar2 || beat == bar2)
				body.setTransform(new Vec2(ax, ay), arad);

			movedLastFrame = false;
		}
	}

	@Override
	public void draw() {
		game.pushMatrix();
		game.shapeMode(PConstants.CENTER);
		game.translate(body.getPosition().x, body.getPosition().y);
		game.rotate(body.getAngle());
		//TODO: figure out how to draw the lines for the dingbats
		game.shape(shape, 0, 0, hx*2, hy*2);
		game.popMatrix();
	}
}
