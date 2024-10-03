import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class print_2d {
    public static void main(String[] args) {

        // Check if the correct number of arguments is provided
        if (args.length != 1) {
            String message = "usage: ./print_2d <input data file>";
            System.out.println(message);
            System.exit(1);
        } else {
            // Collect the input data file name from the command-line arguments
            String inputFilename = args[0];

            try {
                // Create a data input stream to read binary files
                DataInputStream dis = new DataInputStream(new FileInputStream(inputFilename));
                System.out.println("Reading in file: " + inputFilename);

                long startIOTime = System.nanoTime(); // Record IO start time

                // Collect the number of rows and columns from the file
                int rows = dis.readInt();
                int cols = dis.readInt();
                int totalFrames = dis.readInt();

                // Iterate through each frame
                for (int frame = 0; frame < totalFrames; frame++) {
                    System.out.println("Frame " + (frame + 1) + ":");
                    // Create the 2D array to store the data for the current frame
                    double[][] data = new double[rows][cols];

                    // Populate the 2D array from the file for the current frame
                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < cols; j++) {
                            data[i][j] = dis.readDouble();
                        }
                    }

                    // Print the 2D array to the user for the current frame
                    for (double[] row : data) {
                        for (double value : row) {
                            // Print each value with 2 decimal places
                            System.out.print(String.format("%.2f ", value));
                        }
                        System.out.println(); // Move to the next line after printing each row
                    }
                }
                
            } catch (IOException e) {
                // Handle IO exceptions (e.g., file not found)
                e.printStackTrace();
            }
        }

    }
}
