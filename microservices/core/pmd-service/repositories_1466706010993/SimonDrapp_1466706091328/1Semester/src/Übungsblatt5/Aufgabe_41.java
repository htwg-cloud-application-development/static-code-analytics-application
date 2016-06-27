package Übungsblatt5;

import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

public class Aufgabe_41 {

	static String postcode;
	static String town = "";
	static int numberOfLetters = 0;
	static boolean ziffer;

	static Scanner a = new Scanner(System.in);

	public static void main(String[] args) {

		System.out.println("Geben Sie eine Postleitzahl ein: ");
		postcode = a.next();

		for (int i = 0; i < postcode.length(); i++) { // Überprüfung auf Anzahl der eingegebenen Zeichen

			numberOfLetters++;
		}
		ziffer = pruefeString(postcode); // boolesche Methode wird eingelesen

		if (ziffer && numberOfLetters == 5) { // Überprüfung auf beide Bedingungen
			leseDatei();
		} else {
			System.out.println("falsche Eingabe \nzuviele Zeichen oder Buchstaben verwendet");
		}
	}

	public static void leseDatei() {

		try {

			File plz = new File("C:\\Users\\simon\\Desktop\\Programmieren\\OpenGeoDB-plz-ort-de.csv");
			Scanner s = new Scanner(plz, "UTF-8"); // Scanner wird die Datei plz übergeben und der Zeichenkodierung utf-8 zugewiesen

			FileReader f = new FileReader(plz);
			BufferedReader b = new BufferedReader(f);

			while (s.hasNextLine()) {	//solange es eine nächste Zeile gibt

				String zeile = s.nextLine();	//der String-Variablen Zeile wird die aktuelle Zeile übergeben

				if (zeile.contains(postcode)) {

					System.out.println(zeile.substring(5, zeile.length())); // ab dem 5 bis zum letzten Zeichen wird ausgegeben

				}
			}

			b.close();
		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}

	public static boolean pruefeString(String postcode) {

		try {
			Integer.parseInt(postcode); // Überprüfung ob die String-Variable nur Ziffern enthält
			ziffer = true;

		} catch (NumberFormatException e) {

			ziffer = false;

		}

		return ziffer;
	}

}
