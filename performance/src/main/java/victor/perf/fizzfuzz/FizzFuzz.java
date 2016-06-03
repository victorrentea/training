package victor.perf.fizzfuzz;

public class FizzFuzz {
	public static void main(String[] args) {
		int[] divisors = { 3, 5, 15 };
		String[] messages = { "fizz", "fuzz", "fizz fuzz" };
		FizzFuzz fuzz = new FizzFuzz(1, 100);
		fuzz.printNumbersDivisibleByN(divisors, messages);
	}

	int lowerRange, upperRange;

	public FizzFuzz(int lowerRange, int upperRange) {
		this.lowerRange = lowerRange;
		this.upperRange = upperRange;
	}

	public int getLowerRange() {return this.lowerRange;}
	public int getUpperRange() {return this.upperRange;}

	public void printNumbersDivisibleByN(int[] n, String[] messages) {
		if ((n == null) || (messages == null)) return;
		for (int i = getLowerRange(); i < getUpperRange(); i++) {
			String messageToPrint = fizzFuzz(i, n, messages);
			System.out.println(messageToPrint);
		}
	}

	public String fizzFuzz(int value, int[] divisors, String[] messages) {
		if ((divisors == null) || (messages == null)) return "";
		int index = divisors.length;
		while (--index >= 0)
			if ((value % divisors[index]) == 0)
				return messages[index];
		return Integer.toString(value);
	}
}
