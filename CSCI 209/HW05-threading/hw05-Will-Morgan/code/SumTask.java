public class SumTask implements Runnable {
    private int[] array;
    private int start, end;
    private long partialSum = 0;

    public SumTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            partialSum += array[i];
        }
    }

    public long getPartialSum() {
        return partialSum;
    }
}
