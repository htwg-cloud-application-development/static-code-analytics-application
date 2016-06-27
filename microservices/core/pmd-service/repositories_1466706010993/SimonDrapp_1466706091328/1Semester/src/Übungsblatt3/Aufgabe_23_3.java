package Übungsblatt3;

import java.util.Scanner;

public class Aufgabe_23_3 {

	public static void main(String[] args) {

		int i = 0;
		int limit;
		int sum = 0;
		double average = 0;

		Scanner s = new Scanner(System.in);

		System.out.println("Bestimmen Sie die Obergrenze: ");
		limit = s.nextInt();

		do {

			sum = sum + i;

			i++;
		} while (i <= limit);

		average = sum / limit;

		System.out.println("Die Summe lautet: " + sum);
		System.out.println("Der Durchschnitt lautet: " + average);
	}
}