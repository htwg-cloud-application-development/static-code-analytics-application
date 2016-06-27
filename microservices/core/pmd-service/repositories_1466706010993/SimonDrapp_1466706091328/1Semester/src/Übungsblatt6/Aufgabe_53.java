package Übungsblatt6;

import java.util.Scanner;

public class Aufgabe_53 {

	public static void main(String[] args) {

		int a;

		Scanner s = new Scanner(System.in);

		do {
			System.out.println("Bitte geben Sie eine Zahl ein: ");
			a = s.nextInt();
		} while (a <= 0);

		PrimeNumber_53 prime = new PrimeNumber_53(a);
	}

}
