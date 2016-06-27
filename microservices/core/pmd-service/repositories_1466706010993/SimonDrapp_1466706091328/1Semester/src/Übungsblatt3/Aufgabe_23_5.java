package Übungsblatt3;

import java.util.Scanner;

public class Aufgabe_23_5 {

	public static void main(String[] args) {

		int limit;
		double sum = 0;
		double average = 0;
		int zaehler = 0;

		Scanner s = new Scanner(System.in);

		System.out.println("Bestimmen Sie die Obergrenze: ");
		limit = s.nextInt();

		for (int i = 7; i <= limit; i = i + 7) {

			zaehler++;
			sum = sum + i;
			average = sum / zaehler;

		}
		System.out.println("Die Summe lautet: " + sum);
		System.out.println("Der Durchschnitt lautet: " + average);
	}
}
