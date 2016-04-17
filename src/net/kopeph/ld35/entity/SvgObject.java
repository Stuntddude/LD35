package net.kopeph.ld35.entity;
import processing.core.PShape;

public class SvgObject extends Entity{
	private PShape svg;
	private float x, y, hx, hy;
	public SvgObject(String filename) {
		int len = filename.length();
		int ch = filename.indexOf(":");
		svg = game.loadShape(filename.substring(0, ch));
		System.out.println('"' + filename.substring(0, ch) + '"');
		//x = Float.parseFloat(filename.substring(ch+1, ch = filename.indexOf(",", ch+1)));
		//System.out.println('"' + x + '"');
		System.out.println('"' + filename.substring(ch+1, ch = filename.indexOf(",", ch+1)) + '"');
		//y = Float.parseFloat(filename.substring(ch+1, ch = filename.indexOf(",", ch+1)));
		//System.out.println('"' + y + '"');
		System.out.println('"' + filename.substring(ch+1, ch = filename.indexOf(",", ch+1)) + '"');
		//hx = Float.parseFloat(filename.substring(ch+1, ch = filename.indexOf(",", ch+1)));
		//System.out.println('"' + hx + '"');
		System.out.println('"' + filename.substring(ch+1, ch = filename.indexOf(",", ch+1)) + '"');
		//hy = Float.parseFloat(filename.substring(ch+1));
		//System.out.println('"' + hy + '"');
		System.out.println('"' + filename.substring(ch+1) + '"');
	}
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		game.shape(svg, x, y, hx, hy);
	}
}
