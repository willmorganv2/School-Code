import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class process_file_solution {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long startTime = System.currentTimeMillis();
		
		if(args.length != 1){
			String message = "usage: java process_file_solution <input file name>";
			System.out.println(message);
			System.exit(1);
		}
		else {
			//get the name of the file
			String fn = args[0];
			
			//Make the file name into a file object so the scanner can recognize it.
			File f = new File(fn);
			Scanner scanner;
			
			//create try catch in case file does not exist
			try {
				//set the scanner to the file so it can read it
				scanner = new Scanner(f);
				
				//initialize variables
				int sum = 0;
				int max = Integer.MIN_VALUE;
				int min = Integer.MAX_VALUE;
				int linesProcessed = 0;
				
				//create a while loop that says if the scanner sees another int in the file then keep going
				while(scanner.hasNextInt()){
					//get the current number for the next logic
	                int currentNumber = scanner.nextInt();
	                //add all current numbers to sum
	                sum += currentNumber;
	                //use math functions to determine max and min
	                max = Math.max(max, currentNumber);
	                min = Math.min(min, currentNumber);
	                //add total lines processed
	                linesProcessed++;
	            }
				
				//get the current time for the end of the project
				long endTime = System.currentTimeMillis();
				//subtract the two to see how much time passed
				long timeElapsed = endTime - startTime;
				//convert into seconds and print to the user
				double elapsedSec = timeElapsed / 1000.0;
				
				//calculate the average based off the lines processed
				double average = (double)sum / linesProcessed;
				//calculate lines per second
				double linesPerSecond = (double)linesProcessed / elapsedSec;
				
				//print the seconds using printf so that it can be in exponential form
				System.out.printf("Total elapsed runtime = %.2E (seconds), lines processed per second = %.2E\n", elapsedSec, linesPerSecond);
				//display file stats
				System.out.println("Summary stats for file \'" + fn + "\' --> min = " + min + ", max = " + max + ", avg = " +  average + ", sum = " + sum);
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
