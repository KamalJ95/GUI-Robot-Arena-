/**
 * 
 */
package uk.ac.reading.rg014522.kamaljahah.Robots;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.io.Serializable;

/**
 * @author Kamal Jahah
 *
 *         Abstract Robot class that will lead to other Robots being produced
 *         from this class. Implements serializable to save and load from a .ser
 *         file.
 */
public abstract class Robot implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Position and Size of robot.
	private double x, y, rad;
	// Protected static integer to give each robot a unique identifier.
	private static int RobotCounter = 0;
	// Unique Identifier for Robot
	private int RobotID;
	// Sets colour of robots.
	private char col;
	// Angle and the Speed of the travel
	private double rAngle, rSpeed;
	// Time in milliseconds taken for robot to rotate 360 degrees.
	private int rotationDuration;
	// All parts of the robot expect the circles.
	private SerializableGroup robotParts;
	// WheelSize of the robots.
	private double wheelSize;

	/**
	 * Robot constructor.
	 */
	public Robot() {
		this(100, 100, 10);
	}

	/**
	 * construct a Robot of radius ir at ix,iy
	 * 
	 * @param ix
	 * @param iy
	 * @param ir
	 */
	public Robot(double ix, double iy, double ir) {
		x = ix;
		y = iy;
		rad = ir;
		RobotID = RobotCounter++;

		// Constructs a group.
		setRobotParts(new SerializableGroup());

		// Adds the robot parts into each Robot.
		RobotInterface.getRootGroup().getChildren().add(getRobotParts());
	}
	
	public void clearParts() {
		RobotInterface.getRootGroup().getChildren().remove(getRobotParts());
	}

	/**
	 * @return x position
	 */
	public double getX() {
		return x;
	}

	/**
	 * @return y position
	 */
	public double getY() {
		return y;
	}

	/**
	 * @return radius of a Robot
	 */
	public double getRad() {
		return rad;
	}

	/**
	 * @return ID of a Robot
	 */
	public int getID() {
		return RobotID;
	}

	/**
	 * @return col of Robot
	 */
	public char getCol() {
		return col;
	}

	/**
	 * @return robot angle
	 */
	public double getrAngle() {
		return rAngle;
	}

	/**
	 * @param rAngle
	 * @return robot angle
	 */
	public void setrAngle(double rAngle) {
		this.rAngle = rAngle;
	}

	/**
	 * @return robot speed
	 */
	public double getrSpeed() {
		return rSpeed;
	}

	/**
	 * @param rSpeed
	 * @return robot speed
	 */
	public void setrSpeed(double rSpeed) {
		this.rSpeed = rSpeed;
	}
	
	/**
	 * @param RobotId
	 * @return robot id
	 */
	public void setrID(int RobotID) {
		this.RobotID = RobotID;
	}
	/**
	 * Robotcounter settser
	 */
	
	public static int setCounter(int robotcounter) {
		return Robot.RobotCounter = robotcounter;
	}
	/**
	 * @return rotation duration
	 */
	public int getRotationDuration() {
		return rotationDuration;
	}

	/**
	 * @param rotationDuration
	 * @return robots rotation
	 */
	public void setRotationDuration(int rotationDuration) {
		this.rotationDuration = rotationDuration;
	}

	/**
	 * @return Robot parts
	 */
	public SerializableGroup getRobotParts() {
		return robotParts;
	}

	/**
	 * @param Robot
	 *            Parts
	 * @return robot parts
	 */
	public void setRobotParts(SerializableGroup robotParts) {
		this.robotParts = robotParts;
	}

	/**
	 * @return robot wheel size
	 */
	public double getWheelSize() {
		return wheelSize;
	}

	/**
	 * @param wheelSize
	 * @return Robot wheel size
	 */
	public void setWheelSize(double wheelSize) {
		this.wheelSize = wheelSize;
	}

	/**
	 * Draws a Robot into the interface.
	 * 
	 * @param RobotInterface
	 */
	protected void drawRobot(RobotInterface Ri) {
		Ri.showRobot(x, y, rad, getCol());
		Ri.robWheels(x, y, rad, this);
	}

	/**
	 * @return String containing "Robot"
	 */
	protected String getStrType() {
		return "Robot";
	}

	/**
	 * return String describing Robot and position.
	 */
	public String toString() {
		return getStrType() + " ID: " + RobotID + " at X: " + Math.round(x) + ", " + " Y: " + +Math.round(y);
	}

	/**
	 * checkRobot - Change Angle of Robot, rotates the robot. Used for rotation
	 * transitions.
	 * 
	 * @param Robot
	 *            Arena
	 */
	protected void checkRobot(RobotArena R) {
		double newAngle = R.CheckRobotAngle(this);
		// If the newAngle is not equal to the old Angle, rotates the robot to a
		// newAngle.
		if (newAngle != getrAngle()) {
			rotateRobot(R, getrAngle(), newAngle);
		}
	}
	

	/**
	 * Adjusts the robot depending on the speed and the angle it is travelling in.
	 */

	protected void adjustRobot() {
		// Puts the angle into radians.
		double radAngle = getrAngle() * Math.PI / 180;
		// New X position.
		x += getrSpeed() * Math.cos(radAngle);
		// New Y position.
		y += getrSpeed() * Math.sin(radAngle);
	}

	/**
	 * Rotates the rectangles on the Robot.
	 * 
	 * @param robotArena
	 * @param prevAngle
	 * @param newAngle
	 */
	protected void rotateRobot(RobotArena robotArena,double prevAngle,double newAngle) {
		prevAngle = rAngle;
		// Checks if Robot is rotating.
		robotArena.updateRobotRotating(this, true);
		// Animation Duration will be the duration of the rotation (milliseconds).
		double animationDuration = getRotationDuration();
		// Needs to be fixed. Set as newAngle for now. Possibly calculate using
		// prevAngle and newAngle
		double rotationAngleInDegrees = newAngle - prevAngle ;
		// Rotation constructor taking the time and the robot parts (wheels) into
		// consideration.
		RotateTransition rt = new RotateTransition(Duration.millis(animationDuration), getRobotParts());
		// Sets the end angle of the rotation to the rotation Angle in degrees.
		rt.setByAngle(rotationAngleInDegrees);
		// Sets whats to be executed when rotation transition has finished.
		rt.setOnFinished(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				// Test to check if the action has been completed.
				System.out.println("done");
				// Sets rAngle to newAngle.
				setrAngle(newAngle);
				robotArena.updateRobotRotating(Robot.this, false);
				adjustRobot();
			}
		});
		// Plays the rotation transition.
		rt.play();
	}

	/**
	 * Checks the Robot angle if hitting against the top of the canvas or against
	 * the side of the canvas.
	 * 
	 * @param arenaWidth
	 * @param arenaHeight
	 * @return newAngle
	 */
	protected double checkRobotAngle(double arenaWidth, double arenaHeight) {
		// Make the newAngle equal the robot Angle.
		double newAngle = getrAngle();
		// HitboxRadius calls the calculateHitBoxRadius() method.
		double hitBoxRadius = calculateHitboxRadius();
		// If the x + the hitboxRadius is greater or equal to the arena width, or x -the
		// hitboxradius is less than or equal to 0 will mirror the angle.
		if (x + hitBoxRadius >= arenaWidth || x - hitBoxRadius <= 0) {
			newAngle = 180 - getrAngle();
		}
		// If tries to go off the top or bottom, will mirror the angle.
		if (y + hitBoxRadius >= arenaHeight || y - hitBoxRadius <= 0) {
			newAngle = -getrAngle();
		}
		// Returns the new Angle.
		return newAngle;
	}

	/**
	 * Abstract method for calculating the hitbox radius. Will be different for each
	 * type of robot.
	 * 
	 * @return
	 */
	protected abstract double calculateHitboxRadius();

	/**
	 * Checks if the robot is hitting the canvas.
	 * 
	 * @param ox
	 * @param oy
	 * @param or
	 * @return true if hitting
	 */
	public boolean hitting(double ox, double oy, double or) {
		return (ox - x) * (ox - x) + (oy - y) * (oy - y) < (or + calculateHitboxRadius())
				* (or + calculateHitboxRadius());
	}

	/**
	 * Check if Robot is hitting another Robot
	 * 
	 * @param oRobot
	 *            - the other Robot Uses the hitboxradius of other robot rather than
	 *            the rads.
	 * @return true if hitting
	 */
	public boolean hitting(Robot oRobot) {
		return hitting(oRobot.getX(), oRobot.getY(), oRobot.calculateHitboxRadius());
	}
	
}
