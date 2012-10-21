/* CPAC I, Evan Korth
 * Assignment #5
 * 22 Oct 12
 * Victor (Ben) Turner, vt520@nyu.edu, N15271750

 * Object-oriented arrayed version of creating 10 cars and moving them around on a 2D grid.
 
*/

import java.util.InputMismatchException;
import java.util.Scanner;
	
public class CarClassAssignment {

	// ---- CONSTANTS - SIZE OF ENVIRONMENT
	static final int LIMIT_MIN_X = 1;
	static final int LIMIT_MAX_X = 20;
	static final int LIMIT_MIN_Y = 1;
	static final int LIMIT_MAX_Y = 20;
	static final int NUM_CARS = 10;
	// ---- END CONSTANTS
	
	static int carNum; // global for error management
	static CarClass[] car = new CarClass[NUM_CARS];
	
	public static void main(String args[]) {
		boolean appRunning = true;
		int intIn, direction;
		carNum = 0;
		initCars(); // init each car in car array
		
		// Ask user which car to start with.
		displayCarNumPrompt();
		carNum = processCmd() - 1;
		
		while (appRunning == true) {
			displayPrompt(car, carNum);
			intIn = processCmd(); // toggle ignition, move, change car, exit?

			switch (intIn) {
				case 1:
					System.out.println("Car #" + readableCarNum(carNum) + "'s ignition has been changed.");
					car[carNum].toggleIgnition();
					break;
				case 2:
					System.out.println("What direction would you like to move car #" + readableCarNum(carNum) + "? (1 - horizontal; 2 - vertical)");
					direction = processCmd();
					if (direction == 1) {
						System.out.println("How far? (positive value to move right, negative value to move left)");
						car[carNum].moveX(processCmd());
					}
					else if (direction == 2) {
						System.out.println("How far? (positive value to move down, negative value to move up)");
						car[carNum].moveY(processCmd());
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
	
	// Fetches user input (int only). Returns an int if no error. If error, asks again.
	public static int processCmd() {
		Scanner scanner = new Scanner(System.in);
		try {
			return scanner.nextInt();
		}
		catch (InputMismatchException e) {
			System.out.println("Invalid input. Try again.");
			return processCmd();
		}
	}
	
	// Prints out world grid & NUM_CARS cars' locations & colors.
	// Most of below messiness is formatting.
	public static void drawGrid(CarClass[] car) {
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
					if (j == car[k].getX() && i == car[k].getY()) {
						found = true;
						output += readableCarNum(k); // need to adjust from computer array size to human-readable 
						output += car[k].getCarColor(); // Prints out car's location, represented by its color char.
						output += " "; // TODO: Keeps spacing in check but if 2 cars on same row, messes up spacing.
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
	
	public static void displayPrompt(CarClass[] car, int carNum) {
		String ignitionWord = "";
		
		drawGrid(car);
		System.out.println("CAR INFORMATION -- Car #" + readableCarNum(carNum));
		System.out.println("Color: " + car[carNum].getCarColor());
		System.out.println("Location: (" + car[carNum].getX() + ", " + car[carNum].getY() + ")");
		if (car[carNum].getIgnition() == true) { // converting true/false to on/off
			ignitionWord = "on";
		}
		else {
			ignitionWord = "off";
		}
		System.out.println("Ignition: " + ignitionWord);
		System.out.println("What would you like to do next? (1 - change ignition; 2 - change position of car; 3 - change car; 4 - quit this program)");
	}
	
	private static void initCars() { //init all cars
		for (int i=0; i < NUM_CARS; i++) {
			car[i] = new CarClass();
		}
	}
	
}
