package Übungsblatt2;

import java.util.Scanner;

public class Aufgabe_8 {

	public static void main(String[] args) {

		String vorname;
		String nachname;

		System.out.println("Simon Drapp");
		System.out.println();
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter your first name: ");
		vorname = scanner.next();

		System.out.print("Enter your last name: ");
		nachname = scanner.next();
		System.out.println();

		System.out.println("Your name is: " + vorname + " " + nachname);
		System.out.println("Your name is: " + nachname + " " + vorname);

		scanner.close();

	}

}