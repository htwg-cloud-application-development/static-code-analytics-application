package Übungsblatt6;

public class PrimeNumber {

	int value = 7;

	boolean isPrime() {

		boolean primeNumber = true;

		if (value == 1) {
			primeNumber = false;
		}

		for (int i = 2; i < value; i++) {
			if (value % i == 0) {
				primeNumber = false;
				break;
			}
		}
		return primeNumber;
	}
}
