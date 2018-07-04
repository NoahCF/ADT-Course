import java.util.Formatter;

public class TailRecursiveTimingMeasurement {
	/**
	 * class attributes used to calculate the running time and 
	 * to output results into a file. 
	 */
	private static long startTime;
	private static long endTime;
	private static long time;
	private Formatter file;
	private static int counter = 0;
	
	
	/**
	 * method used to create or open the out.txt file
	 * while handling exceptions.
	 */
	public void openFile(){
		try {
			file = new Formatter("out2.txt");
		}
		catch(Exception e) {
			System.out.println("Exception error :\n\t" +e.getMessage());
			System.exit(0);
		}
	}
	
	/**
	 * @param n : the same value of n that is passed as parameter in 
	 * multiplyRunningTime() and multiply() methods
	 * @param t : is the calculated running time in multiplyRunningTime().
	 * @param r : the long value that is returned in multiply().
	 * method used to update information into out.txt 
	 */
	public void updateRecords(int n, long t, int r){
		file.format("For n = "+ n +"\nThe running time for this tail recursive method is " + t +
				" ms and the result that the Multiply method" + 
				" returns is " + r +".");
	}
	
	/**
	 * method used to close the out.txt file.
	 */
	public void closeFile(){
		file.close();
	}
	
	
	/**
	 * @param a : an array that is at least the size of n.
	 * @param n : a value that must be less or equal the size of a[].
	 * method used to calculate the running time and 
	 * to output the result into the out.txt file.
	 */
	public void multiplyRunningTime(int a[], int n, int m) {
		startTime = System.currentTimeMillis();
		int r = multiplyTailRecursive(a, n, m);
		endTime = System.currentTimeMillis();
		time = endTime - startTime;
		openFile();
		updateRecords(n, time, r);
		closeFile();
	}
	
	
	/**
	 * @param a : an array that is at least the size of n, the same 
				  array in multiplyRunningTime()
	 * @param n : a value that must be less or equal the size of a[].
	 * @param m : a value that be multiplied by the value of the next recursive call
	 * 			and that initially must be equal to 1.
	 * @return the calculated value in long.
	 */
	public int multiplyTailRecursive(int a[], int n, int m) {
		if (counter == 0) {
			m = 1;
			counter++;
		}
		if (n < 4){
			return 0;
		}
		else{
			return multiplyTailRecursive(a, n-3, m * a[n-1]);
			 	
		}		
	}
}
