package net.kopeph.ld35.util;

/** @immutable */
public final class Rectangle {
	public final float x, y, w, h;

	public Rectangle(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public Rectangle(Vector2 position, Vector2 dimensions) {
		x = position.x;
		y = position.y;
		w = dimensions.x;
		h = dimensions.y;
	}

	public Rectangle move(float dx, float dy) {
		return new Rectangle(x + dx, y + dy, w, h);
	}

	public Rectangle move(Vector2 offset) {
		return new Rectangle(x + offset.x, y + offset.y, w, h);
	}

	public Rectangle grow(float dw, float dh) {
		return new Rectangle(x, y, w + dw, h + dh);
	}

	public Rectangle grow(Vector2 offset) {
		return new Rectangle(x, y, w + offset.x, h + offset.y);
	}

	public Vector2 center() {
		return new Vector2(x + w/2, y + h/2);
	}

	public float top() {
		return x;
	}

	public float left() {
		return y;
	}

	public float right() {
		return x + w;
	}

	public float bottom() {
		return y + h;
	}

	public boolean contains(float x0, float y0) {
		return x0 > x && y0 > y && x0 < x + w && y0 < y + h;
	}

	public boolean contains(Vector2 v) {
		return contains(v.x, v.y);
	}

	public boolean intersects(Rectangle other) {
		return x < other.x + other.w && other.x < x + w &&
		       y < other.y + other.h && other.y < y + h;
	}
}
