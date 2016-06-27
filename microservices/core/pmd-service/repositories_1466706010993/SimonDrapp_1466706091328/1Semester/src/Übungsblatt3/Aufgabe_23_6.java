package Übungsblatt3;

import java.util.Scanner;

public class Aufgabe_23_6 {

	public static void main(String[] args) {

		int limit;
		double sum = 0;
		double average = 0;

		Scanner s = new Scanner(System.in);

		System.out.println("Bestimmen Sie die Obergrenze: ");
		limit = s.nextInt();

		for (int i = 1; i <= limit; i++) {

			sum = sum + (Math.pow(i, 2));
			average = sum / limit;

			System.out.print(i + "*" + i);

			if (i < limit) {
				System.out.print("+");
			}
		}
		System.out.println("");
		System.out.println("Die Summe lautet: " + sum);
		System.out.println("Der Durchschnitt lautet: " + average);

	}
}
