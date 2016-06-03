package victor.perf.fizzfuzz;

public class FizzFuzzOptimized {
	public static void main(String[] args) {
		int[] n = { 3, 5, 15};
		String[] messages = { "fizz", "fuzz", "fizz fuzz"};
		for ( int i = 1; i < 100; i++) {
			String temp = Integer.toString(i);
			int index = 3;
			if ( ( i % n[ --index]) == 0)
				temp = messages[index];
			else if ( ( i % n[ --index]) == 0)
				temp = messages[index];
			else if ( ( i % n[ --index]) == 0)
				temp = messages[index];
			System.out.println( temp);
		}
	}

}
