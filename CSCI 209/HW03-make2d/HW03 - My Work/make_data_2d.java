import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class make_data_2d {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//get start time
        long startTime = System.nanoTime();
        //check argument length
		if(args.length != 5) 
		{
			String message = "usage: java generate <#rows> <#cols> <low> <high> <output filename>";
			System.out.println(message);
			System.exit(1);
		}
		else 
		{
			//collect data from user
			int rows = Integer.parseInt(args[0]);
	        int cols = Integer.parseInt(args[1]);
	        int low = Integer.parseInt(args[2]);
	        int high = Integer.parseInt(args[3]);
	        String outputFilename = args[4];
	        
	        //create the 2d array
	        int[][] data = new int[rows][cols];

			//initialize random variable that will help us get the random integer
			Random random = new Random();
	        //populate the 2d array with random values based on given parameters
	        for (int i = 0; i < rows; i++) {
	            for (int j = 0; j < cols; j++) {
	                data[i][j] = (int) random.nextInt(high - low + 1) + low;
	            }
	        }

	        try  {
	        	//create data output stream to output binary data
	        	DataOutputStream dos = new DataOutputStream(new FileOutputStream(outputFilename));
	        	//write the rows and cols to the file for use of other programs.
	            dos.writeInt(rows);
	            dos.writeInt(cols); 
	            
	            //write the data to the new file
	            for (int i = 0; i < rows; i++) {
	                for (int j = 0; j < cols; j++) {
	                    dos.writeInt(data[i][j]); 
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	        //calculate and print how long program took and bytes per second.
	        long endTime = System.nanoTime();
	        double elapsedTime = (endTime - startTime) / 1e9; // seconds
	        double bytesPerSecond = (double) new File(outputFilename).length() / elapsedTime;

	        System.out.printf("Time: %.1e seconds\n", elapsedTime);
	        System.out.printf("Bytes per second: %.1e\n", bytesPerSecond);
		}
	}

}
