import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class makeData {
    public static void main(String[] args) {
    	//check args
        if (args.length != 4) {
            System.out.println("Usage: java makeData <number of items> <L> <U> <output file name>");
            System.exit(1);
        }
        long startTime = System.nanoTime();
        //take data from user
        int numItems = Integer.parseInt(args[0]);
        int L = Integer.parseInt(args[1]);
        int U = Integer.parseInt(args[2]);
        String outputFileName = args[3];
        
        // Seeding the RNG with 42
        Random random = new Random(42); 
        
        if(L>U)
        {
        	System.out.println("Error: Upper must be greater than lower");
        	System.exit(1);
        }
        
        long generate = System.nanoTime();
        
        //Use dataoutput stream inside of try catch to read binary files
        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outputFileName)))) {
        	//meta data
            dos.writeInt(numItems); 
            for (int i = 0; i < numItems; i++) {
            	//write "random" ints
                int randomInt = random.nextInt(U - L + 1) + L; 
                dos.writeInt(randomInt);
            }
        } catch (FileNotFoundException e) {
        	System.out.println("File not found: " + outputFileName);
            e.printStackTrace();
        } catch (IOException e) {
        	System.out.println("Error writing to file: " + outputFileName);
            e.printStackTrace();
        }
        long generateEnd = System.nanoTime();
        long endTime = System.nanoTime();
        double timeTakenTotal = (endTime - startTime) / 1e9; 
        double GenTotalTime = (generateEnd - generate) / 1e9; 
        System.out.println("Total time taken and time it took to generate file");
        System.out.printf("%.2E %.2E \n", timeTakenTotal,GenTotalTime);
    }
}
