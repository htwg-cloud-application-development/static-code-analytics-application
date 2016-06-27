package Uebungsblatt_3;

import java.util.Scanner;

public class Aufgbabe_15_1 {

	public static void main(String[] args) {
		Scanner s = new java.util.Scanner(System.in);

		boolean result = false;

		System.out.println("Geben sie eine Zahl ein:"); // Abfrage der Zahl.

		do {
			int weekday = s.nextInt();

			switch (weekday) {
			case 1:
				System.out.println("Montag");
				result = true;
				break;
			case 2:
				System.out.println("Dienstag");
				result = true;
				break;
			case 3:
				System.out.println("Mittwoch");
				result = true;
				break;
			case 4:
				System.out.println("Donnerstag");
				result = true;
				break;
			case 5:
				System.out.println("Freitag");
				result = true;
				break;
			case 6:
				System.out.println("Samstag");
				result = true;
				break;
			case 7:
				System.out.println("Sonntag");
				result = true;
				break;
			default:
				System.out.println("Diese Zahl beinhaltet keinen Wochentag");
				result = false;
			}
		} while (result == false); // Falls die eingegebene Zahl falsch ist
									// wiederholt es sich.
			
	}

}
