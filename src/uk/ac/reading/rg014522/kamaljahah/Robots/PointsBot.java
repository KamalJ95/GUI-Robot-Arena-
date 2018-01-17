package uk.ac.reading.rg014522.kamaljahah.Robots;

import java.io.Serializable;

import javax.swing.JOptionPane;

/**
 * @author Kamal Jahah
 *
 *         Points Robot extending from abstract class of Robot, implementing
 *         Serializable to save and load from .ser files. Will include most
 *         methods from Robot unless have been overwritten. Points Robot will
 *         increment points when a game robot touches it.
 */
public class PointsBot extends Robot implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Points for the points robot.
	private int points;

	/**
	 * Empty pointsbot constructor
	 */
	public PointsBot() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Creates a normal Points Robot. Constuctor takes arguments of x,y,rads,angle
	 * and speed.
	 * 
	 * @param ix
	 * @param iy
	 * @param ir
	 * @param ia
	 * @param is
	 */
	public PointsBot(double ix, double iy, double ir, double ia, double is) {
		// Calls from super class
		super(ix, iy, ir);
		setrAngle(ia);
		setrSpeed(1);

		// Sets original points to 5.
		points = 5;

		// Sets the rotation duration in milliseconds to 1000.
		setRotationDuration(1000);

		// WheelSize of 11. Used for calculating hitboxradius.
		setWheelSize(11);
	}

	/**
	 * Checks the hits against other Robots:
	 * 
	 * If hits from a game robot, points will increase. If hits from a steal robot,
	 * points will decrease. If points hit 100, Points bots will win. If points hit
	 * 0 due to Steal bots, steal bots will win. Calls checkRobot from super class.
	 */
	protected void checkRobot(RobotArena R) {
		// If hits game robots, points increase.
		if (R.checkHit(this)) {
			points++;
		}

		// If hits steal bot, points decrease.
		if (R.checkPoints(this)) {
			points--;
		}
		
		// If points are 3, will slow down the robot due to charge?
		if (points == 3) {
			setrSpeed(0.5);
		}
		
		//If points at 7, robot will speed up. Potentially making it easier to get more points? But also risky.
	//	if (points == 7) {
		//	setrSpeed(10);
	//	}

		// If points are equal to 100, a points bot will win.
		if (points == 10) {
			JOptionPane.showMessageDialog(null, "A Point Bot has won the game!");
			System.exit(-1);
		}

		// If points are less than or equal to 0, steal bot will win.
		// < 0 for testing purposes. Make sure that points cannot go less than zero.
		if (points <= 0) {
			JOptionPane.showMessageDialog(null, "A Steal Bot has stolen all of a Points Bots points!");
			JOptionPane.showMessageDialog(null, "Steal Bots win!");
			// System exit used to exit out of game.
			System.exit(-1);
		}
		super.checkRobot(R);
	}

	/**
	 * Draws the Points bot, calling methods from the Robot Interface.
	 */
	protected void drawRobot(RobotInterface Ri) {
		getRobotParts().getChildren().clear();
		Ri.pointsBot(getX(), getY(), getRad(), getCol());
		Ri.pointsWheels(getX(), getY(), getRad(), this);
		Ri.showPoints(getX(), getY(), points);
	}

	/**
	 * @return Points bot string.
	 */
	protected String getStrType() {
		return "Points Bot";
	}

	/**
	 * Calculates the hitbox radius.
	 * 
	 * @return The radius of the robot plus the wheel size.
	 */
	@Override
	protected double calculateHitboxRadius() {
		return getRad() + getWheelSize() + 10;
	}



}