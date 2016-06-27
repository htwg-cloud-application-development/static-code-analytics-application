package Uebungsblatt_3;

import java.util.Scanner;

public class Aufgabe_23_5 {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int a = 0;
		int b; // Obergrenze
		System.out.println("Geben sie die Obergrenze ein:");
		b = s.nextInt();

		if (b >= 7) {

			for (int i = 7; i <= b; i += 7) {
				a = (a + i);
				System.out.println(i);
			}

			System.out.println("Die Summe ist:" + a);
			System.out.println("Der Mittelwert lautet:" + ((float) a / 2));
			System.out.println("Der Durchschnitt lautet:" + ((float) a / b));

		}

		else {
			System.out.println("Bitte mindestens die Zahl 7 eingeben");

		}
	}

}
