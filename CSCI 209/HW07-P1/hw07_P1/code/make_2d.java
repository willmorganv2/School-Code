import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class make_2d {

    public static void main(String[] args) {

        // check argument length
        if (args.length != 3) { // Check if the correct number of command line arguments is provided
            String message = "usage: ./make_2d <rows> <cols> <output_file>"; // Usage message for incorrect arguments
            System.out.println(message); // Print the usage message
            System.exit(1); // Exit the program with error code 1
        } else {
            // collect data from user
            int rows = Integer.parseInt(args[0]); // Parse the number of rows from command line argument
            int cols = Integer.parseInt(args[1]); // Parse the number of columns from command line argument
            String outputFilename = args[2]; // Get the output filename from command line argument

            try {
                long startIOTime = System.nanoTime(); // Record the start time for IO operations

                // create the 2d array
                double[][] data = new double[rows][cols]; // Create a 2D array of size rows x cols

                // populate the 2d array with random values based on given parameters
                for (int i = 0; i < rows; i++) { // Loop over rows
                    for (int j = 0; j < cols; j++) { // Loop over columns
                        if (j == 0 || j == cols - 1) { // Check if it's the first or last column
                            data[i][j] = 1.0; // Set the value to 1.0 for boundary columns
                        } else {
                            data[i][j] = 0.0; // Set the value to 0.0 for interior columns
                        }
                    }
                }

                // create data output stream to output binary data
                DataOutputStream dos = new DataOutputStream(new FileOutputStream(outputFilename)); // Open a binary output stream for writing data
                // write the rows and cols to the file for use of other programs.
                dos.writeInt(rows); // Write the number of rows to the file
                dos.writeInt(cols); // Write the number of columns to the file

                // write the data to the new file
                for (int i = 0; i < rows; i++) { // Loop over rows
                    for (int j = 0; j < cols; j++) { // Loop over columns
                        dos.writeDouble(data[i][j]); // Write the double value to the file
                    }
                }

            } catch (IOException e) { // Catch IOException if any error occurs during IO operations
                e.printStackTrace(); // Print the stack trace for the exception
            }
        }

    }
}
