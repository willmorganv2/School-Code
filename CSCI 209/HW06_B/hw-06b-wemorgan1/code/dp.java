import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class dp {
    public static void main(String[] args) {
    	//check args
        if (args.length != 2) {
            System.out.println("Usage: java dp <input file name 1> <input file name 2>");
            System.exit(1);
        }
        
        // Start timing
        long startTime = System.nanoTime();
        
        //initialize datainputstream and then try to create it
        DataInputStream fileA = null;
        DataInputStream fileB = null;
        try {
            fileA = new DataInputStream(new FileInputStream(args[0]));
            fileB = new DataInputStream(new FileInputStream(args[1]));
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        }
        
        //initialize num elements then try to read meta data
        int numElementsA = 0;
        int numElementsB = 0;
        try {
            numElementsA = fileA.readInt();
            numElementsB = fileB.readInt();
            if (numElementsA != numElementsB) {
            	//let user know file meta data incorrect
                System.err.println("Files are not the same length.");
                System.exit(1);
            }
        } catch (IOException e) {
        	System.out.println("Error reading file.");
            System.exit(1);
        }
        
        //create arrays and read the files and input that data into the arrays
        int[] A = new int[numElementsA];
        int[] B = new int[numElementsB];
        try {
            for (int i = 0; i < numElementsA; i++) {
                A[i] = fileA.readInt();
                B[i] = fileB.readInt();
            }
        } catch (IOException e) {
        	System.err.println("Error reading integers from file.");
            System.exit(1);
        }
        
        
        // Calculate the dot product and dot product time
        long loopStartTime = System.nanoTime();
        int dp = calc_dp(A, B);
        long loopEndTime = System.nanoTime();
        double timeTakenLoop = (loopEndTime - loopStartTime) / 1e9; 

        // End timing
        long endTime = System.nanoTime();
        double timeTakenTotal = (endTime - startTime) / 1e9; 

        // Print timing information and result
        System.out.printf("%d %.2E %.2E %d\n", numElementsA,timeTakenLoop,timeTakenTotal, dp);
    }

    public static int calc_dp(int[] A, int[] B) {
        int dp = 0;
        for (int i = 0; i < A.length; i++) {
            dp += A[i] * B[i];
        }
        return dp;
    }
}
