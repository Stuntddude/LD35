package net.kopeph.ld35;

import org.jbox2d.common.Vec2;

import net.kopeph.ld35.entity.Entity;
import net.kopeph.ld35.util.Vector2;

/**
 * This class moves stores the transformation matrix and applies the
 * transformation to processing so that processing functions can take in world
 * coordinates and will render to the correct viewport coordinates.
 * <p>
 * Logically, this is where the transformation is stored: in {@link #scale} and
 * {@link #offset}.
 *
 * @author alexg
 */
public final class ViewTransform {
	private static final Game game = Game.game;

	public static final int INITIAL_SCALE = 50;
	public int scale = INITIAL_SCALE;

	/** Position in world coordinates where the center of the screen is. */
	public Vector2 offset = new Vector2();

	/** Sets up processing so that when it receives coordinates they are
	 * interpreted as world coordinates. To revert to interpreting as
	 * viewport coordinates, call {@link #resetMatrix()}.
	 */
	public void applyMatrix() {
		game.pushMatrix();
		game.translate(game.width / 2, game.height / 2);
		game.scale(scale, -scale);
		game.translate(-offset.x, -offset.y);
	}

	/** undoes the effect of {@link #applyMatrix()} */
	public void resetMatrix() {
		game.popMatrix();
	}

	private static final float MAX_FOLLOW_DISTANCE = 1.0f;
	/**
	 * Move {@link #offset} so that the given entity gradually moves to the center
	 * of the screen.
	 *
	 * @param dt time in seconds since the last call.
	 */
	public void follow(Entity entity, float dt) {
		Vec2 position = entity.body.getPosition();
		Vector2 deltaOffset = new Vector2(position.x, position.y).subEquals(offset);

		if (deltaOffset.x > MAX_FOLLOW_DISTANCE)
			offset.x = position.x - MAX_FOLLOW_DISTANCE;
		if (deltaOffset.y > MAX_FOLLOW_DISTANCE)
			offset.y = position.y - MAX_FOLLOW_DISTANCE;
		if (deltaOffset.x < -MAX_FOLLOW_DISTANCE)
			offset.x = position.x + MAX_FOLLOW_DISTANCE;
		if (deltaOffset.y < -MAX_FOLLOW_DISTANCE)
			offset.y = position.y + MAX_FOLLOW_DISTANCE;
	}

	public void shake(float t, float shk) {

	}
}
