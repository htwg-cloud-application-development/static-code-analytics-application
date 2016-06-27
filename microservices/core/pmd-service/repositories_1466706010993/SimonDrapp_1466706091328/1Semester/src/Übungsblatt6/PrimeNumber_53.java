package Übungsblatt6;

public class PrimeNumber_53 {

	int value;
	boolean isPrime;

	public PrimeNumber_53(int zahl) {

		value = zahl; // Instanzvariable = Parametername

		if (zahl == 1) {
			isPrime = false;
		}

		for (int i = 2; i < zahl; i++) {

			if (zahl % i == 0) {
				isPrime = false;
				break;
			} else {
				isPrime = true;
			}
		}
		System.out.println(zahl + " ist eine Primzahl --> " + isPrime);

	}
}
