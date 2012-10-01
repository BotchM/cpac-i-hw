/* CPAC I, Evan Korth
 * Assignment #2
 * 01 Oct 12
 * Victor (Ben) Turner, vt520@nyu.edu, N15271750

 * Takes numbers and operators as user input for basic calculator
 * operations.
*/

import java.util.Scanner; // To take in user input.

public class Calc {
	
	/**
	 * promptOp() - Will ask user for operator and return said operator as a char.
	 * @param scanner : Class object for user input.
	 * @return : Returns a char.
	 */
	public static char promptOp(Scanner scanner) {
		char inputOp;
		System.out.print("op: ");
		inputOp = scanner.next().charAt(0); // Hacky way to convert string immediately to char.
		if (inputOp == 'x') {
			System.out.println("exiting");
			System.exit(0);
		}
		return inputOp;
	}
	
	/**
	 * promptVal() - Prompts user for a value which is returned as a float.
	 * @param scanner : Class object for user input.
	 * @param whichInput : String to keep track of which input it is (1st, 2nd, or more).
	 * @return : Returns a float.
	 */
	public static float promptVal(Scanner scanner, String whichInput) {
		float inputVal = 0.0f;
		System.out.print(whichInput + " input: ");
		inputVal = scanner.nextFloat();
		
		return inputVal;
	}
	
	/**
	 * calcVals() - Takes 2 values and an operator and performs the correct operation, returning a result.
	 * @param val1 : 1st value inputted from user.
	 * @param val2 : 2nd value inputted from user.
	 * @param operator : Operator (*, /, +, -) inputted from user.
	 * @param result : Current result up to this point passed in as "memory".
	 * @param firstTime : If true, then this is the first time calcVals() has been called during the program run. (so that val1 preserved if operator is illegal)
	 * @return : Returns the resulting value of the operation, as a float.
	 */
	public static float calcVals(float val1, float val2, char operator, float result, boolean firstTime) {
		
		if (operator == 'c') { // c will zero out the calculator.
			return 0;
		} else if (operator == 'x') { // x will exit.
			System.exit(0);
		} else if (operator == '*') {
			result = val1 * val2;
			System.out.println("ans: " + result);
		} else if (operator == '+') {
			result = val1 + val2;
			System.out.println("ans: " + result);
		} else if (operator == '-') {
			result = val1 - val2;
			System.out.println("ans: " + result);
		} else if (operator == '/') {
			if (val2 == 0) { // Checks zero divide before operation is attempted.
				System.out.println("Error: division by zero");
			} else {
				result = val1 / val2;
				System.out.println("ans: " + result);
			}
		} else {
			System.out.println("Error: unknown operator " + operator);
			if (firstTime == true) {
				result = val1;
			}
		}
		return result;
	}
	
	public static void testCode() {
		float testResult;
		int numTests = 6, numPassed = 0, numFailed = 0;
		
		// Test addition.
		testResult = calcVals(10, 5, '+', 0, false);
		if (testResult == 15.0) {
			System.out.println("PASSED. Test: 10 + 5 = " + testResult); numPassed++;
		}
		else {
			System.out.println("FAILED. Test: 10 + 5 = " + testResult); numFailed++;
		}
		
		// Test subtraction.
		testResult = calcVals(10, 5, '-', 0, false);
		if (testResult == 5.0) {
			System.out.println("PASSED. Test: 10 - 5 = " + testResult); numPassed++;
		}
		else {
			System.out.println("FAILED. Test: 10 - 5 = " + testResult); numFailed++;
		}
		
		// Test multiplication.
		testResult = calcVals(10, 5, '*', 0, false);
		if (testResult == 50.0) {
			System.out.println("PASSED. Test: 10 * 5 = " + testResult); numPassed++;
		}
		else {
			System.out.println("FAILED. Test: 10 * 5 = " + testResult); numFailed++;
		}
		
		// Test division.
		testResult = calcVals(10, 5, '/', 0, false);
		if (testResult == 2.0) {
			System.out.println("PASSED. Test: 10 / 5 = " + testResult); numPassed++;
		}
		else {
			System.out.println("FAILED. Test: 10 / 5 = " + testResult); numFailed++;
		}
		
		// Test division by zero handling.
		testResult = calcVals(10, 0, '/', 0, false);
		if (testResult == 0.0) {
			System.out.println("PASSED. Zero divide error not allowed."); numPassed++;
		}
		else {
			System.out.println("FAILED. No error message. Test: 10 / 0 = " + testResult); numFailed++;
		}
		
		// Test clearing memory buffer.
		testResult = calcVals(10, 0, 'c', 0, false);
		if (testResult == 0.0) {
			System.out.println("PASSED. Memory cleared. Memory value is: " + testResult); numPassed++;
		}
		else {
			System.out.println("FAILED. Memory not cleared. Result: " + testResult); numFailed++;
		}
		
		// Print out test results.
		System.out.println("Test results -- PASSED: " + numPassed + ", FAILED: " + numFailed + ", TOTAL: " + numTests);
	}
	
	public static void main(String args[]) {
		// Declare & init.
		float inputVal1 = 0.0f, inputVal2 = 0.0f, inputVal = 0.0f, memVal = 0.0f;
		char operator = ' ';
		Scanner scanner = new Scanner(System.in);
		boolean exited = false;
		
		testCode(); // Uncomment this block to test code.
		
		// First attempt to get input, then calc result.
		inputVal1 = promptVal(scanner, "1st");
		operator = promptOp(scanner);
		inputVal2 = promptVal(scanner, "2nd");
		
		// Final parameter checks if this is the first time running the calculator.
		// If true, then if user enters wrong operator, first value (val1) is preserved.
		memVal = calcVals(inputVal1, inputVal2, operator, memVal, true);
		
		while (exited == false) { // Will loop calculator until user exits.
			operator = promptOp(scanner);
			inputVal = promptVal(scanner, "more");
			memVal = calcVals(memVal, inputVal, operator, memVal, false);
		}
	}
	
}
