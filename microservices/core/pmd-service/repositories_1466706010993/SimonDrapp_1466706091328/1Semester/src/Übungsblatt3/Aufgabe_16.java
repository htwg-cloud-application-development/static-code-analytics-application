package Übungsblatt3;

import java.util.Scanner;

public class Aufgabe_16 {

	public static void main(String[] args) {

		int a;
		String b;

		Scanner s = new Scanner(System.in);

		System.out.println("Bestimmen Sie den Beginn der Woche:");
		b = s.next();

		if (b.equals("Montag")) {

			do {

				System.out.println("Geben Sie eine Zahl zwischen 1 und 7 ein: ");
				a = s.nextInt();
				System.out.println();

				switch (a) {
				case 1:
					System.out.println("Montag");
					break;

				case 2:
					System.out.println("Dienstag");
					break;

				case 3:
					System.out.println("Mittwoch");
					break;

				case 4:
					System.out.println("Donnerstag");
					break;

				case 5:
					System.out.println("Freitag");
					break;

				case 6:
					System.out.println("Samstag");
					break;

				case 7:
					System.out.println("Sonntag");
					break;

				default:
					System.out.println("Die eingegebene Zahl ist ungültig");
					System.out.println("Versuchen Sie es nochmal");
					System.out.println();

				}

			} while (a > 7 | a < 1);
		}

		else if (b.equals("Dienstag")) {

			do {

				System.out.println("Geben Sie eine Zahl zwischen 1 und 7 ein: ");
				a = s.nextInt();
				System.out.println();

				switch (a) {
				case 1:
					System.out.println("Dienstag");
					break;

				case 2:
					System.out.println("Mittwoch");
					break;

				case 3:
					System.out.println("Donnerstag");
					break;

				case 4:
					System.out.println("Freitag");
					break;

				case 5:
					System.out.println("Samstag");
					break;

				case 6:
					System.out.println("Sonntag");
					break;

				case 7:
					System.out.println("Montag");
					break;

				default:
					System.out.println("Die eingegebene Zahl ist ungültig");
					System.out.println("Versuchen Sie es nochmal");
					System.out.println();

				}

			} while (a > 7 | a < 1);
		}

		else if (b.equals("Mittwoch")) {

			do {

				System.out.println("Geben Sie eine Zahl zwischen 1 und 7 ein: ");
				a = s.nextInt();
				System.out.println();

				switch (a) {
				case 1:
					System.out.println("Mittwoch");
					break;

				case 2:
					System.out.println("Donnerstag");
					break;

				case 3:
					System.out.println("Freitag");
					break;

				case 4:
					System.out.println("Samstag");
					break;

				case 5:
					System.out.println("Sonntag");
					break;

				case 6:
					System.out.println("Montag");
					break;

				case 7:
					System.out.println("Dienstag");
					break;

				default:
					System.out.println("Die eingegebene Zahl ist ungültig");
					System.out.println("Versuchen Sie es nochmal");
					System.out.println();

				}

			} while (a > 7 | a < 1);
		}

	}
}