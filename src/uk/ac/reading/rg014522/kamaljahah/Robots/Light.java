package uk.ac.reading.rg014522.kamaljahah.Robots;

import java.io.Serializable;

/**
 * 
 * @author Kamal Jahah
 * Light which is also an extension of Robot so that it can use the same functions that are being used in the Robot class.
 */
public class Light extends Robot implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Empty Constructor
	 */
	public Light() {
		
	}

	/**
	 * Light constructor
	 * @param ix
	 * @param iy
	 * @param ir
	 */
	public Light(double ix, double iy, double ir) {
		super(ix,iy,ir);
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
	 * Draws the Light
	 * 
	 * @param RobotInterface
	 */
	protected void drawRobot(RobotInterface Ri) {
		Ri.light(getX(), getY(), getRad());
	}
	

	/**
	 * @return String of type of robot
	 */
	protected String getStrType() {
		return "Light";
	}

	/**
	 * Hitbot radius for the light
	 */
	@Override
	protected double calculateHitboxRadius() {
		return getRad();
	}
}

	
