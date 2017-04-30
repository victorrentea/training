package caplin.tdd;

/**
 * Created by nicolaes on 07/01/2016.
 */
public class Fibonacci {
		int[] getFibonacciSeries(int length) {
				int[] fibonacciSeries = new int[length];
				for (int i = 0; i < length; i++) {
						if (i < 2) {
								fibonacciSeries[i] = i;
						} else {
								fibonacciSeries[i] = fibonacciSeries[i - 1] + fibonacciSeries[i - 2];
						}
				}
				return fibonacciSeries;
		}
}
