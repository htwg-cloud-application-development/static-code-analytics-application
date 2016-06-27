package Aufgabe11;

import java.util.Scanner;

public class Aufgabe23_3 {

	public static void main(String[] args) {

		Scanner s = new Scanner(System.in);

		int a; // a ist meine Obergrenze
		int b = 0;// es fängt bei 0 an zu zählen

		System.out.println("Geben Sie eine Obergrenze ein: ");

		a = s.nextInt();
		int i = 1;			// zählt später auf b 1 dazu

		do {
			System.out.println(i);		// dies wird ausgeführt wenn die Eingabe größer gleich i ist.
			b = b + i;
			i++;

		} while (i <= a);				

		System.out.println("Das Ergebnis der Summe ist:" + b);
		System.out.println("Der Durschnitt lautet: " + (float) b / a);

	}
}

