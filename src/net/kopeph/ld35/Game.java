/*
 * Copyright 2016 Alex Gittemeier
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.kopeph.ld35;

import processing.core.PApplet;

/**
 * This is the main class of the program. draw() is called by processing
 * repeatedly, and it is the program's job to move and paint everything inside
 * this.
 *
 * @author alexg
 */
public class Game extends PApplet {
	//Used for keys and triggers
	public static final boolean
		KEY_DOWN = true,
		KEY_UP = false;

	public static final Game game = new Game();

	@Override
	public void settings() {
		size(640, 480);
	}

	@Override
	public void setup() {
		//
	}

	@Override
	public void draw() {
		//
	}

	@Override
	public void keyPressed() {
		//
	}

	@Override
	public void keyReleased() {
		//
	}

	public static void main(String[] args) {
		PApplet.runSketch(new String[] { Game.class.getName() }, game);
	}
}
