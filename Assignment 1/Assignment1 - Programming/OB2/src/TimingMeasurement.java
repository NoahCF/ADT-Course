
/**
 * @author Noah Francis
 * OB2
 */
import java.util.*;
import java.io.*;
import java.util.*;

public class TimingMeasurement {
	
	/**
	 * class attributes used to calculate the running time and 
	 * to output results into a file. 
	 */
	private static long startTime;
	private static long endTime;
	private static long time;
	private Formatter file;
	
	
	/**
	 * method used to create or open the out.txt file
	 * while handling exceptions.
	 */
	public void openFile(){
		try {
			file = new Formatter("out.txt");
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
		file.format("For n = "+ n +"\nThe running time is " + t +
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
	public void multiplyRunningTime(int a[], int n) {
		startTime = System.currentTimeMillis();
		int r = multiply(a, n);
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
	 * @return the calculated value in long.
	 */
	public int multiply(int a[], int n) {
		int temp;
		if (n < 4){
			return 0;
		}
		else{
			temp = a[n-1]*multiply(a, n-3);
			return temp;		
		}		
	}

}