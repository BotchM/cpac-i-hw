/* CPAC I, Evan Korth
 * Assignment #4
 * 15 Oct 12
 * Victor (Ben) Turner, vt520@nyu.edu, N15271750

 * Non-object-oriented arrayed version of creating 10 cars and moving them around on a 2D grid.
 
 * TODO: Sanitize and error-check input.
*/

import java.util.Random;
import java.util.Scanner;

class CarArray {
	// CONSTANTS - SIZE OF ENVIRONMENT
	static final int LIMIT_MIN_X = 1;
	static final int LIMIT_MAX_X = 20;
	static final int LIMIT_MIN_Y = 1;
	static final int LIMIT_MAX_Y = 20;
	static final int NUM_CARS = 10;
	
	// --------------------------------------------------- BEGIN - RANDOMIZE FUNCTIONS
	public static int getRandomPos(int min, int max) {
		Random randNum = new Random();
		return randNum.nextInt(max - min + 1) + min;
	}
	
	// Returns a char that symbolizes the car's color.
	public static char getRandomColor() {
		Random randNum = new Random();
		int min = 0, max = 4;
		int colorNum = randNum.nextInt(max - min + 1) + min;
		char carColor;
		
		switch (colorNum) {
			case 0:
				carColor = 'R';
				break;
			case 1:
				carColor = 'G';
				break;
			case 2:
				carColor = 'B';
				break;
			case 3:
				carColor = 'W';
				break;
			case 4:
				carColor = 'S';
				break;
			default:
				carColor = 'W';
		}
		
		return carColor;
	}
	// --------------------------------------------------- END - RANDOMIZE FUNCTIONS
	
	// Does error-checking on user-entered value, returns new value if okay to move, old value if not.
	public static int moveX(boolean ignition, int oldPos, int mvUnits) {
		int newPos = oldPos + mvUnits;
		if (ignition == false) { // car is off
			System.out.println("Your car is not running. You must turn the ignition on first!");
			return oldPos;
		}
		else {
			if (newPos < LIMIT_MIN_X || newPos > LIMIT_MAX_Y) { // out of bounds
				System.out.println("You cannot drive beyond the limits of the grid. Try again!");
				return oldPos;
			}
			else {
				return newPos;				
			}
		}
	}
	
	// Does error-checking on user-entered value, returns new value if okay to move, old value if not.
	// TODO: Error-check if mvUnits is an int.
	public static int moveY(boolean ignition, int oldPos, int mvUnits) {
		int newPos = oldPos + mvUnits;
		if (ignition == false) {
			System.out.println("Your car is not running. You must turn the ignition on first!");
			return oldPos;
		}
		else {
			if (newPos < LIMIT_MIN_Y || newPos > LIMIT_MAX_Y) {
				System.out.println("You cannot drive beyond the limits of the grid. Try again!");
				return oldPos;
			}
			else {
				return newPos;
			}
		}
	}
	
	// Fetches user input (int only).  Returns 1 int.
	public static int processCmd() {
		Scanner scanner = new Scanner(System.in);
		return scanner.nextInt();
	}
	
	// If ignition is false (off), return its opposite value. If true, returns false.
	public static boolean toggleIgnition(boolean ignition) {
		return !ignition;
	}
	
	// Prints out world grid & NUM_CARS cars' locations & colors.
	// Most of below messiness is formatting.
	public static void drawGrid(int[] x, int[] y, char[] carColor) {
		// TODO: What if 2+ cars on same spot?
		boolean found = false;
		
		String output = "  "; // x-axis key padding
		for (int j=LIMIT_MIN_X; j<=LIMIT_MAX_X; j++) {
			if (j < 10) output += "  "; // left-zero padding
			else output += " ";
			output += j;
		}
		output += "\n";
		
		for (int i=LIMIT_MIN_Y; i<=LIMIT_MAX_Y; i++) { // y-axis rows
			if (i < 10) output += " "; // left-zero padding
			output += i + " ";
			
			for (int j=LIMIT_MIN_X; j<=LIMIT_MAX_X; j++) {
				found = false;
				for (int k=0; k<NUM_CARS; k++) {
					if (j == x[k] && i == y[k]) {
						found = true;
						output += readableCarNum(k); // need to adjust from computer array size to human-readable 
						output += carColor[k]; // Prints out car's location, represented by its color char.
						output += " ";
						break; // Breaks out of checking all NUM_CARS' locations to just print one empty char " - ".
					}
				}
				if (found == false) {
					output += " - ";
				}
			}
			
			output += "\n";
		}
		System.out.println(output); // prints grid
	}
	
	public static void displayCarNumPrompt() {
		System.out.print("Which car would you like to use? (1 through " + NUM_CARS + "): ");
	}
	
	/**
	 * Tweak so that car array entry #0 (say, car[0]) is seen as "1" to a human. 
	 * @param carNum : should be 0 to NUM_CARS-1
	 * @return human readable number (e.g. 1 for car[0])
	 */
	public static int readableCarNum(int carNum) {
		return carNum + 1;
	}
	
	public static void displayPrompt(int[] x, int[] y, boolean[] ignition, char[] carColor, int carNum) {
		String ignitionWord = "";
		
		drawGrid(x, y, carColor);
		System.out.println("CAR INFORMATION -- Car #" + readableCarNum(carNum));
		System.out.println("Color: " + carColor[carNum]);
		System.out.println("Location: (" + x[carNum] + ", " + y[carNum] + ")");
		if (ignition[carNum] == true) { // converting true/false to on/off
			ignitionWord = "on";
		}
		else {
			ignitionWord = "off";
		}
		System.out.println("Ignition: " + ignitionWord);
		System.out.println("What would you like to do next? (1 - change ignition; 2 - change position of car; 3 - change car; 4 - quit this program)");
	}
	
	public static void main(String args[]) {
		boolean appRunning = true;
		int intIn, direction, carNum = 0;
		boolean[] ignition = new boolean[NUM_CARS];
		int[] x = new int[NUM_CARS];
		int[] y = new int[NUM_CARS];
		char[] carColor = new char[NUM_CARS];
		
		// init all cars
		for (int i=0; i < NUM_CARS; i++) {
			x[i] = getRandomPos(LIMIT_MIN_X, LIMIT_MAX_X);
			y[i] = getRandomPos(LIMIT_MIN_Y, LIMIT_MAX_Y);
			ignition[i] = false;
			carColor[i] = getRandomColor();
		}
		
		// Ask user which car to start with.
		displayCarNumPrompt();
		carNum = processCmd() - 1;
		
		// DEBUGGING
//		System.out.println(x[carNum]);
//		System.out.println(y[carNum]);
//		System.out.println(ignition[carNum]);
//		System.out.println(carColor[carNum]);
//		System.out.println(carNum);
		
		while (appRunning == true) {
			displayPrompt(x, y, ignition, carColor, carNum);
			intIn = processCmd(); // toggle ignition, move, change car, exit?

			switch (intIn) {
				case 1:
					System.out.println("Car #" + readableCarNum(carNum) + "'s ignition has been changed.");
					ignition[carNum] = toggleIgnition(ignition[carNum]);
					break;
				case 2:
					System.out.println("What direction would you like to move car #" + readableCarNum(carNum) + "? (1 - horizontal; 2 - vertical)");
					direction = processCmd();
					if (direction == 1) {
						System.out.println("How far? (positive value to move right, negative value to move left)");
						x[carNum] = moveX(ignition[carNum], x[carNum], processCmd());
					}
					else if (direction == 2) {
						System.out.println("How far? (positive value to move down, negative value to move up)");
						y[carNum] = moveY(ignition[carNum], y[carNum], processCmd());
					}
					else {
						System.out.println("Not a valid command!");
					}
					break;
				case 3:
					System.out.println("Which car (1 through " + NUM_CARS + ") would you like to drive?");
					carNum = processCmd() - 1; // tweak it back down to computer array terms
					break;
				case 4:
					appRunning = false;
					System.exit(0);
				default:
					System.out.println("Not a valid command!");
			}
		}

	}
}

