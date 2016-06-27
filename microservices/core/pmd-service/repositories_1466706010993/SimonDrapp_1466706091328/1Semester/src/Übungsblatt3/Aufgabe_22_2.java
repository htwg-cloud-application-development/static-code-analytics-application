package Übungsblatt3;

import java.util.Scanner;
import java.util.ArrayList;

public class Aufgabe_22_2 {

	public static void main(String[] args) {

		int zahl;
		boolean isprimnb = true;

		Scanner s = new Scanner(System.in);

		System.out.println("Geben sie eine Ganzzahl ein: ");
		zahl = s.nextInt();
		ArrayList<Integer> list1 = new ArrayList<Integer>();

		for (int i = 2; i < zahl; i++) {

			if (zahl % i == 0) {
				isprimnb = false;
			}

			boolean kleinprim = true;

			for (int j = 2; j < i; j++) {

				if (i % j == 0) {

					kleinprim = false;

				}
			}

			if (kleinprim) {

				list1.add(i);
				
			}
		}

		if (isprimnb && zahl != 1) {
			System.out.println(zahl + " ist eine Primzahl");
		} else {

			System.out.println(zahl + " ist KEINE Primzahl");
		}

		System.out.println("");
		System.out.println("Primzahlen kleiner gleich der eingegebenen Zahl: ");

		for (int i = 0; i < list1.size(); i++) {

			System.out.print(list1.get(i) + " ");
		}

		ArrayList<Integer> list2 = new ArrayList<Integer>(); // neue ArrayList

		int zahl1 = zahl; // zahl1 gleichsetzen mit zahl / Wert nicht verlieren
		int i = 0; // Index null setzen

		while (zahl1 != 1) { // Ende divison

			if (zahl1 % list1.get(i) == 0) { // zahl1 durch gespeicherte
												// variable (Primzahl)in
												// ArrayListe
												// Überprüfung auf restzahl

				zahl1 = zahl1 / list1.get(i); // Zahl durch gespeicherte
												// Variable (Primzahl)in
												// ArrayList
				list2.add(list1.get(i)); // Primzahl der Liste2 hinzufügen
			} else {

				i++; // Index erhöhen
			}

		}

		System.out.println("Die Primzahlzerlegung lautet: ");
		for (int j = 0; j < list2.size(); j++) {

			System.out.print(list2.get(j));

			if (j < list2.size() - 1) {

				System.out.print("*");
			}
		}
	}
}