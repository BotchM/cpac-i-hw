/* CPAC I, Evan Korth
 * Assignment #3
 * 08 Oct 12
 * Victor (Ben) Turner, vt520@nyu.edu, N15271750

 * Non-object-oriented version of creating a car and moving it around on a 2D grid.
 
 * TODO: Sanitize and error-check input.
*/

import java.util.Random;
import java.util.Scanner;

class Car {
	
	// CONSTANTS - SIZE OF ENVIRONMENT
	static final int LIMIT_MIN_X = 1;
	static final int LIMIT_MAX_X = 20;
	static final int LIMIT_MIN_Y = 1;
	static final int LIMIT_MAX_Y = 20;
	
	// BEGIN - RANDOMIZE FUNCTIONS
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
	// END - RANDOMIZE FUNCTIONS
	
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
	
	// If ignition is false (off), return its opposite value.
	public static boolean toggleIgnition(boolean ignition) {
		return !ignition;
	}
	
	// Prints out world grid.
	public static void drawGrid(int x, int y, char carColor) {
		String output = "  "; // x-axis key padding
		for (int j=LIMIT_MIN_X; j<=LIMIT_MAX_X; j++) {
			if (j < 10) output += " "; // left-zero padding
			output += j;
		}
		output += "\n";
		for (int i=LIMIT_MIN_Y; i<=LIMIT_MAX_Y; i++) {
			if (i < 10) output += " "; // left-zero padding
			output += i + " ";
			
			for (int j=LIMIT_MIN_X; j<=LIMIT_MAX_X; j++) {
				if (j == x && i == y) output += carColor + " "; // Prints out car's location, represented by its color char.
				else output += "- ";
			}
			
			output += "\n";
		}
		System.out.println(output); // prints grid
	}
	
	public static void displayPrompt(int x, int y, boolean ignition, char carColor) {
		String ignitionWord = "";
		
		drawGrid(x, y, carColor);
		System.out.println("CAR INFORMATION");
		System.out.println("Color: " + carColor);
		System.out.println("Location: (" + x + ", " + y + ")");
		if (ignition == true) { // converting true/false to on/off
			ignitionWord = "on";
		}
		else {
			ignitionWord = "off";
		}
		System.out.println("Ignition: " + ignitionWord);
		System.out.println("What would you like to do next? (1 - change ignition; 2 - change position of car; 3 - quit this program)");
	}
	
	public static void main(String args[]) {
		boolean appRunning = true;
		boolean ignition = false;
		int x = getRandomPos(LIMIT_MIN_X, LIMIT_MAX_X);
		int y = getRandomPos(LIMIT_MIN_Y, LIMIT_MAX_Y);
		int intIn, direction;
		
		char carColor = getRandomColor();
	
		while (appRunning == true) {
			displayPrompt(x, y, ignition, carColor);
			intIn = processCmd(); // toggle ignition, move, exit?

			switch (intIn) {
				case 1:
					ignition = toggleIgnition(ignition);
					break;
				case 2:
					System.out.println("What direction would you like to move the car? (1 - horizontal; 2 - vertical)");
					direction = processCmd();
					if (direction == 1) {
						System.out.println("How far? (positive value to move right, negative value to move left)");
						x = moveX(ignition, x, processCmd());
					}
					else if (direction == 2) {
						System.out.println("How far? (positive value to move down, negative value to move up)");
						y = moveY(ignition, y, processCmd());
					}
					else {
						System.out.println("Not a valid command!");
					}
					break;
				case 3:
					System.exit(0);
				default:
					System.out.println("Not a valid command!");
			}
		}

	}
}

