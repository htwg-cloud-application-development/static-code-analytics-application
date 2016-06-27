package Übungsblatt3;

public class Aufgabe_24_1 {

	public static void main(String[] args) {

		double pi = 0;
		double x = 1;
		double pi1;

		for (int i = 1; i < 1000; i++) {

			double a = (1 / x);
			x = x + 2;
			double b = -(1 / x);
			x = x + 2;

			pi = pi + a + b;

		}

		pi1 = 4 * pi;

		System.out.println("Pi ist ungefähr " + pi1);

	}

}