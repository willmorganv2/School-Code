import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class generate_solution {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Get the start time of the project
		long startTime = System.currentTimeMillis();
		
		//if args length is not 4 the program will throw an error
		if(args.length != 4) {
			String message = "usage: java generate <fileName> <number> <lower-bound> <upper-bound>";
			System.out.println(message);
			System.exit(1);
		}
		else {
			//store all necessary outputs into variables
			String fn = args[0];
			int number = Integer.parseInt(args[1]);
			int lb = Integer.parseInt(args[2]);
			int ub = Integer.parseInt(args[3]);
			
			//print messages
			System.out.println(" --> opening file \"" + fn + "\" for writing");
			System.out.println(" --> writing " + number + " ints into file");
			System.out.println(" --> using UNIF[" + lb + "," + ub + "] distribution");
			
			//initialize FileWriter and set it to the given file name
			FileWriter file = null;
			try {
				file = new FileWriter(fn);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//try code
			try {
				//initialize random variable that will help us get the random integer
				Random random = new Random();
				//make randonInts array to the length of the given number
				int[] randomInts = new int[number];
				//Create BufferedWriter for faster running of program
				BufferedWriter newFile = new BufferedWriter(file);
				//loop through the randomInts array by its length
				for( int i = 0; i < randomInts.length; i++) 
				{
					//set the values in array with a random number
					//"nextInt(ub - lb + 1)" will get a random integer in the specified range (ub - lb + 1). + lb at the end makes sure that the last and first value is included
					randomInts[i] = random.nextInt(ub - lb + 1) + lb;
					
					//write randomInts into the file
					newFile.write(randomInts[i] + "");
					//write new line
					newFile.write('\n');
				}
				//close the buffer file
				newFile.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//close the file
			try {
				file.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//get the current time for the end of the project
		long endTime = System.currentTimeMillis();
		//subtract the two to see how much time passed
		long timeElapsed = endTime - startTime;
		//convert into seconds and print to the user
		double elapsedSec = timeElapsed / 1000.0;
		System.out.println("total elapsed runtime = " + elapsedSec + " (seconds)");				
	}

}
