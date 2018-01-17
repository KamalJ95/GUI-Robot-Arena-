/**
 * 
 */
package uk.ac.reading.rg014522.kamaljahah.Robots;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javafx.scene.shape.Rectangle;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * @author Kamal Jahah RobotInterface that contains the drawings of the robots,
 *         the JavaFX, buttons etc.
 */

public class RobotInterface extends Application implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Group node used to rotate every part of a robot together.
	private static SerializableGroup rootGroup;
	// Graphics context for drawing things.
	private GraphicsContext gc;
	// Animation timer used for animation.
	private AnimationTimer timer;
	// Vertical Box for putting information into
	private VBox rtPane;
	// Arena used to call items from the RobotArena class.
	private RobotArena arena;


	 

	/**
	 * Method to show information about the program
	 */
	private void showAbout() {
		// Defines what the box is
		Alert alert = new Alert(AlertType.INFORMATION);
		// Says About
		alert.setTitle("About");
		alert.setHeaderText(null);
		// Gives text
		alert.setContentText("Kamal Jahah -- GUI RobotArena \n"
				+ "GameRobot: Standard roaming robot. Adds points to the points robot. \n"
				+ "PointsBot: Accumalate points or 'charge'. Speed will change at different points.\n"
				+ "StealBot: Will remove points from the Points Bot. \n"
				+ "WallBot: Turns 90 degrees upon touching a wall. \n"
				+ "LightBot: When Lightbot touches a light, it will stick to it.\n"
				+ "WhiskerBot: When whiskers touch anything, the WhiskerBot will run away."
				+ "Press 'File' and then 'Load' to load a default arena.");
		// Shows box and waits for user to close.
		alert.showAndWait();
	}

	/**
	 * Unused method. Just shows the status of the Robots if clicked.
	 * 
	 * @param canvas
	 */
	private void setMouseEvents(Canvas canvas) {
		// When a mouse is pressed
		canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				// Redraws the world
				drawWorld();
				drawStatus();
			}
		});
	}
	
	/**
	 * Sets a new arena with new parameters
	 */
	private void newArena() {
		clearArena();
		int width = -1 , height= -1;
		
		TextInputDialog dialog = new TextInputDialog("X Value");
		dialog.setTitle("X Value for new Arena");
		dialog.setHeaderText("X Value:");
		dialog.setContentText("Please enter your new X value:");

		// Traditional way to get the response value.
		Optional<String> xVal = dialog.showAndWait();
		
		if (xVal.isPresent()){
			System.out.println("x");
		   String inputx = xVal.get();
		   width = Integer.parseInt(inputx);
		}
		
		TextInputDialog dialog2 = new TextInputDialog("Y Value");
		dialog.setTitle("Y Value for new Arena");
		dialog.setHeaderText("Y Value:");
		dialog.setContentText("Please enter your new Y value:");

		// Traditional way to get the response value.
		Optional<String> yVal = dialog2.showAndWait();
		
		if (yVal.isPresent()) {
			System.out.println("Y");
		  String inputy = yVal.get();
		  height =  Integer.parseInt(inputy);
		}
	
		System.out.println("New arena");
		
		arena = new RobotArena(width,height);
		drawWorld();
		Canvas canvas = new Canvas(width,height);
		getRootGroup().getChildren().add(canvas);
		gc = canvas.getGraphicsContext2D();
	}
	
	
/**
 * Clears the arena
 */
	private void clearArena() {
		System.out.println("Cleared");
		gc.clearRect(0, 0, 1000, 1000);
		arena.clearArena();
		rootGroup.getChildren().clear();
		Canvas canvas = new Canvas(400, 500);
		getRootGroup().getChildren().add(canvas);
		gc = canvas.getGraphicsContext2D();
	}
	/**
	 * Sets up the Menu of commands for the GUI, will include file handling such as
	 * Saving and Loading.
	 * 
	 * @return the menu bar
	 */
	private MenuBar setMenu() {
		// Creates the main menu
		MenuBar menuBar = new MenuBar();

		// Adds a File to the Main Menu
		Menu mFile = new Menu("Configuration");

		// Sub Menu has "Exit"
		MenuItem mExit = new MenuItem("Exit");
		mExit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				// Actions to be taken on exit actionevent
				timer.stop();
				System.exit(0);
			}
		});
		// Adds exit to the Menu
		mFile.getItems().addAll(mExit);

		// Sub Menu has "Save" feature
		MenuItem mSave = new Menu("Save");
		mSave.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent s) {
				// Calls the saveArena() Method.
				saveArena();
			}
		});
		// Adds Save to the Menu
		mFile.getItems().addAll(mSave);

		// Sub Menu has "Load" feature
		MenuItem mLoad = new Menu("Load");
		mLoad.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent act) {
				// Calls the openArena() Method.
				openArena();
			}
		});
		// Adds ClearArena to the Menu
		mFile.getItems().addAll(mLoad);
		
		MenuItem clearArena = new Menu("Clear Arena");
		clearArena.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent act) {
				clearArena();
			}
		});
		
		mFile.getItems().addAll(clearArena);
		
		//Adds Specify Arena Size to the arena
		MenuItem newArena = new Menu("Specify Arena Size");
		newArena.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent act) {
				newArena();
			}
		});
		
		mFile.getItems().addAll(newArena);
	

		// Creates a Help Menu option
		Menu mHelp = new Menu("Help");
		// Adds About sub menu item
		MenuItem mAbout = new MenuItem("About");
		mAbout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				// Action to be taken when About selected
				showAbout();
			}
		});
		// Adds about to the Help main item
		mHelp.getItems().addAll(mAbout);

		// Sets the main menu with File and Help.
		menuBar.getMenus().addAll(mFile, mHelp);

		// Returns the menu
		return menuBar;
	}

	/**
	 * Sets up the horizontal box for the bottom of the borderpane with the relevant
	 * buttons for adding different robots into the arena.
	 * 
	 * @return the buttons
	 */
	private HBox setButtons() {
		// Creates a button for starting
		Button btnStart = new Button("Start");
		btnStart.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// Action to start the timer
				timer.start();
			}
		});

		// Creates a button for stopping
		Button btnStop = new Button("Pause");
		btnStop.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// Action to stop the timer
				timer.stop();
			}
		});

		// Creates a button for Game Robot
		Button btnAdd = new Button("Game Robot");
		btnAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// Action to add Game Robot
				arena.addRobot();
				drawWorld();

			}
		});

		// Creates a button for a Points Robot
		Button pointsBot = new Button("Points Bot");
		pointsBot.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// Action to add the Points Robot
				arena.addPoints();
				drawWorld();

			}
		});

		// Creates a button for a Whisker Robot
		Button whiskerBot = new Button("Whisker Bot");
		whiskerBot.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// Action to add the Whisker Robot
				arena.addWhisker();
				drawWorld();

			}
		});

		// Creates a button for an Obstruction (Robot ;))
		Button Obstruction = new Button("Obstacle");
		Obstruction.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// Action to add the Obstruction (BOT! xD)
				arena.addObstruction();
				drawWorld();
			}
		});

		// Creates a button for a Wall Robot
		Button wallBot = new Button("Wall Bot");
		wallBot.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				// Action to add the Wall Robot
				arena.addWallBot();
				drawWorld();
			}
		});

		// Creates a button for a Steal Robot
		Button stealBot = new Button("Steal Bot");
		stealBot.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				// Action to add the Steal Robot
				arena.addStealBot();
				drawWorld();
			}
		});
		
		//Creates a button for Light Robot
		Button lightBot = new Button("Light Robot");
		lightBot.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				//Action to add the light robot
				arena.lightRobot();
				drawWorld();
			}
		});
		
		//Creates a button for a light
		Button light = new Button("Light");
		light.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				//Action to add light
				arena.light();
				drawWorld();
			}
		});

		// Adds these buttons to the bottom of the HBox.
		return new HBox(btnStart, btnStop, btnAdd, pointsBot, whiskerBot, Obstruction, wallBot, stealBot, lightBot,light);
	}

	/**
	 * Shows the Wall Robot in position x,y,rad and colour.
	 * 
	 * @param x
	 * @param y
	 * @param rad
	 * @param col
	 */

	public void wallBot(double x, double y, double rad, char col) {
		//Sets the fill with a Coral colour.
		gc.setFill(Color.CORAL);
		gc.fillArc(x - rad, y - rad, rad * 2, rad * 2, 0, 360, ArcType.ROUND);
	}

	/**
	 * Uses Rectangle (Extension of shape class) to draw the wheels so can be
	 * rotated around the circle.
	 * 
	 * @param x
	 * @param y
	 * @param rad
	 * @param robot
	 */

	public void wallWheels(double x, double y, double rad, Robot robot) {
		//Sets the wheelSize of 6.
		int wheelSize = 6;
		//Where to draw the first wheel in relation to the circle.
		Rectangle wheel1 = new Rectangle(x - rad, y + rad, 2 * rad, wheelSize);
		wheel1.setFill(Color.BLACK);
		//Groups the first wheel into a group.
		robot.getRobotParts().getChildren().add(wheel1);
		//Where to draw the second wheel in relation to the circle.
		Rectangle wheel2 = new Rectangle(x - rad, y - (rad + wheelSize), 2 * rad, wheelSize);
		wheel2.setFill(Color.BLACK);
		//Groups the second wheel into a group.
		robot.getRobotParts().getChildren().add(wheel2);
	}

	/**
	 * Shows the Game Robot in position x,y,rad and colour.
	 * 
	 * @param x
	 * @param y
	 * @param rad
	 * @param col
	 */
	public void showRobot(double x, double y, double rad, char col) {
		//Sets circle colour to yellow.
		gc.setFill(Color.YELLOW); 
		gc.fillArc(x - rad, y - rad, rad * 2, rad * 2, 0, 360, ArcType.ROUND); 

	}

	/**
	 * Uses Rectangle (Extension of shape class) to draw the wheels so can be
	 * rotated around the circle.
	 * 
	 * @param x
	 * @param y
	 * @param rad
	 * @param robot
	 */
	public void robWheels(double x, double y, double rad, Robot robot) {
		//Sets wheelsize to 6.
		int wheelSize = 6;
		//Where to draw the first wheel in relation to the circle.
		Rectangle wheel1 = new Rectangle(x - rad, y + rad, 2 * rad, wheelSize);
		wheel1.setFill(Color.BLACK);
		//Groups the first wheel into a group.
		robot.getRobotParts().getChildren().add(wheel1);
		//Where to draw the second wheel in relation to the circle.
		Rectangle wheel2 = new Rectangle(x - rad, y - (rad + wheelSize), 2 * rad, wheelSize);
		wheel2.setFill(Color.BLACK);
		//Groups the second wheel into a group.
		robot.getRobotParts().getChildren().add(wheel2);
	}
	
	/**
	 * Shows the Points Robot in position x,y,rad and colour.
	 * 
	 * @param x
	 * @param y
	 * @param rad
	 * @param col
	 */
	public void lightBot(double x, double y, double rad, char col) {
		//Sets the fill colour to ChartReuse.
		gc.setFill(Color.LIGHTPINK); 
		gc.fillArc(x - rad, y - rad, rad * 2, rad * 2, 0, 360, ArcType.ROUND); 
	}
	
	/**
	 * Light obstacle
	 */
	public void light(double x, double y, double rad) {
		//sets the fill of the light
		gc.setFill(Color.YELLOW);
		gc.fillArc(x - rad, y - rad, rad * 2, rad * 2, 0, 360, ArcType.ROUND); 
	}

	/**
	 * Uses Rectangle (Extension of shape class) to draw the wheels so can be
	 * rotated around the circle.
	 * 
	 * @param x
	 * @param yF
	 * @param rad
	 * @param robot
	 */
	public void lightWheels(double x, double y, double rad, Robot robot) {
		//Sets the wheelSize to 10.
		int wheelSize = 6;
		//Where to draw the first wheel in relation to the circle.
		Rectangle wheel1 = new Rectangle(x - rad, y + rad, 2 * rad, wheelSize);
		wheel1.setFill(Color.BLACK);
		//Groups the first wheel into a group.
		robot.getRobotParts().getChildren().add(wheel1);
		//Where to draw the second wheel in relation to the circle.
		Rectangle wheel2 = new Rectangle(x - rad, y - (rad + wheelSize), 2 * rad, wheelSize);
		wheel2.setFill(Color.BLACK);
		//Groups the second wheel into a group.
		robot.getRobotParts().getChildren().add(wheel2);
	}

	

	/**
	 * Shows the Points Robot in position x,y,rad and colour.
	 * 
	 * @param x
	 * @param y
	 * @param rad
	 * @param col
	 */
	public void pointsBot(double x, double y, double rad, char col) {
		//Sets the fill colour to ChartReuse.
		gc.setFill(Color.CHARTREUSE); 
		gc.fillArc(x - rad, y - rad, rad * 2, rad * 2, 0, 360, ArcType.ROUND); 
	}

	/**
	 * Uses Rectangle (Extension of shape class) to draw the wheels so can be
	 * rotated around the circle.
	 * 
	 * @param x
	 * @param y
	 * @param rad
	 * @param robot
	 */
	public void pointsWheels(double x, double y, double rad, Robot robot) {
		//Sets the wheelSize to 10.
		int wheelSize = 10;
		//Where to draw the first wheel in relation to the circle.
		Rectangle wheel1 = new Rectangle(x - rad, y + rad, 2 * rad, wheelSize);
		wheel1.setFill(Color.BLACK);
		//Groups the first wheel into a group.
		robot.getRobotParts().getChildren().add(wheel1);
		//Where to draw the second wheel in relation to the circle.
		Rectangle wheel2 = new Rectangle(x - rad, y - (rad + wheelSize), 2 * rad, wheelSize);
		wheel2.setFill(Color.BLACK);
		//Groups the second wheel into a group.
		robot.getRobotParts().getChildren().add(wheel2);
	}

	/**
	 * Shows the Steal Robot in position x,y,rad and colour.
	 * 
	 * @param x
	 * @param y
	 * @param rad
	 * @param col
	 */
	public void stealBot(double x, double y, double rad, char col) {
		//Sets the circle colour to grey.
		gc.setFill(Color.GREY);
		gc.fillArc(x - rad, y - rad, rad * 2, rad * 2, 0, 360, ArcType.ROUND);
	}

	/**
	 * Uses Rectangle (Extension of shape class) to draw the wheels so can be
	 * rotated around the circle.
	 * 
	 * @param x
	 * @param y
	 * @param rad
	 * @param robot
	 */

	public void stealWheels(double x, double y, double rad, Robot robot) {
		//Sets the wheel size to 10.
		int wheelSize = 10;
		//Where to draw the first wheel in relation to the circle.
		Rectangle wheel1 = new Rectangle(x - rad, y + rad, 2 * rad, wheelSize);
		wheel1.setFill(Color.BLACK);
		//Groups the first wheel into a group.
		robot.getRobotParts().getChildren().add(wheel1);
		//Where to draw the second wheel in relation to the circle.
		Rectangle wheel2 = new Rectangle(x - rad, y - (rad + wheelSize), 2 * rad, wheelSize);
		wheel2.setFill(Color.BLACK);
		//Groups the second wheel into a group.
		robot.getRobotParts().getChildren().add(wheel2);
	}

	/**
	 * Shows the Whisker Robot in position x,y,rad and colour.
	 * 
	 * @param x
	 * @param y
	 * @param rad
	 * @param col
	 */
	public void whiskerBot(double x, double y, double rad, char col) {
		//Sets the circle colour to brown.
		gc.setFill(Color.BROWN); 
		gc.fillArc(x - rad, y - rad, rad * 2, rad * 2, 0, 360, ArcType.ROUND);
	}

	/**
	 * Uses Rectangle (Extension of shape class) to draw the wheels so can be
	 * rotated around the circle.
	 * 
	 * @param x
	 * @param y
	 * @param rad
	 * @param robot
	 */
	public void whiskerWheels(double x, double y, double rad, Robot robot) {
		//Sets the wheelsize to 10
		int wheelSize = 10;
		//Where to draw the first wheel in relation to the circle.
		Rectangle wheel1 = new Rectangle(x - rad, y + rad, 2 * rad, wheelSize);
		wheel1.setFill(Color.BLACK);
		//Groups the first wheel into a group.
		robot.getRobotParts().getChildren().add(wheel1);
		//Where to draw the second wheel in relation to the circle.
		Rectangle wheel2 = new Rectangle(x - rad, y - (rad + wheelSize-2), 2 * rad, wheelSize);
		wheel2.setFill(Color.BLACK);
		//Groups the second wheel into a group.
		robot.getRobotParts().getChildren().add(wheel2);
	}

	/**
	 * Javadocs for whiskers. 
	 * @param x
	 * @param y
	 * @param rad
	 * @param robot
	 */
	public void whiskers(double x, double y, double rad, Robot robot) {
		//Sets length of whisker to 20
		int whiskerLength = 20;
		//Sets spreadage (distance between whiskers) to 20.
		int whiskerSpreadage = 20;
		//Draws first whisker in position related to circle
		Line whisker1 = new Line(x - rad, y, x - rad - whiskerLength, y + whiskerSpreadage);
		whisker1.setFill(Color.BLACK);
		//Groups the first whisker.
		robot.getRobotParts().getChildren().add(whisker1);
		//Draws second whisker in position related to circle.
		Line whisker2 = new Line(x - rad, y, x - rad - whiskerLength, y - whiskerSpreadage);
		whisker2.setFill(Color.BLACK);
		//Groups the second whisker.
		robot.getRobotParts().getChildren().add(whisker2);
		//Draws a counter balance whisker behind the Robot so that when rotations occur its square not rectangle
		Line whisker3 = new Line(x + rad, y, x + rad + whiskerLength, y + whiskerSpreadage);
		//Sets it transparent.
		whisker3.setStroke(Color.TRANSPARENT);
		//Groups the third whisker.
		robot.getRobotParts().getChildren().add(whisker3);
	}

	/**
	 * Javadocs for circles on end of whiskers.
	 * @param x
	 * @param y
	 * @param rad
	 * @param robot
	 */
	public void whiskerCircles(double x, double y, double rad, Robot robot) {
		//Sets length of whisker to 20.
		int whiskerLength = 20;
		//Sets spreadage to 20.
		int whiskerSpreadage = 20;
		//Circle on end of whisker, no functionality.
		Circle circle1 = new Circle(x - rad - whiskerLength, y + whiskerSpreadage, 5);
		circle1.setFill(Color.BROWN);
		//Groups circle
		robot.getRobotParts().getChildren().add(circle1);
		//Second circle on end of whisker, no functionality.
		Circle circle2 = new Circle(x - rad - whiskerLength, y - whiskerSpreadage, 5);
		circle2.setFill(Color.BROWN);
		//Groups circle.
		robot.getRobotParts().getChildren().add(circle2);

	}

	/**
	 * Shows the Obstruction in position x,y,rad.
	 * 
	 * @param x
	 * @param y
	 * @param rad
	 */
	public void Obstruction(double x, double y, double rad) {
		//Sets obstruction to a blue colour.
		gc.setFill(Color.DARKBLUE);
		gc.fillRect(x - rad, y - rad + 12, 40, 10);
	}

	/**
	 *Draws the world with Robots inside it.
	 */
	private void drawWorld() {
		gc.setFill(Color.AQUAMARINE); // set aqua colour
		gc.fillRect(0, 0, arena.getXSize(), arena.getYSize()); // clear the arena
		arena.drawArena(this);
	}

	/**
	 * Writes the points of a points bot to selected position in the circle.
	 * @param x
	 * @param y
	 * @param point
	 */

	protected void showPoints(double x, double y, int points) {
		//Sets a horizontal alignment
		gc.setTextAlign(TextAlignment.CENTER); 
		//Vertical
		gc.setTextBaseline(VPos.CENTER);
		//Colour in red
		gc.setFill(Color.RED); 
		//Prints the score to the screen as an integer.
		gc.fillText(Integer.toString(points), x, y); 
	}

	
	/**
	 * Writes "Steal" on a steal bot, who steals points from a points bot.
	 * @param x
	 * @param y
	 * @param string
	 */
	protected void showSteal(double x, double y, String string) {
		//Sets horizontal alighment
		gc.setTextAlign(TextAlignment.CENTER);
		//Vertical
		gc.setTextBaseline(VPos.CENTER);
		//Sets "Steal" to colour red
		gc.setFill(Color.RED); 
		//Prints "Steal" to Robot as text.
		gc.fillText("Steal", x, y);
	}

	/**
	 * Shows where the Robots are in the Arena on the right panel.
	 */
	private
	void drawStatus() {
		//Clears the rtPane.
		rtPane.getChildren().clear(); 
		ArrayList<String> allRs = arena.describeAll();
		for (String s : allRs) {
			//Turns description into a label
			Label l = new Label(s);
			//Adds a label
			rtPane.getChildren().add(l);
		}
	}

	/**
	 * Opening the arena checks if there is a file there to use, if not
	 * then it will go through the errors and then default.
	 * 
	 */
	private void openArena() {
		int ctr = 1; //initialise counter
		try {
			File file = new File("C:\\Users\\Kamal\\Desktop\\Final RobotArena\\stuff1.ser");
			FileInputStream inFile = new FileInputStream(file);
			ObjectInputStream inObj = new ObjectInputStream(inFile);
			this.arena = (RobotArena)inObj.readObject();
			inFile.close();
			inObj.close();
			drawWorld();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			ctr++;
		} catch (IOException e) {
			e.printStackTrace();
			ctr++;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			ctr++;
			System.out.println("This is an invalid file type");
		}		 
		if (ctr != 0){
			System.out.println("File cannot be loaded");
			clearArena();
			arena.addRobot();
			arena.addWhisker();
			arena.addStealBot();
			arena.addPoints();
			drawWorld();

	}}

	/**
	 * Used to save the arena, calls the java file chooser and shows the open
	 * dialog, selects if you save a file or directory then output streams the file
	 * and then calls the output stream on the object which is my object which is my
	 * arena that contains the contents.
	 */
	private void saveArena() {
		// JFileChooser to open up the box
		JFileChooser chooser = new JFileChooser();

		int returnval = chooser.showSaveDialog(null);
		// Approves option and opens chooser
		if (returnval == JFileChooser.APPROVE_OPTION) {
			File selFile = chooser.getSelectedFile();

			System.out.println("You chose to open this file ");
			// Gets the name of the file
			selFile.getName();
			// what i saved
			if (selFile.isFile()) {
				System.out.println("You saved a file");
			} else {
				System.out.println("You saved a directory");
			}

			try {
				// Output the file into the object
				FileOutputStream stream = new FileOutputStream(selFile);
				ObjectOutputStream dataStream = new ObjectOutputStream(stream);
				// writes the object which is myarena
				dataStream.writeObject(arena);
			
				// flushes the output stream
				dataStream.flush();

				dataStream.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Potential method to call somewhere to create a default arena
	 */
	private void startUp(){
		
		int ctr = 1; //initialise counter
		try {
			File file = new File("C:\\Users\\Kamal\\Desktop\\sRobotArena\\stuff2.ser");
			FileInputStream inFile = new FileInputStream(file);
			ObjectInputStream inObj = new ObjectInputStream(inFile);
			this.arena = (RobotArena)inObj.readObject();
			inFile.close();
			inObj.close();
			drawWorld();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			ctr++;
		} catch (IOException e) {
			e.printStackTrace();
			ctr++;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			ctr++;
			System.out.println("This is an invalid file type");
		}		 
		if (ctr != 0){
			System.out.println("File cannot be loaded");
			clearArena();
			arena = new RobotArena(400, 500);
			arena.addRobot();
			arena.addWhisker();
			arena.addStealBot();
			arena.addPoints();
			drawWorld();
		}}
	
	/**
	 * Getter for SerializableGroup
	 * @return rootgroup
	 */
	public static SerializableGroup getRootGroup() {
		return rootGroup;
	}

	/**
	 * Setter for group 
	 * @param rootGroup
	 */
	
	
	public static void setRootGroup(SerializableGroup rootGroup) {
		RobotInterface.rootGroup = rootGroup;
	}


	/**
	 * Primary stage for setting up the JavaFX and the Robot Interface
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Robot Arena");
		BorderPane bp = new BorderPane();
		bp.setPadding(new Insets(10, 20, 10, 20));
		
		//Puts menu at the top of the borderpane.
		bp.setTop(setMenu());

		//Creates a group with a canvas
		setRootGroup(new SerializableGroup()); 
		Canvas canvas = new Canvas(400, 500);
		getRootGroup().getChildren().add(canvas);
		//Loads canvas to the left area.
		bp.setLeft(getRootGroup());

		//Graphics Context for drawing
		gc = canvas.getGraphicsContext2D();

		//Sets the mouse events on the canvas
		setMouseEvents(canvas);
		

		//Sets up the arena.
		arena = new RobotArena(400, 500);
		drawWorld();

		//Sets up the timer
		timer = new AnimationTimer() {
			public void handle(long currentNanoTime) { 
				arena.checkRobots(); 
				arena.adjustRobots();
				drawWorld(); 
				drawStatus(); 
			}
		};
		

		//Sets vertical box on right to list the items.
		rtPane = new VBox(); 
		rtPane.setAlignment(Pos.TOP_LEFT);
		rtPane.setPadding(new Insets(5, 75, 75, 5));
		bp.setRight(rtPane);

		//Sets bottom of borderpane with buttons
		bp.setBottom(setButtons());
		
		
		//Sets the overall scene.
		Scene scene = new Scene(bp, 700, 600); 
		bp.prefHeightProperty().bind(scene.heightProperty());
		bp.prefWidthProperty().bind(scene.widthProperty());

		primaryStage.setScene(scene);
		primaryStage.show();

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Welcome to my Robot Arena!");
		alert.setHeaderText("CS2JA17 -- Robot Arena Coursework");
		alert.setContentText("Produced/Property of Kamal Jahah");

		alert.showAndWait();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Launch the GUI
		Application.launch(args);

	}

}
