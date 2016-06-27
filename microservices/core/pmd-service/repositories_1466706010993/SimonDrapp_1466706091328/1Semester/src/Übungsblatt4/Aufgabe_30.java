package Übungsblatt4;

import java.util.ArrayList;
import java.util.Scanner;

public class Aufgabe_30 {

	public static void main(String[] args) {

		int limit = 1001;
		int zahl;

		boolean[] primzahlArray = new boolean[limit];

		ArrayList<Integer> list1 = new ArrayList<Integer>();
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		Scanner s = new Scanner(System.in);

		System.out.println("Geben Sie eine Ganzzahl zwischen 1 und 1000 ein: ");
		zahl = s.nextInt();

		for (int i = 1; i < primzahlArray.length; i++) {

			if (i == 1) {

				primzahlArray[i] = false;
			} else {
				primzahlArray[i] = true;
				for (int j = 2; j <= i; j++) {

					if (i % j == 0 && j != i) {
						primzahlArray[i] = false;
					} else if (i < zahl && primzahlArray[i]) {

						list1.add(i);
						break;
					}
				}
			}
		}
		if (primzahlArray[zahl]) {

			System.out.println(zahl + " ist eine Primzahl");
		}

		else {
			System.out.println(zahl + " ist keine Primzahl");

			// for (int i = 0; i < list1.size(); i++) {
			//
			// System.out.print(list1.get(i) + " ");
			// }

			if (zahl == 1) {
				System.out.println("keine Zerlegung möglich");
			} else if (zahl < 1) {
				System.out.println("Bitte geben Sie eine Zahl zwischen 1 und 1000 ein");
			}

			int zahl1 = zahl;
			int i = 0;
			while (zahl1 != 1 && zahl > 0) {

				if (zahl1 % list1.get(i) == 0) {

					zahl1 = zahl1 / list1.get(i);

					list2.add(list1.get(i));

				} else {

					i++;

				}

			}

			if (zahl > 1) {

				System.out.println("Die Primzahlzerlegung lautet: ");
				for (int j = 0; j < list2.size(); j++) {

					System.out.print(list2.get(j));

					if (j < list2.size() - 1) {

						System.out.print("*");
					}
				}
			}
		}

	}
}
