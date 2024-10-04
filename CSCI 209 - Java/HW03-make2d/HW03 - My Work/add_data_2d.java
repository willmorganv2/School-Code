import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class add_data_2d {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//get the start time of the program
        long startTime = System.nanoTime();
        //check is all argument properties are met
		if(args.length != 3) 
		{
			String message = "usage: java generate <in file1> <in file2> <out filename>";
			System.out.println(message);
			System.exit(1);
		}
		else 
		{
			//collect data from user
			String inputFile1 = args[0];
	        String inputFile2 = args[1];
	        String outputFile = args[2];
	        //surround with try catch 
	        try {
	        	//use datainput stream to read binary from the file and use output stream to write binary to the file
		        DataInputStream dis1 = new DataInputStream(new FileInputStream(inputFile1));
	            DataInputStream dis2 = new DataInputStream(new FileInputStream(inputFile2));
	            DataOutputStream dos = new DataOutputStream(new FileOutputStream(outputFile));
	            //read the first two ints from the file (the rows and cols of the 2d array)
	            int rows = dis1.readInt();
	            int cols = dis1.readInt();
	            int rows2 = dis2.readInt();
	            int cols2 = dis2.readInt();
	            //make sure the rows and cols are the same in both 2d arrays otherwise there will be an error.
	            if(rows == rows2 && cols == cols2) 
	            {
	            	//write the rows and the cols so that the read_data works
		            dos.writeInt(rows); // Write the number of rows
		            dos.writeInt(cols); // Write the number of columns
		            //make the two 2d arrays
		            int[][] data1 = new int[rows][cols];
		            int[][] data2 = new int[rows][cols];
		            //index through array and add all numbers element wise 
		            for (int i = 0; i < rows; i++) {
		                for (int j = 0; j < cols; j++) {
			                 data1[i][j] = dis1.readInt();
			                 data2[i][j] = dis2.readInt();
			                 dos.writeInt(data1[i][j] + data2[i][j]); 
		                }
		            }
		            //record end time and elapsed time
			        long endTime = System.nanoTime();
			        double elapsedTime = (endTime - startTime) / 1e9; 
	
			        try {
			        	//calculate and record read write and additions per second
			            long bytesPerSecondRead = new File(inputFile1).length() + new File(inputFile2).length();
			            double bytesPerSecondWrite = (double) new File(outputFile).length() / elapsedTime;
			            double additionsPerSecond = (double) (rows * cols) / elapsedTime;
			            
			            //print all to screen
			            System.out.printf("Time: %.1e seconds\n", elapsedTime);
			            System.out.printf("Bytes per second (read): %.1e\n", bytesPerSecondRead / elapsedTime);
			            System.out.printf("Bytes per second (write): %.1e\n", bytesPerSecondWrite);
			            System.out.printf("Additions per second: %.1e\n", additionsPerSecond);
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
	            }
	            else 
	            {
	            	System.out.println("Please enter two files that have the same number of rows and columns");
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
	}
}
