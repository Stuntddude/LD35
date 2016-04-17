package net.kopeph.ld35;

import processing.core.PApplet;

import org.jbox2d.common.Settings;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import net.kopeph.ld35.entity.Platform;
import net.kopeph.ld35.entity.Player;

public final class Game extends PApplet {
	public static final Game game = new Game();

	static {
		//increased from 8 to 16 to accomodate more complex convex hulls
		//can and will be decreased again if it becomes a performance bottleneck
		Settings.maxPolygonVertices = 16;
	}

	//the game isn't based on real-world units, so gravity is arbitrary
	public static final World world = new World(new Vec2(0, -15), false);
	public static final ViewTransform transform = new ViewTransform();
	public static final Audio audio = new Audio();

	private Player player;
	private Level level;
	private Platform[] platforms = new Platform[2];

	/** in seconds */
	public float beatInterval;
	public long startNanos;
	public long elapsedNanos;
	public long lastNanos;

	@Override
	public void settings() {
		size(640, 480, P3D);
		smooth(8);
	}

	@Override
	public void setup() {
		frameRate(60);
		noStroke();

		level = new Level("project.txt");

		player = new Player(0, 5);
		platforms[0] = new Platform(0, 0, 2.0f, 0.25f);
		platforms[1] = new Platform(8, 0, 4.0f, 0.25f);

		//load music
//		audio.playMusic("Old Cranberries Test.wav");
		//calculate beat period from beat frequency
		beatInterval = 60.0f/142;

		//start the timer AFTER all initialization, so we are less likely to miss anything
		lastNanos = startNanos = System.nanoTime();
	}

	@Override
	public void draw() {
		long nanos = System.nanoTime();

		float timeStep = (nanos - lastNanos)/1e9f; //in seconds
		elapsedNanos = nanos - startNanos;
		lastNanos = nanos;

		player.move(left, right);

		int velocityIterations = 6;
		int positionIterations = 3;
		world.step(timeStep, velocityIterations, positionIterations);

		transform.follow(player, timeStep);
		transform.applyMatrix();
		background(0xFFFFFFFF); //white
		level.draw();
		platforms[0].draw();
		platforms[1].draw();
		player.draw();
		transform.resetMatrix();
	}

	@Override
	public void stop() {
		//I seriously don't know if this is important?
		audio.close();
		super.stop();
	}

	private boolean left, right;

	@Override
	public void keyPressed() {
		if (key == 'a')
			left = true;
		else if (key == 'd')
			right = true;
		else if (key == 'w' || key == ' ')
			player.jump();
		else if (key == 'p') {
			for (Player.LocationInfo li : player.recorded)
				System.out.println(li);
		}
	}

	@Override
	public void keyReleased() {
		if (key == 'a')
			left = false;
		else if (key == 'd')
			right = false;
	}

	public static void main(String[] args) {
		PApplet.runSketch(new String[] { Game.class.getName() }, game);
	}
}
