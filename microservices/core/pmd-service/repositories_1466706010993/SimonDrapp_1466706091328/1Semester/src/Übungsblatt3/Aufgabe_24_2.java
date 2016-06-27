package Übungsblatt3;

import java.util.Scanner;

public class Aufgabe_24_2 {

	public static void main(String[] args) {

		double pi = 0;
		double x = 1;
		double pi1;
		int zahl;
		int zahl1;
		double abweichung1;
		double abweichung2;
		double differenz;

		Scanner s = new Scanner(System.in);

		System.out.println("Geben Sie die Anzahl der Reihenglieder an: ");
		zahl = s.nextInt();

		for (int i = 1; i <= (zahl / 2); i++) {

			double a = (1 / x);
			x = x + 2;
			double b = -(1 / x);
			x = x + 2;

			pi = pi + a + b;

		}

		pi1 = 4 * pi;
		abweichung1 = 100 - (pi1 / Math.PI) * 100;

		System.out.println("Mein Pi: " + pi1);
		System.out.println("PC-Pi: " + Math.PI);

		System.out.println();

		System.out.println("Abweichung Mein Pi vs. PC-Pi: " + abweichung1 + " %");

		System.out.println();

		System.out.println("Geben Sie die Anzahl der Reihenglieder an: ");
		zahl1 = s.nextInt();

		x = 1;
		pi = 0;

		for (int i = 1; i <= (zahl1 / 2); i++) {

			double a = (1 / x);
			x = x + 2;
			double b = -(1 / x);
			x = x + 2;

			pi = pi + a + b;

		}

		pi1 = 4 * pi;

		abweichung2 = 100 - (pi1 / Math.PI) * 100;

		System.out.println();

		System.out.println("Mein Pi: " + pi1);
		System.out.println("PC-Pi: " + Math.PI);

		System.out.println();

		System.out.println("Abweichung Mein Pi vs. PC-Pi: " + abweichung2 + " %");

		differenz = Math.abs(abweichung1 - abweichung2);

		System.out.println();

		System.out.println("Abweichung Anzahl Reihenglieder: " + differenz + "%");

	}

}