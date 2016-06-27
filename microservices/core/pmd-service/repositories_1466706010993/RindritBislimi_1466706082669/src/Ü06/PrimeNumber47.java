package Ü06;

public class PrimeNumber47 {

	static int primzahl;
	static boolean test = false;
	
	public static boolean isPrime(int num) {
		int teiler;
		if (num != 1) {
			teiler = 0;
			for (int zähler = 1; zähler <= num; zähler++) {
				if (num % zähler == 0) {
					teiler++;
				}
			}
			if (teiler == 2) {
				test = true;
				return test;
			}
		}
		return test;
	}
}
