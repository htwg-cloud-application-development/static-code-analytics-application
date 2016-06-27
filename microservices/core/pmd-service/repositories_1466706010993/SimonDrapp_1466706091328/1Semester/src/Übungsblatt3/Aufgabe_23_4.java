package Übungsblatt3;

import java.util.Scanner;

public class Aufgabe_23_4 {

	public static void main(String[] args) {

		int limit;
		int sum = 0;
		double average = 0;
		int zaehler = 0;

		Scanner s = new Scanner(System.in);

		System.out.println("Bestimmen Sie die Obergrenze: ");
		limit = s.nextInt();

		for (int i = 1; i <= limit; i = i + 2) {

			zaehler++;
			sum = sum + i;
			average = sum / zaehler;

		}

		System.out.println("Die Summe lautet: " + sum);
		System.out.println("Der Durchschnitt lautet: " + average);
	}
}