package Aufgabe11;

import java.util.Scanner;

public class Aufgabe15 {

	public static void main(String[] args) {

		System.out.println("Hier einen Wochentag eingeben: ");

		Scanner s = new Scanner(System.in);

		int Tag;
		int x;
		String Wochentag;

		do {
			// Aufgabe 16
			Wochentag = s.next();

			switch (Wochentag) {

			case ("Montag"):
				x = 0;
				break;

			case ("Dienstag"):
				x = 1;
				break;

			case ("Mittwoch"):
				x = 2;
				break;

			case ("Donnerstag"):
				x = 3;
				break;

			case ("Freitag"):
				x = 4;
				break;
			case ("Samstag"):
				x = 5;
				break;

			case ("Sonntag"):
				x = 6;
				break;

			default:
				x = 99;
				System.out.println("geben Sie einen anderen Tag ein!: ");
				break;
			}

			// Aufgabe 15 und 16

		} while (x == 99);

		{
			System.out.println("Hier eine Zahl von 1-7 eingeben: ");

			do {

				Tag = s.nextInt();

				switch (Tag + x +1) {

				case (1):
					System.out.println("Montag");
					break;

				case (2):
					System.out.println("Dienstag");
					break;

				case (3):
					System.out.println("Mittwoch");
					break;

				case (4):
					System.out.println("Donnerstag");
					break;

				case (5):
					System.out.println("Freitag");
					break;

				case (6):
					System.out.println("Samstag");
					break;

				case (7):
					System.out.println("Sonntag");
					break;

				case (8):
					System.out.println("Montag");
					break;

				case (9):
					System.out.println("Dienstag");
					break;

				case (10):
					System.out.println("Mittwoch");
					break;

				case (11):
					System.out.println("Donnerstag");
					break;

				case (12):
					System.out.println("Freitag");
					break;

				case (13):
					System.out.println("Samstag");
					break;

				case (14):
					System.out.println("Sonntag");
					break;

				default:
					System.out.println("geben Sie eine andere Zahl ein!: ");
					break;

				}

			} while (Tag > 7 || Tag < 1);
		}
	}
}
