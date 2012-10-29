/* CPAC I, Evan Korth
 * Assignment #5
 * 22 Oct 12
 * Victor (Ben) Turner, vt520@nyu.edu, N15271750

 * w/ CarClassAssignment.java
 
*/

import java.util.Random;

class CarClass {
	private boolean ignition;
	private int x, y;
	private char carColor;

	CarClass() {
		ignition = false;
		x = randomizePos(CarClassAssignment.LIMIT_MIN_X, CarClassAssignment.LIMIT_MAX_X);
		y = randomizePos(CarClassAssignment.LIMIT_MIN_Y, CarClassAssignment.LIMIT_MAX_Y);
		carColor = randomizeColor();
	}
	
	//Does error-checking on user-entered value, returns new value if okay to move, old value if not.
	public int moveX(int mvUnits) {
		int newPos = this.x + mvUnits;
		if (ignition == false) { // car is off
			System.out.println("Your car is not running. You must turn the ignition on first!");
			return this.x;
		}
		else {
			if (newPos < CarClassAssignment.LIMIT_MIN_X || newPos > CarClassAssignment.LIMIT_MAX_Y) { // out of bounds
				System.out.println("You cannot drive beyond the limits of the grid. Try again!");
				return this.x;
			}
			else {
				this.x = newPos;
				return newPos;				
			}
		}
	}
	
	// Does error-checking on user-entered value, returns new value if okay to move, old value if not.
	// TODO: Error-check if mvUnits is an int.
	public int moveY(int mvUnits) {
		int newPos = this.y + mvUnits;
		if (ignition == false) {
			System.out.println("Your car is not running. You must turn the ignition on first!");
			return this.y;
		}
		else {
			if (newPos < CarClassAssignment.LIMIT_MIN_Y || newPos > CarClassAssignment.LIMIT_MAX_Y) {
				System.out.println("You cannot drive beyond the limits of the grid. Try again!");
				return this.y;
			}
			else {
				this.y = newPos;
				return newPos;
			}
		}
	}
	
	//--------------------------------------------------- BEGIN - RANDOMIZE FUNCTIONS
	private static int randomizePos(int min, int max) {
		Random randNum = new Random();
		return randNum.nextInt(max - min + 1) + min;
	}
	
	// Returns a char that symbolizes the car's color.
	private static char randomizeColor() {
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
	
	//If ignition is false (off), return its opposite value. If true, returns false.
	public void toggleIgnition() {
		ignition = !this.ignition;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public char getCarColor() {
		return this.carColor;
	}
	
	public boolean getIgnition() {
		return this.ignition;
	}
	
}
