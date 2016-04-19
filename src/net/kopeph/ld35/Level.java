package net.kopeph.ld35;

import net.kopeph.ld35.entity.SvgObject;

public class Level {
	private static final Game game = Game.game;

	public final String filename;

	SvgObject[] objects;

	public final int threshold;

	public Level(String filename, int threshold) {
		this.filename = filename;
		this.threshold = threshold;

		String[] lines = game.loadStrings(filename);
		objects = new SvgObject[lines.length];

		//debug: print lines
		for (int i = 0; i < lines.length; i++){
			System.out.println('"' + lines[i] + '"');
			objects[i] = new SvgObject(lines[i]);
		}
	}

	public void move() {
		for (SvgObject object : objects)
			object.move();
	}

	public void draw() {
		for (SvgObject object : objects)
			object.draw();

		game.stroke(0xFF00FF00);
		game.line(-100, threshold, 100, threshold);
		game.noStroke();
	}
}
