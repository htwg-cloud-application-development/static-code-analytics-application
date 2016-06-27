package Übungsblatt4;

import java.util.Scanner;

public class Aufgabe_28 {

	public static void main(String[] args) {

		int zahl;
		int sum = 0;
		int index1 = 0;
		int index2 = 0;
		int kleiner = 0;
		int groeßer = 0;

		Scanner s = new Scanner(System.in);

		System.out.println("Bestimmen Sie die Feldgröße: ");
		zahl = s.nextInt();

		if (zahl <= 0) {

			System.out.println("Die Feldgröße muss mindestens 1 betragen!");
		} else {
			int[] a = new int[zahl];

			for (int i = 0; i < a.length; i++) {// for-Schleife zur Erzeugung
												// der Zufallszahlen

				a[i] = (int) ((Math.random() * 200) + 1) - 101;
				sum = sum + a[i];
				System.out.println("Index" + i + ": " + a[i]);

			}

			kleiner = a[0];
			groeßer = a[0];

			for (int k = 0; k < a.length; k++)// erste for-Schleife

				for (int j = k + 1; j < a.length; j++)// zweite for-Schleife

					if (kleiner > a[j]) {

						kleiner = a[j];
						index1 = j;

					} else if (groeßer < a[j]) {

						groeßer = a[j];
						index2 = j;

					}
			// else {
			// continue;
			// }

		}

		System.out.println();
		System.out.println("Summe: " + sum);
		System.out.println("Durchschnitt: " + ((double) sum / zahl));
		System.out.println();
		System.out.println("kleinste Index der kleinsten Komponenten: " + index1 + "\nWert: " + kleiner);
		System.out.println("kleinste Index der größten Komponenten: " + index2 + "\nWert: " + groeßer);
	}

}
