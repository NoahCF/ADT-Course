/*Noah Hayek (27080409)
 * OB1
 */
public class ExecutionTime {
	public static long startTime;
	public static long endTime;
	
	
	public void sumOne(long n) {
		startTime = System.currentTimeMillis();
		long sum = 0L;
		for (long m = 1L; m <= n; m++) {
			sum = sum +m;
		}
		endTime = System.currentTimeMillis();
		System.out.println("\t\tThe first algorithm took " + (endTime - startTime) + " ms to execute.\n"
				+ "\t\tThe sum is equal to: " + sum + "\n");
	}
	
	public void sumTwo(long n) {
		startTime = System.currentTimeMillis();
		long sum = 0L;
		for (long m = 1L; m <= n; m++) {
			for (long k = 1L; k <= m; k++) {
				sum = sum + 1L;
			}
		}
		endTime = System.currentTimeMillis();
		System.out.println("\t\tThe second algorithm took " + (endTime - startTime) + " ms to execute.\n"
				+ "\t\tThe sum is equal to: " + sum + "\n");
	}
	
	public void sumThree(long n) {
		startTime = System.currentTimeMillis();
		long sum = n * (n + 1L) / 2L ;
		endTime = System.currentTimeMillis();
		System.out.println("\t\tThe third algorithm took " + (endTime - startTime) + " ms to execute.\n"
				+ "\t\tThe sum is equal to: " + sum + "\n\n");
	}
}
