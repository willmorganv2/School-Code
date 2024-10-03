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
        if (args.length != 3) {
            System.out.println("usage: ./stencil_2d <num iterations> <input file> <output file>");
            System.exit(1);
        }

        // Parse command-line arguments
        int num_iterations = Integer.parseInt(args[0]);
        String inputfile = args[1];
        String outputfile = args[2];

        try {
            // Create a data input stream to read binary files
            DataInputStream dis = new DataInputStream(new FileInputStream(inputfile));
            // Read the number of rows and columns from the input file
            int rows = dis.readInt();
            int cols = dis.readInt();

            // Create a 2D array to store the data
            double[][] data = new double[rows][cols];

            // Populate the 2D array from the input file
            long startIOTime = System.nanoTime(); // Record IO start time
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    data[i][j] = dis.readDouble();
                }
            }
            long endIOTime = System.nanoTime(); // Record IO end time
            IOTime = endIOTime - startIOTime; // Calculate IO time

            // Perform stencil computation for the specified number of iterations
            for (int k = 0; k < num_iterations; k++) {
                long startWorkTime = System.nanoTime(); // Record computation start time
                double[][] newData = new double[rows][cols];
                for (int i = 1; i < rows - 1; i++) {
                    for (int j = 1; j < cols - 1; j++) {
                        // Apply the stencil operation to compute the new value
                        newData[i][j] = (data[i - 1][j - 1] + data[i - 1][j] + data[i - 1][j + 1] +
                                data[i][j - 1] + data[i][j] + data[i][j + 1] +
                                data[i + 1][j - 1] + data[i + 1][j] + data[i + 1][j + 1]) / 9.0;
                    }
                }

                // Update the original data with the new values after each iteration
                for (int i = 1; i < rows - 1; i++) {
                    for (int j = 1; j < cols - 1; j++) {
                        data[i][j] = newData[i][j];
                    }
                }
                long endWorkTime = System.nanoTime(); // Record computation end time
                workTime += endWorkTime - startWorkTime; // Accumulate computation time
            }

            try {
                // Create a data output stream to write binary data to the output file
                DataOutputStream dos = new DataOutputStream(new FileOutputStream(outputfile));
                // Write the number of rows and columns to the output file
                dos.writeInt(rows);
                dos.writeInt(cols);

                // Write the computed data to the output file
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        dos.writeDouble(data[i][j]);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.nanoTime();
        // Calculate the total time taken for the operation
        double totalTimeInSeconds = (double) (endTime - startTime) ;

        // Print the total time, work time, and IO time in seconds
        System.out.printf("%.2e %.2e %.2e\n", (double) totalTimeInSeconds / 1000000000, (double) workTime / 1000000000, (double) IOTime / 1000000000);

    }
}
