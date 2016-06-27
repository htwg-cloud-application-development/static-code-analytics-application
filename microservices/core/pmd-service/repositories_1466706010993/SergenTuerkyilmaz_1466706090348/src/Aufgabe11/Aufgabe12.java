package Aufgabe11;

import java.util.Scanner;

public class Aufgabe12 {

	public static void main(String[] args) {

		Scanner s = new Scanner(System.in);

		System.out.println("Hier zwei Zahlen eingeben:");

		int a = s.nextInt();
		int b = s.nextInt();

		if (!(!(a == 42 && !(b == 42)) && !(!(a == 42) && b == 42))) {
			// a == 42 und b(nicht) == 42 oder a (nicht) ==42 und b == 42 
			// mit Hilfe de Morgan gelöst

			System.out.println("Das ist die Antwort");

		}

	}
}
