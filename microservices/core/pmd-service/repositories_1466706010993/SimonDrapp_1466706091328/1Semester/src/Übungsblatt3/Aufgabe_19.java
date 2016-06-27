package Übungsblatt3;

import java.util.Scanner;

public class Aufgabe_19 {

	public static void main(String[] args) {

		double exchangeRate;
		int direction;
		double amount;

		Scanner s = new Scanner(System.in);

		System.out.println("Enter the exchange rate USD/EUR: ");
		exchangeRate = s.nextDouble();

		System.out.println("Choose the direction of the conversion");
		System.out.println("1 for USD --> EUR");
		System.out.println("2 for EUR --> USD");
		direction = s.nextInt();

		System.out.println();

		if (direction == 1) {

			System.out.println("Enter the amount in USD: ");
			amount = s.nextDouble();

			System.out.println();

			double a = amount * exchangeRate;

			System.out.println(amount + " USD are " + (float) a + " EUR");

		}

		else if (direction == 2) {

			System.out.println("Enter the amount in EUR: ");
			amount = s.nextDouble();

			System.out.println();

			double a = amount / exchangeRate;

			System.out.println(amount + " EUR are " + (float) a + " USD");
		}

	}

}
