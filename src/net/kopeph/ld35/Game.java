package net.kopeph.ld35;

import processing.core.PApplet;
import processing.core.PShape;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import net.kopeph.ld35.entity.Platform;
import net.kopeph.ld35.entity.Player;

public final class Game extends PApplet {
	public static final Game game = new Game();

	public static final World world = new World(new Vec2(0, -9.8f), false);
	public static final ViewTransform transform = new ViewTransform();

	private Player player;
	private Platform[] platforms = new Platform[2];

	private PShape softRect;
	private PShape wonkRect;

	@Override
	public void settings() {
		size(640, 480, P3D);
		smooth(8);
	}

	@Override
	public void setup() {
		frameRate(60);
		noStroke();

		player = new Player(0, 10);
		platforms[0] = new Platform(0, 0, 2.0f, 0.25f);
		platforms[1] = new Platform(8, 0, 4.0f, 0.25f);
	}

	@Override
	public void draw() {
		float timeStep = 1.0f / 60.0f;
		int velocityIterations = 6;
		int positionIterations = 3;

		world.step(timeStep, velocityIterations, positionIterations);

		transform.follow(player, timeStep);
		transform.applyMatrix();
		background(0);
		player.draw();
		platforms[0].draw();
		platforms[1].draw();
		transform.resetMatrix();

		//debug movement
		if (left)
			player.body.applyLinearImpulse(new Vec2(-0.02f, 0), player.body.getPosition());
		if (right)
			player.body.applyLinearImpulse(new Vec2( 0.02f, 0), player.body.getPosition());
	}

	private boolean left, right;

	@Override
	public void keyPressed() {
		if (key == 'a') {
			left = true;
		} else if (key == 'd') {
			right = true;
		} else if (key == 'w') {
			player.body.applyLinearImpulse(new Vec2( 0, 0.6f), player.body.getPosition());
		} else if (key == 'p') {
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
