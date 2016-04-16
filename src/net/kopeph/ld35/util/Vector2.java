package net.kopeph.ld35.util;

import java.util.Arrays;

/** @author alexg */
public final class Vector2  {
	public float x;
	public float y;

	/** constructs the <0, 0> vector */
	public Vector2() {
		this(0, 0);
	}

	/** Copy constructor */
	public Vector2(Vector2 other) {
		this(other.x, other.y);
	}

	/** constructs an <x, y> vector */
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/** constructs an <x, y> vector */
	public Vector2(double x, double y) {
		this.x = (float)x;
		this.y = (float)y;
	}

	/**
	 * Constructs a vector based polar coordinates.
	 * @param m magnitude, or length
	 * @param t theta, or angle (in radians)
	 */
	public static Vector2 polar(double m, double t) {
		return new Vector2(m * Math.cos(t), m * Math.sin(t));
	}

	/** @return the magnitude or length of this vector */
	public float mag() {
		return (float)Math.sqrt(x * x + y * y);
	}

	/** @return the angle of this vector */
	public float theta() {
		return (float)Math.atan2(y, x);
	}

	/**
	 * Adds this and the supplied vector together.
	 * @return the resultant vector
	 */
	public Vector2 add(Vector2 rhs) {
		return new Vector2(x + rhs.x, y + rhs.y);
	}

	/**
	 * Adds this and the supplied vector together, storing the result in this.
	 * @return this
	 */
	public Vector2 addEquals(Vector2 rhs) {
		x += rhs.x;
		y += rhs.y;
		return this;
	}

	/**
	 * Adds this and the supplied vector together.
	 * @return the resultant vector
	 */
	public Vector2 add(float x, float y) {
		return new Vector2(this.x + x, this.y + y);
	}

	/**
	 * Adds this and the supplied vector together, storing the result in this.
	 * @return this
	 */
	public Vector2 addEquals(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}

	/**
	 * Subtracts the supplied vector from this
	 * @return the resultant vector
	 */
	public Vector2 sub(Vector2 rhs) {
		return new Vector2(x - rhs.x, y - rhs.y);
	}

	/**
	 * Subtracts the supplied vector from this, storing the result in this.
	 * @return this
	 */
	public Vector2 subEquals(Vector2 rhs) {
		x -= rhs.x;
		y -= rhs.y;
		return this;
	}

	/**
	 * Subtracts the supplied vector from this
	 * @return the resultant vector
	 */
	public Vector2 sub(float x, float y) {
		return new Vector2(this.x - x, this.y - y);
	}

	/**
	 * Subtracts the supplied vector from this, storing the result in this.
	 * @return this
	 */
	public Vector2 subEquals(float x, float y) {
		this.x -= x;
		this.y -= y;
		return this;
	}

	@Override
	public boolean equals(Object rhs) {
		if (this == rhs) return true;
		if (rhs.getClass() != this.getClass()) return false;
		return equals((Vector2) rhs);
	}

	/** See: equals(Object) */
	public boolean equals(Vector2 rhs) {
		return x == rhs.x && y == rhs.y;
	}

	@Override
	public int hashCode() {
		return Arrays.asList(x, y).hashCode();
	}

	/**
	 * Scales this vector by the supplied scalar.
	 * @return the resultant vector
	 */
	public Vector2 mul(float scalar) {
		return new Vector2(scalar * x, scalar * y);
	}

	/**
	 * Scales this vector by the supplied scalar, storing the result in this
	 * @return this
	 */
	public Vector2 mulEquals(float scalar) {
		x *= scalar;
		y *= scalar;
		return this;
	}

	/**
	 * Performs the dot product of this and the supplied vector.
	 * @return the resultant scalar
	 */
	public float dotMul(Vector2 rhs) {
		return x * rhs.x + y * rhs.y;
	}

	/**
	 * Performs the dot product of this and the supplied vector.
	 * @return the resultant scalar
	 */
	public float dotMul(float x, float y) {
		return this.x * x + this.y * y;
	}

	/**
	 * Performs the cross product of this and the supplied vector.
	 * @return the resultant scalar
	 */
	public float crossMul(Vector2 rhs) {
		return x * rhs.y - y * rhs.x;
	}

	/**
	 * Performs the cross product of this and the supplied vector.
	 * @return the resultant scalar
	 */
	public float crossMul(float x, float y) {
		return this.x * y - this.y * x;
	}

	/**
	 * Multiplies the vector components together.
	 * @return the resultant scalar
	 */
	public float compMul() {
		return x * y;
	}

	/**
	 * Multiplies this vector with the supplied vector by their components.
	 * @return the resultant scalar
	 */
	public Vector2 compMul(Vector2 rhs) {
		return new Vector2(x * rhs.x, y * rhs.y);
	}

	/**
	 * Multiplies this vector with the supplied vector by their components, storing the result in this.
	 * @return this
	 */
	public Vector2 compMulEquals(Vector2 rhs) {
		x *= rhs.x;
		y *= rhs.y;
		return this;
	}

	/**
	 * Multiplies this vector with the supplied vector by their components.
	 * @return the resultant scalar
	 */
	public Vector2 compMul(float x, float y) {
		return new Vector2(this.x * x, this.y * y);
	}

	/**
	 * Multiplies this vector with the supplied vector by their components, storing the result in this.
	 * @return this
	 */
	public Vector2 compMulEquals(float x, float y) {
		this.x *= x;
		this.y *= y;
		return this;
	}

	/**
	 * Adjusts the length of the vector (without changing the angle) such that it is 1 unit long.
	 * @return the resultant vector
	 */
	public Vector2 normalize() {
		float m = mag();
		if (m != 0)
			return new Vector2(x / m, y / m);
		return new Vector2(0, 0);
	}

	/**
	 * Adjusts the length of the vector (without changing the angle) such that it is 1 unit long, storing the result in this.
	 * @return this
	 */
	public Vector2 normalizeEquals() {
		float m = mag();
		if (m != 0) {
			x /= m;
			y /= m;
		}
		return this;
	}

	/**
	 * Equivalent to <code>this.sub(other).theta()</code>.
	 * @return the angle from this vector to the supplied vector
	 */
	public float thetaTo(Vector2 other) {
		return (float)Math.atan2(y - other.y, x - other.x);
	}

	/**
	 * Equivalent to <code>this.sub(x, y).theta()</code>.
	 * @return the angle from this vector to the supplied vector
	 */
	public float thetaTo(float x, float y) {
		return (float)Math.atan2(this.y - y, this.x - x);
	}

	/**
	 * Equivalent to <code>this.sub(other).mag()</code>.
	 * @return the distance from this vector to the supplied vector
	 */
	public float distanceTo(Vector2 other) {
		float dx = x - other.x;
		float dy = y - other.y;
		return (float)Math.sqrt(dx * dx + dy * dy);
	}

	/**
	 * Equivalent to <code>this.sub(x, y).mag()</code>.
	 * @return the distance from this vector to the supplied vector
	 */
	public float distanceTo(float x, float y) {
		float dx = this.x - x;
		float dy = this.y - y;
		return (float)Math.sqrt(dx * dx + dy * dy);
	}

	@Override
	public String toString() {
		return String.format("<%.6f, %.6f>", x, y); //$NON-NLS-1$
	}
}
