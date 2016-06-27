package Aufgabe11;

import java.util.Scanner;

public class Aufgbe21_2 {

	public static void main(String[] args) {

		Scanner s = new Scanner(System.in);

		System.out.println("Geben Sie hier eine römische Zahl ein: ");

		String y = s.next();

		int a = 0;
		int b = 0;
		int c = 0;

		for (int i = y.length() - 1; i >= 0; i--) { // i = länge der Eingabe ;
													// die vorletzte Ziffer wird
													// als Startwert festgelegt.
													// i-- zählt auf 0 runter.

			switch (y.charAt(i)) { 		

			case 'I':
				a = 1;					// Switch-Case Anweisung um die einzelnen Ziffern zu deklarieren
										// diese werden durch die If-Else Anweisung berechnet und später in syso ausgegeben
				break;

			case 'V':
				a = 5;
				break;

			case 'X':
				a = 10;
				break;
				
			case 'L':
				a = 50;
				break;
				
			case 'C':
				a = 100;
				break;
				
			case 'D':
				a = 500;
				break;
				
			case 'M':
				a = 1000;
				break;

			}

			if (c > a) {
				b = b - a; // für beispielsweis IV --> " 5-1"
			}

			else {
				b = b + a;// für beispielsweise VI --> " 5 +1 "
			}
			c = a;

		}
		System.out.println(b);
	}
}
