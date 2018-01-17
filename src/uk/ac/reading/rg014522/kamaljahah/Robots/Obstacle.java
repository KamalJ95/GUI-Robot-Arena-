package uk.ac.reading.rg014522.kamaljahah.Robots;

import java.io.Serializable;


/**
 * 
 * @author Kamal Jahah
 *
 *	Obstruction extending from Robot class. This is not a Robot, but extending from Robot class helped me to implement a hitbox because hitting
 *	methods were already implemented.
 */
public class Obstacle extends Robot implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public Obstacle() {
		
	}
	
	public Obstacle(double ix, double iy, double ir) {
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
	 * Draws the obstacle
	 * 
	 * @param RobotInterface
	 */
	protected void drawRobot(RobotInterface Ri) {
		Ri.Obstruction(getX(), getY(), getRad());
	}
	

	/**
	 * @return String of type of robot
	 */
	protected String getStrType() {
		return "Obstacle";
	}
	
	
	/**
	 * Calculate hitbox radius for obstacle
	 */
	@Override
	protected double calculateHitboxRadius() {
		return getRad() - 3;
	}
}

	
