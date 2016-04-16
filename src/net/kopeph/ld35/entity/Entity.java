package net.kopeph.ld35.entity;

import org.jbox2d.dynamics.Body;

import net.kopeph.ld35.Game;

abstract public class Entity {
	protected static final Game game = Game.game;

	public Body body;

	abstract public void draw();
}
