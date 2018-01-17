package uk.ac.reading.rg014522.kamaljahah.Robots;

import java.io.Serializable;


/**
 * @author Kamal Jahah
 *
 *         Whisker Robot extending from abstract class of Robot, implementing
 *         Serializable to save and load from .ser files.
 * 
 *         Line class provided by RJM was not implemented, and so a Hitbox
 *         system [Square around the whiskers] was attempted to fix this. Not
 *         100% working, but attempted and works sometimes.
 */
public class WhiskerBot extends Robot implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Whisker Robot constructor
	 */
	public WhiskerBot() {

	}

	/**
	 * Whisker Robot constructor, taking ix, iy, ir, ia and is as arguments
	 * @param ix x val
	 * @param iy y val
	 * @param ir rad val
	 * @param ia angle val
	 * @param is speed val
	 */
	public WhiskerBot(double ix, double iy, double ir, double ia, double is) {
		// Super class called
		super(ix, iy, ir);
		setrAngle(ia);
		setrSpeed(1);

		// Sets the rotation duration during robots rotation in milliseconds to 1000.
		setRotationDuration(1000);

		// Sets the wheelSize to 20 (Hitbox)
		setWheelSize(20);

	}

	/**
	 * Draws the Whisker Robot with: Whiskers, the Circle, the Wheels and the
	 * Whiskers Circles (aesthetics for whiskers).
	 */

	protected void drawRobot(RobotInterface Ri) {
		getRobotParts().getChildren().clear();
		Ri.whiskers(getX(), getY(), getRad(), this);
		Ri.whiskerBot(getX(), getY(), getRad(), getCol());
		Ri.whiskerWheels(getX(), getY(), getRad(), this);
		Ri.whiskerCircles(getX(), getY(), getRad(), this);
	}

	/**
	 * Calls super class check Robot
	 * 
	 * @param Robot
	 *            Arena
	 */
	protected void checkRobot(RobotArena R) {
		super.checkRobot(R);

	}

	/**
	 * @return String defining whisker bot
	 */
	protected String getStrType() {
		return "Whisker Bot";
	}

	/**
	 * Calculates the hitbox radius
	 * 
	 * @return The rad+rad (two circles) + 5 for extra.
	 */
	@Override
	protected double calculateHitboxRadius() {
		return getRad() + 25;
	}

}
