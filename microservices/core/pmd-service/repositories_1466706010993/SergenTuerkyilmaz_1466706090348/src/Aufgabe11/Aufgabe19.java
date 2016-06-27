package Aufgabe11;

import java.util.Scanner;

public class Aufgabe19 {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		System.out.print("Geben Sie hier den Umrechnungskurs ein: ");

		double exchangeRate = s.nextDouble();

		System.out.print("Geben Sie die Zielwährung an,  € oder $:");

		String x = s.next();
		String $ = x;
		String € = x;

		System.out.print("Geben Sie den Ausgangsbetrag ein: ");
		double Money = s.nextDouble();


		if (x == $)
			System.out.println("Ihr Ergebnis lauet " + exchangeRate * Money +"€");

		else

		if (x == €)
			System.out.println("Ihr Ergebnis lautet "+ Money / exchangeRate+ "$");

	}

}

// aktueller Kurs 1,1303