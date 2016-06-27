package Übungsblatt3;

import java.util.Scanner;

public class Aufgabe_13_2 {

	public static void main(String[] args) {

		int a;
		int b;
		int c;

		Scanner s = new Scanner(System.in);

		System.out.print("Enter the first number: ");
		a = s.nextInt();

		System.out.print("Enter the second number: ");
		b = s.nextInt();

		System.out.print("Enter the third number: ");
		c = s.nextInt();

		String min = (a < b) ? ((b < c) ? "The numbers are rising  " : "The numbers aren`t rising")
				: "The numbers aren`t rising";

		System.out.println();
		System.out.println(min);

	}
}
