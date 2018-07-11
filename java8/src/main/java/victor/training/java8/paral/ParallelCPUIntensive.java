package victor.training.java8.paral;

import java.util.stream.IntStream;

class ParallelCPUIntensive {
	public static void main(String[] args) {
		long t0 = System.currentTimeMillis();
//		int n = 0;
//		for (int i = 0; i < 50000000; i++) {
//			if (isPalindrome(i)) {
//				n++;
//			}
//		}

		int n = (int) IntStream.range(0, 50000000).parallel().filter(ParallelCPUIntensive::isPalindrome).count();
		System.out.println("Dt = " + (System.currentTimeMillis() - t0));
		System.out.println("Palindromes: " + n);
	}
	private static boolean isPalindrome(int i) {
		String s = "" + i;
		return s.equals(new StringBuilder(s).reverse().toString());
	}
}