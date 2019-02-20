package victor;

import java.util.ArrayList;
import java.util.List;

class FizzBuzz {
	public static List<String> run() {
		int[] divisors = { 3, 5, 15 };
		String[] messages = { "fizz", "fuzz", "fizz fuzz" };
		FizzBuzz fuzz = new FizzBuzz(1, 100);
		fuzz.printNumbersDivisibleByN(divisors, messages);
		return fuzz.getResults();
	}

	int lowerRange, upperRange;
	List<String> results = new ArrayList<>();

	public FizzBuzz(int lowerRange, int upperRange) {
		this.lowerRange = lowerRange;
		this.upperRange = upperRange;
	}

	public List<String> getResults() {
		return results;
	}

	public int getLowerRange() {
		return this.lowerRange;
	}

	public int getUpperRange() {
		return this.upperRange;
	}

	public void printNumbersDivisibleByN(int[] n, String[] messages) {
		if ((n == null) || (messages == null))
			return;
		for (int i = getLowerRange(); i < getUpperRange(); i++) {
			String messageToPrint = fizzFuzz(i, n, messages);
			results.add(messageToPrint);
		}
	}

	public String fizzFuzz(int value, int[] divisors, String[] messages) {
		if ((divisors == null) || (messages == null))
			return "";
		int index = divisors.length;
		while (--index >= 0)
			if ((value % divisors[index]) == 0)
				return messages[index];
		return Integer.toString(value);
	}
}