package Übungsblatt3;

import java.util.Scanner;

public class Aufgabe_18 {

	public static void main(String[] args) {

		double weight;

		Scanner s = new Scanner(System.in);

		System.out.println("Enter the weight of the package in kg: ");
		weight = s.nextDouble();

		if (weight <= 2) {

			System.out.println("The shipping charges amount to 4,99 EUR");
		}

		else if (weight <= 5) {

			System.out.println("The shipping charges amount to 5,99 EUR");
		}

		else if (weight <= 10) {

			System.out.println("The shipping charges amount to 7,99 EUR");
		}

		else if (weight <= 31.5) {

			System.out.println("The shipping charges amount to 13,99 EUR");
		}

		else if (weight > 31.5) {

			System.out.println("The weight exceeds the maximum weight");
		}
	}

}