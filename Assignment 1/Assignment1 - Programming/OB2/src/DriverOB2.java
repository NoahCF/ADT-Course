import java.util.Random;

public class DriverOB2 {
	/**
	 * @param args 
	 */
	public static void main(String[] args) {
	
		// n is assigned to a value that does not cost a stack overflow.
		// n is the size of array a which is populated with random variables.
		int n = 25000;
		int x = 32000;
		int a[] = new int[x];
		Random rand = new Random();
		for (int i : a) {
			a[i] = rand.nextInt();
		}
		
		/*
		 *  creating an instance of type TimingMeasurement and calling 
		 *  multplyRunningTime() method to get the running time and to 
		 *  output all relevant results to the out.txt file. 
		 */
		TimingMeasurement test = new TimingMeasurement();
		test.multiplyRunningTime(a, x);
		
		TailRecursiveTimingMeasurement test2 = new TailRecursiveTimingMeasurement();
		test2.multiplyRunningTime(a, n, 1);
	}
}
