package net.kopeph.ld35;

public class Level {
	private static final Game game = Game.game;

	public Level(String filename) {
		String[] lines = game.loadStrings(filename);

		//debug: print lines
		for (String line : lines)
			System.out.println('"' + line + '"');

		//TODO
	}
}
