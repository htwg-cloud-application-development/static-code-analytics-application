package Übungsblatt3;

import java.util.Scanner;

public class Aufgabe_23_2 {

	public static void main(String[] args) {

		int i = 1;
		int limit;
		int sum = 0;
		double average = 0;

		Scanner s = new Scanner(System.in);

		System.out.println("Bestimmen Sie die Obergrenze: ");
		limit = s.nextInt();

		while (i <= limit) {

			sum = sum + i;
			average = sum / limit;
			i++;

		}

		System.out.println("Die Summe lautet: " + sum);
		System.out.println("Der Durchschnitt lautet: " + average);
	}
}
