import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class stencil_2d {

    public static void main(String[] args) {
        // Variables to measure execution time
        long startTime = System.nanoTime();
        long IOTime = 0; // IO time
        long workTime = 0; // Computation time

        // Check if the correct number of command-line arguments is provided
        if (args.length != 4) {
            System.out.println("usage: java stencil_2d <num_iterations> <initial_state> <final_state> <all_frames_output_file>");
            System.exit(1);
        }

        // Parse command-line arguments
        int numIterations = Integer.parseInt(args[0]);
        String initialState = args[1];
        String finalState = args[2];
        String allFramesOutputFile = args[3];

        try {
            // Read initial state from the input file
            long startIOTime = System.nanoTime();
            DataInputStream dis = new DataInputStream(new FileInputStream(initialState));
            // Read the number of rows and columns from the input file
            int rows = dis.readInt();
            int cols = dis.readInt();
            int frameStart = dis.readInt();

            // Create an array to store the data for all frames
            double[][][] frames = new double[numIterations + 1][rows][cols];

            // Populate the initial frame with data from the input file
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    frames[0][i][j] = dis.readDouble();
                }
            }
            dis.close();
            long endIOTime = System.nanoTime();
            IOTime += endIOTime - startIOTime;

            // Perform stencil computation for the specified number of iterations
            for (int iter = 1; iter <= numIterations; iter++) {
                long startWorkTime = System.nanoTime(); // Record computation start time
                double[][] newData = new double[rows][cols];
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        if (i == 0 || i == rows - 1 || j == 0 || j == cols - 1) {
                            // Boundary cells remain unchanged
                            newData[i][j] = frames[iter - 1][i][j];
                        } else {
                            // Apply the stencil operation to compute the new value
                            newData[i][j] = (frames[iter - 1][i - 1][j - 1] + frames[iter - 1][i - 1][j] + frames[iter - 1][i - 1][j + 1] +
                                    frames[iter - 1][i][j - 1] + frames[iter - 1][i][j] + frames[iter - 1][i][j + 1] +
                                    frames[iter - 1][i + 1][j - 1] + frames[iter - 1][i + 1][j] + frames[iter - 1][i + 1][j + 1]) / 9.0;
                        }
                    }
                }

                // Store the new frame in the frames array
                frames[iter] = newData;

                long endWorkTime = System.nanoTime(); // Record computation end time
                workTime += endWorkTime - startWorkTime; // Accumulate computation time
            }

            // Write all frames to the output file
            long startIOTimeAllFrames = System.nanoTime();
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(allFramesOutputFile));
            dos.writeInt(rows);
            dos.writeInt(cols);
            dos.writeInt(numIterations + 1); // Total frames

            for (int iter = 0; iter <= numIterations; iter++) {
                double[][] frame = frames[iter];
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        dos.writeDouble(frame[i][j]);
                    }
                }
            }
            dos.close();
            long endIOTimeAllFrames = System.nanoTime();
            IOTime += endIOTimeAllFrames - startIOTimeAllFrames;

            // Write the final frame to the finalState file
            long startIOTimeFinalFrame = System.nanoTime();
            DataOutputStream finalDos = new DataOutputStream(new FileOutputStream(finalState));
            finalDos.writeInt(rows);
            finalDos.writeInt(cols);
            finalDos.writeInt(1);
            double[][] finalFrame = frames[numIterations];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    finalDos.writeDouble(finalFrame[i][j]);
                }
            }
            finalDos.close();
            long endIOTimeFinalFrame = System.nanoTime();
            IOTime += endIOTimeFinalFrame - startIOTimeFinalFrame;
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.nanoTime();
        // Calculate the total time taken for the operation
        double totalTimeInSeconds = (double) (endTime - startTime);

        // Print the total time, work time, and IO time in seconds
        System.out.printf("%.2e %.2e %.2e\n", (double) totalTimeInSeconds / 1000000000, (double) workTime / 1000000000, (double) IOTime / 1000000000);

    }
}
