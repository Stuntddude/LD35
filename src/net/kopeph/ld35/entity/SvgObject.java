package net.kopeph.ld35.entity;
import processing.core.PConstants;
import processing.core.PShape;

public class SvgObject extends Entity {
	private static final float SCALE = 100;

	private PShape shape;
	private float x, y, hx, hy;

	public SvgObject(String line) {
		String[] parts = line.split(":");
		shape = game.loadShape(parts[0]);
		String[] numbers = parts[1].split(",");
		float left = Float.parseFloat(numbers[0])/SCALE;
		float top = Float.parseFloat(numbers[1])/SCALE;
		float width = Float.parseFloat(numbers[2])/SCALE;
		float height = Float.parseFloat(numbers[3])/SCALE;
		hx = width/2;
		hy = height/2;
		x = left + hx;
		y = top + hy;
	}

	@Override
	public void draw() {
		game.shapeMode(PConstants.CENTER);
		game.shape(shape, x, y, hx*2, hy*2);
	}
}
