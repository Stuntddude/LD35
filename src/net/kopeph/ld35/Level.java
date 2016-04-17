package net.kopeph.ld35;

import net.kopeph.ld35.entity.SvgObject;

public class Level {
	private static final Game game = Game.game;

	public Level(String filename) {
		String[] lines = game.loadStrings(filename);
		SvgObject[] svg = new SvgObject[lines.length];
		
		//debug: print lines
		for (int i = 0; i < lines.length; i++){
			System.out.println('"' + lines[i] + '"');
			svg[i] = new SvgObject(lines[i]);
		}

		//TODO
	}
}
