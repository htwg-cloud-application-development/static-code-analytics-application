package Übungsblatt6;

public class PrimeNumber {

	public static boolean isPrime(int zahl) { //Klassenmethode --> static

		boolean isprime = true;

		if (zahl == 1) {
			isprime = false;
		}

		for (int i = 2; i < zahl; i++) {

			if (zahl % i == 0) {
				isprime = false;
				break;					//wenn es einmal in die if-Anweisung geht  
			}							//ist es immer erfüllt

		}

		return isprime;

	}

}
