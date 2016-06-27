package Übungsblatt4;

import java.util.Scanner;

public class Aufgabe_26 {

	public static void main(String[] args) {

		int zahl;

		Scanner s = new Scanner(System.in);

		System.out.println("Bestimmen Sie die Feldgröße: ");
		zahl = s.nextInt();

		if (zahl <= 0) {

			System.out.println("Die Feldgröße muss mindestens 1 betragen!");
		}

		else {

			int[] a = new int[zahl];

			for (int i = 0; i < a.length; i++) {

				a[i] = (int) ((Math.random() * 200) + 1) - 101; // Zufallszahlen
																// von -100 bis
																// +100
				System.out.println("Index" + i + ": " + a[i]);
			}

		}
	}

}
