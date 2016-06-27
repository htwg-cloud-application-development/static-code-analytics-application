package Übungsblatt3;

import java.util.Scanner;

public class Aufgabe_12 {

	public static void main(String[] args) {

		int a;
		int b;

		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter the first number: ");
		a = scanner.nextInt();

		System.out.print("Enter the second number: ");
		b = scanner.nextInt();

		if (!(!(a == 42 && !(b == 42)) && (!(!(a == 42) && b == 42)))) {

			System.out.println("Das ist die Antwort");

		} else {
			System.out.println("Versuchen Sie es nochmal");
		}
	}

}
