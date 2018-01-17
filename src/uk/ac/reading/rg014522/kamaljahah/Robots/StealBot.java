package uk.ac.reading.rg014522.kamaljahah.Robots;

import java.io.Serializable;


/**
 * @author Kamal Jahah
 *
 *         Steal Robot extending from abstract class of Robot, implementing
 *         Serializable to save and load from .ser files. Will include most
 *         methods from Robot unless have been overwritten.
 */
public class StealBot extends Robot implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// String that will have "Steal" written instead of the integer of points like
	// on a Points bot.
	private String steal;

	/**
	 * Empty constructor
	 */
	public StealBot() {
	}

	/**
	 * Creates a Steal Robot that will remove points from the points robot.
	 * Constuctor takes arguments of x,y,rads,angle and speed.
	 * 
	 * @param ix
	 * @param iy
	 * @param ir
	 * @param ia
	 * @param is
	 */
	public StealBot(double ix, double iy, double ir, double ia, double is) {
		// Calls from super class
		super(ix, iy, ir);
		setrAngle(ia);
		setrSpeed(1);

		// Sets the rotation to 1000 milliseconds.
		setRotationDuration(1000);

		// Sets the wheel size to 11.
		setWheelSize(11);
	}

	/**
	 * Calls the super class check Robot method.
	 * 
	 * @param RobotArena
	 */
	protected void checkRobot(RobotArena R) {
		super.checkRobot(R);
	}

	/**
	 * Draws the Steal Robot, calls methods from the RobotInterface
	 * 
	 * @param RobotInterface
	 */
	protected void drawRobot(RobotInterface Ri) {
		getRobotParts().getChildren().clear();
		Ri.stealBot(getX(), getY(), getRad(), getCol());
		Ri.stealWheels(getX(), getY(), getRad(), this);
		Ri.showSteal(getX(), getY(), steal);
	}


	/**
	 * @return String calling Steal Bot
	 */
	protected String getStrType() {
		return "Steal Bot";
	}

	/**
	 * @return rad + wheelSize for calculating the hitbox radius.
	 */
	@Override
	protected double calculateHitboxRadius() {
		return getRad() + getWheelSize() + 2;
	}

}
