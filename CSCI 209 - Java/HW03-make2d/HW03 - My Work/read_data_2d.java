import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class read_data_2d {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//get start time
        long startTime = System.nanoTime();
        //check if argument requirement is met
		if(args.length != 1) {
			String message = "usage: java generate <input filename>";
			System.out.println(message);
			System.exit(1);
		}
		else 
		{
			//collect data from the user
			String inputFilename = args[0];

	        try {
	        	//create data input stream to read binary files
	        	DataInputStream dis = new DataInputStream(new FileInputStream(inputFilename));
	        	//collect the rows and cols from file
	            int rows = dis.readInt();
	            int cols = dis.readInt();
	            
	            //create the 2d array
	            int[][] data = new int[rows][cols];
	            
	            //populate 2d array from the file
	            for (int i = 0; i < rows; i++) {
	                for (int j = 0; j < cols; j++) {
	                    data[i][j] = dis.readInt();
	                }
	            }

	            // print the 2d array out to the user
	            for (int[] row : data) {
	                for (int value : row) {
	                    System.out.print(value + " ");
	                }
	                System.out.println();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	        //calculate the bytes per second, time program took, and how long it took to read.
	        long endTime = System.nanoTime();
	        double elapsedTime = (endTime - startTime) / 1e9; // seconds
	        try {
	            long bytesPerSecondRead = new File(inputFilename).length();
	            double bytesPerSecond = (double) bytesPerSecondRead / elapsedTime;

	            System.out.printf("Time: %.1e seconds\n", elapsedTime);
	            System.out.printf("Bytes per second: %.1e\n", bytesPerSecond);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
	}

}
