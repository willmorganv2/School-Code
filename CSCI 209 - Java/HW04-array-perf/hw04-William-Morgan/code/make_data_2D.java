public class make_data_2D {
    public static void main(String[] args) {
        // TODO -- update to take one more parameter w
        // w is the number of times to do the "work" portion of the code
    	//parameter check
        if(args.length != 3) {
            System.out.println("usage: java make_data_2D <#r> <#c> <w>");
            System.exit(1);
        }
        //take in values
        int rows = Integer.parseInt(args[0]);
        int cols = Integer.parseInt(args[1]);
        int w = Integer.parseInt(args[2]);

        // way 1 -- normal way -- all at once
        //start create time for W1
        long start1_create = System.nanoTime();
        double[][] a = new double[rows][cols];
        long end1_create = System.nanoTime();
        //calculate create time for W1
        double elapsed1_create = (end1_create - start1_create) / 1E9;

        //start work time for W1, using the "Work" parameter
        long start1_work = System.nanoTime();
        // this is the work part of the code for way 1
        // TODO -- add outer loop to do the work part "w" times
        for(int k = 0; k < w; k++) {
            for(int i=0; i<a.length; i++) {
                for(int j=0; j<a[i].length; j++) {
                    a[i][j] = 42.5;
                }
            }
            for(int i=0; i<a.length; i++) {
                for(int j=0; j<a[i].length; j++) {
                    a[i][j] *= (i+j);
                }
            }
        }
        long end1_work = System.nanoTime();
        //calculate work time for W1
        double elapsed1_work = (end1_work - start1_work) / 1E9;

        // way 2 -- row by row
        //start create for initializing W2
        long start2_create = System.nanoTime();
        double[][] b = new double[rows][];
        for(int i=0; i<b.length; i++) {
            b[i] = new double[cols];
        }
        long end2_create = System.nanoTime();
        //calculate create time
        double elapsed2_create = (end2_create - start2_create) / 1E9;
        
        //calculate work time for w2, using the "Work" parameter
        long start2_work = System.nanoTime();
        // this is the work part for way 2
        // TODO -- add outer loop to do the work part "w" times
        for(int k = 0; k < w; k++) {
            for(int i=0; i<b.length; i++) {
                for(int j=0; j<b[i].length; j++) {
                    b[i][j] = 42.5;
                }
            }
            for(int i=0; i<b.length; i++) {
                for(int j=0; j<b[i].length; j++) {
                    b[i][j] *= (i+j);
                }
            }
        }
        long end2_work = System.nanoTime();
        //calculate work time for w2
        double elapsed2_work = (end2_work - start2_work) / 1E9;

        double total1 = elapsed1_create + elapsed1_work;
        double total2 = elapsed2_create + elapsed2_work;
        //print to screen
        System.out.printf("W1 = %.1E + %.1E = %.1E, W2 = %.1E + %.1E = %.1E \n", elapsed1_create, elapsed1_work, total1, elapsed2_create, elapsed2_work, total2);
    }
}
