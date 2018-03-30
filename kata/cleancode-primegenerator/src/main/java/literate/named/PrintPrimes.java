package literate.named;

public class PrintPrimes {

	public static void main(String[] args) {
		final int numberOfPrimes = 1000;
		final int rowsPerPage = 50;
		final int columnsPerPage = 4;
		final int ORDMAX = 30;
		int primes[] = new int[numberOfPrimes + 1];
		int pageNumber;
		int pageOffset;
		int currentRowOffset;
		int column;
		int possiblePrime;
		int currentPrimeIndex;
		boolean isPrime;
		int ord;
		int square;
		int n;
		int multiples[] = new int[ORDMAX + 1];

		possiblePrime = 1;
		currentPrimeIndex = 1;
		primes[1] = 2;
		ord = 2;
		square = 9;

		while (currentPrimeIndex < numberOfPrimes) {
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
		{
			pageNumber = 1;
			pageOffset = 1;
			while (pageOffset <= numberOfPrimes) {
				System.out.println("The First " + numberOfPrimes
						+ " Prime Numbers --- Page " + pageNumber);
				System.out.println();
				for (currentRowOffset = pageOffset; currentRowOffset < pageOffset + rowsPerPage; currentRowOffset++) {
					for (column = 0; column < columnsPerPage; column++)
						if (currentRowOffset + column * rowsPerPage <= numberOfPrimes)
							System.out.format("%10d", primes[currentRowOffset + column * rowsPerPage]);
					System.out.println("");
				}
				System.out.println("\n");
				pageNumber = pageNumber + 1;
				pageOffset = pageOffset + rowsPerPage * columnsPerPage;
			}
		}
	}
}
