package net.kopeph.ld35;

import net.kopeph.ld35.entity.SvgObject;

public class Level {
	private static final Game game = Game.game;

	SvgObject[] objects;

	public Level(String filename) {
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
	}
}
