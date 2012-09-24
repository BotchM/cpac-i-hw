/* CPAC I, Evan Korth
 * Assignment #1
 * 23 Sep 12
 * Victor (Ben) Turner, vt520@nyu.edu, N15271750

 * Calculates the difference between two military standard time
 * readings and outputs the result.
 * 
 * I tried to keep main() clean logically.  calcTimeDiff() will
 * convert the military times to seconds, subtracts the two,
 * then converts the result back to military time.
 * 
 * main() -> readTimeInput() [x2] -> calcTimeDiff() -> getTimeInDigits() [x2] ->
 * calcSeconds() [x2] -> reassembleTime() -> printOutput()
*/

import java.util.Scanner; // to take in user input2

public class TimeDiff {
	
	// reads in each military time
	public static int readTimeInput(Scanner scanner, String whichTime) {
		int timeInput = 0;
		
		System.out.print("Enter " + whichTime + " time: ");
		timeInput = scanner.nextInt();
		
		return timeInput; // returns user-entered int
	}
	
	// breaks up military time into arr[3] containing hr, min, sec
	public static int[] getTimeInDigits(int timeStamp) {
		int[] timeInDigits = new int[3];
		int i = 0;
		
		while (timeStamp > 0) {
			timeInDigits[i] = timeStamp % 100; // array will be reversed so seconds will be arr[0], hours arr[2]
			timeStamp /= 100; // secs -> mins -> hrs
			i++;
		}
		
		return timeInDigits; // returns arr[3]
	}
	
	// turns the military time into timestamp of seconds
	public static int calcSeconds(int[] timeArr) {
		int hours = 0, minutes = 0, seconds = 0, totalSeconds = 0;
		
		hours = timeArr[2] * 3600;
		minutes = timeArr[1] * 60;
		seconds = timeArr[0];
		totalSeconds = hours + minutes + seconds;
		
		return totalSeconds; // returns int of seconds
	}
	
	// converts difference in seconds back into military time
	public static int reassembleTime(int secondsDiff) {
		int finalTimeDiff = 0, hours = 0, minutes = 0, seconds = 0;
		
		hours = secondsDiff / 3600;
		secondsDiff = secondsDiff % 3600;
		minutes = secondsDiff / 60;
		secondsDiff = secondsDiff % 60;
		seconds = secondsDiff;
		finalTimeDiff = hours * 10000 + minutes * 100 + seconds;
		
		return finalTimeDiff;
	}
	
	// converts two military times into seconds, subtracts them, reassembles difference
	// into military time
	public static int calcTimeDiff(int timeStart, int timeEnd) {
		int secondsDiff = 0, finalTimeDiff = 0;
		int[] timeStartArr, timeEndArr;
		
		timeStartArr = getTimeInDigits(timeStart);
		timeEndArr = getTimeInDigits(timeEnd);
		secondsDiff = calcSeconds(timeStartArr) - calcSeconds(timeEndArr);
		finalTimeDiff = reassembleTime(secondsDiff);
		
		return finalTimeDiff; // returns military time
	}
	
	public static void printOutput(int timeDiff) {
		System.out.println("TIME DIFFERENCE: " + timeDiff + "\n");
	}
	
	public static void main(String args[]) {
		// declare & init
		Scanner scanner = new Scanner(System.in);
		int timeStart, timeEnd, finalTimeDiff;
		
		// user input
		timeStart = readTimeInput(scanner, "first");
		timeEnd = readTimeInput(scanner, "second");
		
		// calculates difference
		finalTimeDiff = calcTimeDiff(timeStart, timeEnd);
		
		// outputs result
		printOutput(finalTimeDiff);
	}
	
}

