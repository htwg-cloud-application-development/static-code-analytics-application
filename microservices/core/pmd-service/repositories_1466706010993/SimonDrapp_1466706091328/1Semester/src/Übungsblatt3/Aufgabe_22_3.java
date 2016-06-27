package Übungsblatt3;

import java.util.Scanner;
import java.util.ArrayList;

public class Aufgabe_22_3 {

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

		for (int i = 0; i < list1.size(); i++) {

			System.out.print(list1.get(i) + " ");
		}
	}
}