import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ParallelSum {
	public static void main(String[] args) throws InterruptedException, IOException {
		// Check if args is less than two or greater than three since it can either be
		// two or three arguments
		if (args.length < 2 || args.length > 3) {
			System.out.println("Usage: java ParallelSum <number of threads> <array size> <result file>");
			return;
		}
		// get information
		int numberOfThreads = Integer.parseInt(args[0]);
		int arraySize = Integer.parseInt(args[1]);
		String resultFile = "";

		// check if length is 3 for third parameter
		if (args.length == 3)
			resultFile = args[2];

		// create and populate array
		int[] numbers = new int[arraySize];
		for (int i = 0; i < arraySize; i++) {
			// numbers[i] = (int) (Math.random() * 100); // Fill the array with random
			// numbers
			numbers[i] = 1;
		}

		// Calculate times
		// Sequential execution
		long startTime = System.nanoTime();
		long seqSum = sequentialSum(numbers);
		long endTime = System.nanoTime();
		long sequentialTime = endTime - startTime;

		System.out.println("Sequential sum: " + seqSum + ", Time: " + (sequentialTime / 1e9) + " seconds");

		// Parallel execution
		startTime = System.nanoTime();
		long parallelSum = parallelSum(numbers, numberOfThreads);
		endTime = System.nanoTime();
		long parallelTime = endTime - startTime;

		System.out.println("Parallel sum: " + parallelSum + ", Time: " + (parallelTime / 1e9) + " seconds");

		// Calculate speedup and efficiency
		double speedup = (double) sequentialTime / parallelTime;
		double efficiency = speedup / numberOfThreads;

		System.out.println("Speedup: " + speedup);
		System.out.println("Efficiency: " + efficiency);

		if (args.length == 3) {
			// write results to file
			File file = new File(resultFile);
			boolean fileExists = file.exists() && !file.isDirectory();
			// create file writer
			try (FileWriter writer = new FileWriter(resultFile, true)) {
				// add headers if the file is empty
				if (!fileExists || file.length() == 0) {
					writer.write("N\tP\tT_1\tT_P\tS_P\tE_P\n");
				}
				writer.write(arraySize + "\t" + numberOfThreads + "\t" + (sequentialTime / 1e9) + "\t"
						+ (parallelTime / 1e9) + "\t" + speedup + "\t" + efficiency + "\n");
			} catch (IOException e) {

			}
		}
	}

	public static long parallelSum(int[] numbers, int numberOfThreads) throws InterruptedException {
		// get size for each partition
		int partSize = numbers.length / numberOfThreads;

		// arrays for the tasks and threads
		SumTask[] tasks = new SumTask[numberOfThreads];
		Thread[] threads = new Thread[numberOfThreads];
		long totalSum = 0;

		// divide arrays into partitions and create the tasks for each partition
		for (int i = 0; i < numberOfThreads; i++) {
			// get end and start size based on partition size
			int start = i * partSize;
			int end = (i + 1) * partSize;

			if (i == numberOfThreads - 1)
				end = numbers.length;

			// create the tasks for current partition
			tasks[i] = new SumTask(numbers, start, end);
			// set the thread to that task
			threads[i] = new Thread(tasks[i]);
			// start the thread
			threads[i].start();
		}

		// wait for all of the threads to finish executing
		for (int i = 0; i < numberOfThreads; i++) {
			threads[i].join();
			totalSum += tasks[i].getPartialSum(); // get sums for all threads
		}

		return totalSum;
	}

	public static long sequentialSum(int[] array) {
		long sum = 0;
		for (int value : array) {
			sum += value;
		}
		return sum;
	}
}
