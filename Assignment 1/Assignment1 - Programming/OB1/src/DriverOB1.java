/*Noah Hayek (27080409)
 * OB1
 */
public class DriverOB1 {
	public static void main(String[] args) {
		System.out.println("Computer running: " + System.getProperty("os.name") + "\n\n\n");
		ExecutionTime test = new ExecutionTime();
		long n = 1L;
		
		//testing execution time when n=1
		System.out.println("\tTesting execution time when n = " + n +".\n\n");
		test.sumOne(n);
		test.sumTwo(n);
		test.sumThree(n);
		n = n * 10L;
		
		//testing execution time when n=10
		System.out.println("\tTesting execution time when n = " + n +".\n\n");
		test.sumOne(n);
		test.sumTwo(n);
		test.sumThree(n);
		n = n * 10L;
		
		//testing execution time when n=100
		System.out.println("\tTesting execution time when n = " + n +".\n\n");
		test.sumOne(n);
		test.sumTwo(n);
		test.sumThree(n);
		n = n * 10L;
		
		//testing execution time when n=1000
		System.out.println("\tTesting execution time when n = " + n +".\n\n");
		test.sumOne(n);
		test.sumTwo(n);
		test.sumThree(n);
		n = n * 10L;
		
		//testing execution time when n=10000
		System.out.println("\tTesting execution time when n = " + n +".\n\n");
		test.sumOne(n);
		test.sumTwo(n);
		test.sumThree(n);
		n = n * 10L;
		
		//testing execution time when n=100000
		System.out.println("\tTesting execution time when n = " + n +".\n\n");
		test.sumOne(n);
		test.sumTwo(n);
		test.sumThree(n);
		n = n * 10L;
		
		//testing execution time when n=1000000
		System.out.println("\tTesting execution time when n = " + n +".\n\n");
		test.sumOne(n);
		test.sumTwo(n);
		test.sumThree(n);
		n = n * 10L;
		
		//testing execution time when n=10000000
		System.out.println("\tTesting execution time when n = " + n +".\n\n");
		test.sumOne(n);
		test.sumTwo(n);
		test.sumThree(n);
		n = 1L;	
	}
}
