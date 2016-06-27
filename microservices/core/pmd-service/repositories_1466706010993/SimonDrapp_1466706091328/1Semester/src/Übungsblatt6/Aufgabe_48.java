package Übungsblatt6;

import java.util.Scanner;

public class Aufgabe_48 {

	static Scanner a = new Scanner(System.in);

	public static void main(String[] args) {

		String postcode1;
		int postcode;
		int numberOfLetters = 0;
		boolean ziffer;

		System.out.println("Geben Sie eine Postleitzahl ein: ");
		postcode1 = a.next(); 	// String-Variable als Eingabe, damit man
								// Eingabe auf Fehler überprüfen kann

		for (int i = 0; i < postcode1.length(); i++) { // Überprüfung auf Anzahl
														// der eingegebenen
														// Zeichen

			numberOfLetters++;
		}
		ziffer = Address.pruefeString(postcode1); // boolesche Methode wird
													// eingelesen

		if (ziffer && numberOfLetters == 5) { // Überprüfung auf beide
												// Bedingungen

			postcode = Integer.parseInt(postcode1);		//String zu int
			System.out.println(Address.postcodeToCity(postcode));	//Klassenmethode Address 
																	//wird aufgerufen
			

		} else {
			System.out.println("falsche Eingabe \nzuviele Zeichen oder Buchstaben verwendet");
		}
	}

}
