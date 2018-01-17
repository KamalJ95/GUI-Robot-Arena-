package uk.ac.reading.rg014522.kamaljahah.Robots;

import java.io.Serializable;

public class lightBot extends Robot implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Empty constructor
	 */
	public lightBot() {

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
	public lightBot(double ix, double iy, double ir, double ia, double is) {
		// Calls from super class
		super(ix, iy, ir);
		setrAngle(ia);
		setrSpeed(1);
		// Sets the rotation duration when rotation. (In milliseconds).
		setRotationDuration(1000);
		// Sets the hypothetical wheelSize so can be added into the hitbox calculation.
		setWheelSize(6);
	}

	/**
	 * Calls check robot from abstract Robot class.
	 * 
	 * @param RobotArena
	 */
	protected void checkRobot(RobotArena R) {
		if(R.checkLightHit(this)) {
			setrSpeed(0);
		}
		super.checkRobot(R);
	}
	
	/**
	 * Draws the robot with the parts. Clears previous robot parts so doesn't draw a
	 * long line when robot is moving.
	 * 
	 * @param RobotInterface
	 */
	protected void drawRobot(RobotInterface Ri) {
		getRobotParts().getChildren().clear();
		Ri.lightBot(getX(), getY(), getRad(), getCol());
		Ri.lightWheels(getX(), getY(), getRad(), this);
	}

	/**
	 * @return String of type of robot
	 */
	protected String getStrType() {
		return "Light Robot";
	}


	@Override
	protected double calculateHitboxRadius() {
		// TODO Auto-generated method stub
		return getRad() + 6;
	}

}
