/**
 * Package for @author Kamal Jahah of Robot Arena
 */
package uk.ac.reading.rg014522.kamaljahah.Robots;

import java.io.Serializable;

/**
 * @author Kamal Jahah
 *
 *         Game Robot extending from abstract class of Robot, implementing
 *         Serializable to save and load from .ser files. Will include most
 *         methods from Robot unless have been overwritten.
 */
public class GameRobot extends Robot implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Empty constructor
	 */
	public GameRobot() {

	}

	/**
	 * Creates a normal game Robot. Constuctor takes arguments of x,y,rads,angle and
	 * speed.
	 * 
	 * @param ix
	 * @param iy
	 * @param ir
	 * @param ia
	 * @param is
	 */
	public GameRobot(double ix, double iy, double ir, double ia, double is) {
		// Calls from super class
		super(ix, iy, ir);
		setrAngle(ia);
		setrSpeed(1);
		// Sets the rotation duration when rotation. (In milliseconds).
		setRotationDuration(1000);
		// Sets the hypothetical wheelSize so can be added into the hitbox calculation.
		setWheelSize(8);
	}

	/**
	 * Calls check robot from abstract Robot class.
	 * 
	 * @param RobotArena
	 */
	protected void checkRobot(RobotArena R) {
		super.checkRobot(R);
	}

	/**
	 * Draws the wheels of the game robot. Wont be used here. Implemented in
	 * abstract class for testing.
	 * 
	 * @param RobotInterface
	 *            -- Contains the actual drawing of the robot wheels.
	 */
	protected void drawWheels(RobotInterface robotInterface) {
		robotInterface.robWheels(getX(), getY(), getRad(), this);
	}

	/**
	 * Draws the robot with the parts. Clears previous robot parts so doesn't draw a
	 * long line when robot is moving.
	 * 
	 * @param RobotInterface
	 */
	protected void drawRobot(RobotInterface Ri) {
		getRobotParts().getChildren().clear();
		Ri.showRobot(getX(), getY(), getRad(), getCol());
		Ri.robWheels(getX(), getY(), getRad(), this);
	}

	/**
	 * @return String of type of robot
	 */
	protected String getStrType() {
		return "Game Robot";
	}

	/**
	 * calculates the hitboxradius of each seperate robot. Override annotation used
	 * because each robot is different.
	 * 
	 * @return rad + wheelSize + (1) so Robot does not clip through wall
	 */

	@Override
	protected double calculateHitboxRadius() {
		return getRad() + getWheelSize() + 1;
	}

}
