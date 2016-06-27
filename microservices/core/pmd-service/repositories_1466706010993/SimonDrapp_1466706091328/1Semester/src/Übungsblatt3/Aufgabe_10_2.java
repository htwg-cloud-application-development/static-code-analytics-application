package Übungsblatt3;

import java.util.Scanner;

public class Aufgabe_10_2 {

	public static void main(String[] args) {

		double number1;
		double number2;
		int result;
		double result1;
		double postcomma;

		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter the first number: ");
		number1 = scanner.nextDouble();

		System.out.print("Enter the second number: ");
		number2 = scanner.nextDouble();

		result1 = (double) number1 * (double) number2;
		result = (int) result1;
		postcomma = result1 - result;

		System.out.println("The result of the multiplication is " + result);
		System.out.println("The postcomma portion is " + postcomma);

		scanner.close();

	}

}
