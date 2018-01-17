package uk.ac.reading.rg014522.kamaljahah.Robots;

import java.io.Serializable;

/**
 * @author Kamal Jahah
 *
 *         Wall Robot extending from abstract class of Robot, implementing
 *         Serializable to save and load from .ser files. Will include most
 *         methods from Robot unless have been overwritten.
 *         Robot that just follows the wall around. Cant implement rectangles turning because
 *         it overrides the checkRobot method.
 */
public class WallBot extends Robot implements Serializable {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Empty Constructor
	 */
	public WallBot() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * WallBot constructor
	 * @param ix
	 * @param iy
	 * @param ir
	 * @param iang
	 * @param isp
	 */
	public WallBot(double ix, double iy, double ir, double iang, double isp) {
	       	//Calls super class 
			super(ix, iy, ir);
	        setrAngle(iang);
	        setRotationDuration(1000);
	        //Keeps the robot at an angle of 90 degrees.
	        setrAngle(90 * Math.floor(getrAngle() / 90));
	        setrSpeed(5);
	}
	
	/**
	 * Draws the WallBot using the methods from the robot interface.
	 */
	protected void drawRobot(RobotInterface Ri) {
        getRobotParts().getChildren().clear();
		Ri.wallBot(getX(), getY(), getRad(), getCol());
		Ri.wallWheels(getX(), getY(), getRad(), this);
	}

	/**
	 * function to check where Robot is, and to turn through 90 degrees when touches the wall (canvas).
	 * @param xSize		max x size of arena
	 * @param ySize		max y size of the arena.
	 * 
	 * Uses the Override annotation so Robot will turn when hits walls. Means that rectangle turning cannot be used.
	 */
	@Override
	protected void checkRobot(RobotArena R) {
		//Turns if Robot Angle is 0 and near a right wall.
		if (getrAngle() < 10 && getX() > R.getXSize() - getRad()*2) {rotateRobot(R,0,90);}
		//Turns if Robot Angle is 90 and near the bottom.
		else if (getrAngle() > 80 & getrAngle() < 100 && getY() > R.getYSize()-getRad()*2) { rotateRobot(R,90,180);}
		//Turns if Robot Angle is 180 and near the left wall.
		else if (getrAngle() > 170 && getrAngle() < 190 && getX() < getRad()*2) { rotateRobot(R,180,270);		}
		//Turns if the Robot Angle is 270 and near the top wall.
		else if (getrAngle() > 260 && getY() < getRad()*2) { rotateRobot(R,270,0);		}
	}
	
	/**
	 * Calculates the hitbox of the wall bots so that if any Robots hit the wall bot as it is
	 * going around the wall they will go off it.
	 */
	@Override
	protected double calculateHitboxRadius() {
		return getRad()+10;
	}
	
	/**
	 * @return String called Wall Bot.
	 */
	protected String getStrType() {
		return "Wall Bot";
	}



}
