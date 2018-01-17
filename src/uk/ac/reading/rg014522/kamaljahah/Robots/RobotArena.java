/**
 * 
 */
package uk.ac.reading.rg014522.kamaljahah.Robots;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;



/**
 * @author Kamal Jahah
 * 
 *         Class for Arena of Robots. Contains x Size and y Size of the arena,
 *         checking the robots, adjusting the robots, adding robots into the
 *         arena.
 */
public class RobotArena implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// xSize and ySize of the Robot Arena.
	private double xSize, ySize;
	// Arraylist of all of the Robots in the Arena.
	private ArrayList<Robot> allRobots;
	// Array List of all of the Rotating Robots in the arena.
	private ArrayList<Robot> rotatingrobots;
	// Randomly generated numbers.
	private Random rgen = new Random();
	//Robot
	Robot rob;

	/**
	 * Constructs an Arena.
	 */
	protected RobotArena() {
		// Default Size of an Arena
		this(500, 400);
	}

	/**
	 * Robot Arena constructor that constructs an Arena by size xS and xY.
	 * 
	 * @param xS
	 * @param yS
	 */
	protected RobotArena(double xS, double yS) {
		xSize = xS;
		ySize = yS;
		// List of all the Robots, initially empty.
		allRobots = new ArrayList<Robot>();
		// List of all of the Rotating Robots, initially empty.
		rotatingrobots = new ArrayList<Robot>();

	}
	
	
	/**
	 * Clears the Robot Arena
	 */
	protected void clearArena() {
		allRobots.removeAll(allRobots);
		rob.setCounter(0);
	}

	/**
	 * Returns the arena size in the x Direction.
	 * 
	 * @return xSize
	 */
	public double getXSize() {
		return xSize;
	}

	/**
	 * Returns the arena size in the y Direction.
	 * 
	 * @return ySize
	 */
	public double getYSize() {
		return ySize;
	}
	

	/**
	 * Draws all the robots in the arena into the Robot Interface
	 * 
	 * @param Robot
	 *            Interface
	 */
	public void drawArena(RobotInterface Ri) {
		// Draws all the Robots
		for (Robot R : allRobots)
			R.drawRobot(Ri);
	}
	
	/**
	 * 
	 * Checks all of the Robots, checks if needs to change the angle of the moving
	 * Robots.
	 * 
	 */
	public void checkRobots() {
		ArrayList<Robot> botsToCheck = getNonRotatingBots();
		for (Robot R : botsToCheck) {
			// Checks all of the Robots
			R.checkRobot(this);
		}
	}

	/**
	 * Adjusts all of the Robots, rotates the non rotating ones.
	 */
	public void adjustRobots() {
		ArrayList<Robot> botsToAdjust = getNonRotatingBots();
		for (Robot R : botsToAdjust) {
			// Adjusts the Robots
			R.adjustRobot();
		}
	}

	/**
	 * Gets the non rotating robots, method to be called in adjust Robots so that
	 * the non moving bots can be moved.
	 * 
	 * @return
	 */
	private ArrayList<Robot> getNonRotatingBots() {
		ArrayList<Robot> nonRotatingBots = new ArrayList<>(allRobots);
		nonRotatingBots.removeAll(rotatingrobots);
		return nonRotatingBots;
	}

	/**
	 * Updates the Robots rotation. SysOut used to check if robot is/isn't
	 * registered as rotating.
	 * 
	 * @param Robot
	 * @param Boolean
	 *            (isRotating)
	 */
	protected void updateRobotRotating(Robot robot, boolean isRotating) {
		if (isRotating) {
			System.out.println("Robot registered as rotating");
			rotatingrobots.add(robot);
		} else {
			System.out.println("Robot unregistered as rotating");
			rotatingrobots.remove(robot);
		}
	}

	/**
	 * Adds a string describing each robot for all robots.
	 * 
	 * @return ans
	 */
	public ArrayList<String> describeAll() {
		// Sets up an empty Arraylist.
		ArrayList<String> ans = new ArrayList<String>();
		// Adds a string defining each Robot
		for (Robot R : allRobots)
			ans.add(R.toString());
		return ans;
	}

	/**
	 * Checks the angle of a Robot, calls the checkRobotAngle method from Robot. For
	 * all of the Robots, checks to see if hitting another Robot, changes angle if
	 * so.
	 * 
	 * @param Robot
	 *            Used to call robot.checkRobotAngle from Robot class.
	 * @return New angle;
	 */
	public double CheckRobotAngle(Robot robot) {
		// Double angle = robot class' check Robot Angle.
		double ang = robot.checkRobotAngle(xSize, ySize);
		// For all the Robots do this:
		for (Robot R : allRobots) {
			// If the robots ID is not equal to another Robots ID, and it's hitting the
			// x/y/rad, the angle will be:
			if (R.getID() != robot.getID() && R.hitting(robot.getX(), robot.getY(), robot.calculateHitboxRadius())) {
				// New angle if impact between two robots.
				ang = 180 * Math.atan2(robot.getY() - R.getY(), robot.getX() - R.getX()) / Math.PI;
			}
		}
		return ang;
	}

	/**
	 * Checks if the abstract Robot has hit another Robot.
	 * 
	 * @param target
	 *            The target Robot
	 * @return true if hit
	 */
	public boolean checkHit(Robot target) {
		boolean ans = false;
		for (Robot R : allRobots) {
			if (R instanceof GameRobot && R.hitting(target)) { 
				ans = true;
			}
		}
		return ans;
	}

	/**
	 * Checks if a Robot is an instance of a Steal bot and if it is hitting the
	 * Pointsbot target. Method to be used in Pointsbot to take points away.
	 * 
	 * @param pointsBot
	 * @return true if hit
	 */
	public boolean checkPoints(PointsBot pointsBot) {
		boolean ans = false;
		for (Robot R : allRobots) {
			if (R instanceof StealBot && R.hitting(pointsBot)) {
				ans = true;
			}
		}
		return ans;
	}
	
	/**
	 * Checks if a light robot hits a light. If it does, it is attracted to the light and stops.
	 * @param Light
	 * @return true if hit
	 */
	public boolean checkLightHit(lightBot light) {
		boolean ans = false;
		for (Robot R : allRobots) {
			if (R instanceof Light && R.hitting(light)) {
				ans = true;
			}
		}
		return ans;
	}
	

	/**
	 * Adds a Game Robot in selected position.
	 */
	public void addRobot() {
		allRobots.add(new GameRobot(xSize / 2, ySize / 2, 10, 225, 1));
	}

	/**
	 * Adds a Points Robot in selected position.
	 */
	public void addPoints() {
		allRobots.add(new PointsBot(xSize / 3, ySize / 3, 20, 225, 1));
	}

	/**
	 * Adds a Whisker Bot in selected position.
	 */
	public void addWhisker() {
		allRobots.add(new WhiskerBot(xSize / 4, ySize / 2.5, 15, 215, 1));
	}

	/**
	 * Adds an Obstruction in selected position.
	 */
	public void addObstruction() {
		allRobots.add(new Obstacle(rgen.nextInt(360) + 1, rgen.nextInt(360) + 1, 20));
	}

	/**
	 * Adds a Wall Robot in selected position.
	 */
	public void addWallBot() {
		allRobots.add(new WallBot(xSize / 4, ySize / 4, 10, 45, 20));
	}

	/**
	 * Adds a Steal Robot in selected position.
	 */
	public void addStealBot() {
		allRobots.add(new StealBot(xSize / 1.5, ySize / 1.5, 15, 225, 1));
	}
	
	/**
	 * Adds a light robot in selected position.
	 */
	public void lightRobot() {
		allRobots.add(new lightBot(xSize/1.5, ySize/1.5, 15, 225, 1));
	}
	
	/**
	 * Adds a light
	 */
	public void light() {
		allRobots.add(new Light(rgen.nextInt(360) + 1, rgen.nextInt(360) + 1, 20));
	}

}
