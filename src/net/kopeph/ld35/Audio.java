package net.kopeph.ld35;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

//TODO: make this work with the layered stems keenan sent me
public final class Audio {
	private final Minim minim = new Minim(Game.game);

	AudioPlayer music;
	float beatInterval; //in seconds

	public void playMusic(String filename) {
		music = minim.loadFile(filename);
		music.loop();
	}

	//supposedly, calling this before program exit in PApplet.stop() is important
	//but if the program is going to shut down anyway, who knows?
	public void close() {
		if (music != null)
			music.close();
		minim.stop();
	}
}
