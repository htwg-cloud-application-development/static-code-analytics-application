package Übungsblatt3;

import java.util.Scanner;

public class Aufgabe_10_1 {

	public static void main(String[] args) {

		int number1;
		int number2;
		double result;

		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter the first number: ");
		number1 = scanner.nextInt();

		System.out.print("Enter the second number: ");
		number2 = scanner.nextInt();

		if (number2 == 0) {

			System.out.println("The numerator is not allowed to be 0!");

		} else {

			result = (double) number1 / (double) number2;
			System.out.println("The result of the divison is: " + result);

		}

		scanner.close();
	}
}