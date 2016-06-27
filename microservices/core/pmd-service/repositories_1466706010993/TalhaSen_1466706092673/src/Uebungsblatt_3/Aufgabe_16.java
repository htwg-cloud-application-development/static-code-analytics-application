package Uebungsblatt_3;

import java.util.Scanner;

public class Aufgabe_16 {

	public static void main(String[] args) {
		Scanner weekday = new Scanner(System.in);

		System.out.println("Mit welchem Tag soll die Woche beginnen?(Zahl zwischen 1-7)");
		switch (weekday.nextInt()) {
		case 1:
			System.out.println("Geben sie eine Zahl zwischen 1-7 ein!");
			switch (weekday.nextInt()) {
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
			}
		case 2:
			System.out.println("Geben sie eine Zahl zwischen 1-7 ein!");
			switch (weekday.nextInt()) {
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
			case 7:
				System.out.println("Montag");
				break;
			}
		case 3:
			System.out.println("Geben sie eine Zahl zwischen 1-7 ein!");
			switch (weekday.nextInt()) {
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
			}
		case 4:
			System.out.println("Geben sie eine Zahl zwischen 1-7 ein!");
			switch (weekday.nextInt()) {
			case 1:
				System.out.println("Donnerstag");
				break;
			case 2:
				System.out.println("Freitag");
				break;
			case 3:
				System.out.println("Samstag");
				break;
			case 4:
				System.out.println("Sonntag");
				break;
			case 5:
				System.out.println("Montag");
				break;
			case 6:
				System.out.println("Dienstag");
				break;
			case 7:
				System.out.println("Mittwoch");
				break;
			}
		case 5:
			System.out.println("Geben sie eine Zahl zwischen 1-7 ein!");
			switch (weekday.nextInt()) {
			case 1:
				System.out.println("Freitag");
				break;
			case 2:
				System.out.println("Samstag");
				break;
			case 3:
				System.out.println("Sonntag");
				break;
			case 4:
				System.out.println("Montag");
				break;
			case 5:
				System.out.println("Dienstag");
				break;
			case 6:
				System.out.println("Mittwoch");
				break;
			case 7:
				System.out.println("Donnerstag");
				break;
			}
		case 6:
			System.out.println("Geben sie eine Zahl zwischen 1-7 ein!");
			switch (weekday.nextInt()) {
			case 1:
				System.out.println("Samstag");
				break;
			case 2:
				System.out.println("Sonntag");
				break;
			case 3:
				System.out.println("Montag");
				break;
			case 4:
				System.out.println("Dienstag");
				break;
			case 5:
				System.out.println("Mittwoch");
				break;
			case 6:
				System.out.println("Donnerstag");
				break;
			case 7:
				System.out.println("Freitag");
				break;
			}
		case 7:
			System.out.println("Geben sie eine Zahl zwischen 1-7 ein !");
			switch (weekday.nextInt()) {
			case 1:
				System.out.println("Sonntag");
				break;
			case 2:
				System.out.println("Montag");
				break;
			case 3:
				System.out.println("Dienstag");
				break;
			case 4:
				System.out.println("Mittwoch");
				break;
			case 5:
				System.out.println("Donnerstag");
			case 6:
				System.out.println("Freitag");
				break;
			case 7:
				System.out.println("Samstag");
				break;
			}
			
		}
	}

}
