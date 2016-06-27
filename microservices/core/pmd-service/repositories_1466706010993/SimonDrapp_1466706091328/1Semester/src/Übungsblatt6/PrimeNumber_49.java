package Übungsblatt6;

public class PrimeNumber_49 {

	public int value;

	public boolean isPrime() { // Instanzmethode

		boolean isprime = true;

		if (value == 1) {
			isprime = false;
		}

		for (int i = 2; i < value; i++) {

			if (value % i == 0) {
				isprime = false;
			}
		}

		return isprime;

	}
}
