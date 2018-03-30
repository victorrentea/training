package literate.local;

public class PrintPrimes {
	public final static int NUMBER_OF_PRIMES = 1000;
	public final static int ROWS_PER_PAGE = 50;
	public final static int COLUMNS_PER_PAGE = 4;
	public final static int ORDMAX = 30;

	public static void main(String[] args) {
		int primes[] = new int[NUMBER_OF_PRIMES + 1];
		int currentPrimeIndex = 1;
		int possiblePrime = 1;
		boolean isPrime;
		int ord = 2;
		int square = 9;
		int n;
		int multiples[] = new int[ORDMAX + 1];

		primes[1] = 2;

		while (currentPrimeIndex < NUMBER_OF_PRIMES) {
			do {
				possiblePrime = possiblePrime + 2;
				if (possiblePrime == square) {
					ord = ord + 1;
					square = primes[ord] * primes[ord];
					multiples[ord - 1] = possiblePrime;
				}
				n = 2;
				isPrime = true;
				while (n < ord && isPrime) {
					while (multiples[n] < possiblePrime)
						multiples[n] = multiples[n] + primes[n] + primes[n];
					if (multiples[n] == possiblePrime)
						isPrime = false;
					n = n + 1;
				}
			} while (!isPrime);
			currentPrimeIndex = currentPrimeIndex + 1;
			primes[currentPrimeIndex] = possiblePrime;
		}

		
		int pageNumber;
		int pageOffset;
		int rowOffset;
		int column;
		{
			pageNumber = 1;
			pageOffset = 1;
			while (pageOffset <= NUMBER_OF_PRIMES) {
				System.out.println("The First " + NUMBER_OF_PRIMES
						+ " Prime Numbers --- Page " + pageNumber);
				System.out.println();
				for (rowOffset = pageOffset; rowOffset < pageOffset + ROWS_PER_PAGE; rowOffset++) {
					for (column = 0; column < COLUMNS_PER_PAGE; column++)
						if (rowOffset + column * ROWS_PER_PAGE <= NUMBER_OF_PRIMES)
							System.out.format("%10d", primes[rowOffset + column * ROWS_PER_PAGE]);
					System.out.println("");
				}
				System.out.println("\n");
				pageNumber = pageNumber + 1;
				pageOffset = pageOffset + ROWS_PER_PAGE * COLUMNS_PER_PAGE;
			}
		}
	}
}
