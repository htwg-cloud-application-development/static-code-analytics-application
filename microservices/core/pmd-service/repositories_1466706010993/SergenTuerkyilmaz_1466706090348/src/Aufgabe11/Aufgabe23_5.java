package Aufgabe11;

import java.util.Scanner;

public class Aufgabe23_5 {

	public static void main(String[] args) {

		Scanner s = new Scanner(System.in);

		int a; // a ist meine Obergrenze
		int b = 0;// es fängt bei 0 an zu zählen

		System.out.println("Geben Sie eine Obergrenze ein: ");

		a = s.nextInt();

		if (a >= 7) { // die eingabe muss mindestens die Zahl 7 sein!

			for (int i = 7; i <= a; i += 7) { // i+=7 bedeutet das i in siebener
												// schritten hochgezählt wird

				b = b + i;
				System.out.println(i);

			}

			System.out.println("Das Ergebnis der Summe ist:" + b);
			System.out.println("Der Durschnitt lautet: " + (float) b / a);
		}

		else {
			System.out.println("Bitte mindestens die Zahl 7 eingeben!");
		}
	}

}