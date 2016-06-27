package Übungsblatt6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Address {

	public String name;
	public String street;
	public String city;
	public int postcode;
	public String email;
	public String comment;
	public static boolean ziffer;			
	public static int numberOfLetters = 0;

	/** Constructor */
	public Address() {
		this.name = "";
		this.street = "";
		this.city = "";
		this.postcode = 0;
		this.email = "";
		this.comment = "";
	}

	/** Instance method */
	public void setAddress(String aStreet, int aPostcode, String aCity) {
		this.street = aStreet;
		this.postcode = aPostcode;
		this.city = aCity;
	}

	/** Instance method */
	public String fullAddress() {
		return name + ", " + street + ", " + postcode + " " + city;
	}

	/** Class method */
	public static String postcodeToCity(int postcode) {

		String zeile1 = ""; // neue Variable die zurückgegeben wird

		try {

			File plz = new File("C:\\Users\\simon\\Desktop\\Programmieren\\OpenGeoDB-plz-ort-de.csv");
			Scanner s = new Scanner(plz, "UTF-8"); // Scanner wird die Datei plz
													// übergeben und der
													// Zeichenkodierung utf-8
													// zugewiesen

			FileReader f = new FileReader(plz);
			BufferedReader b = new BufferedReader(f);

			while (s.hasNextLine()) { // solange es eine nächste Zeile gibt

				String zeile = s.nextLine(); // der String-Variablen zeile wird
											// die aktuelle Zeile übergeben

				if (zeile.contains(Integer.toString(postcode))) {	// int zu String

					zeile1 = zeile.substring(5, zeile.length()); // ab dem 5 bis
																// zum letzten Zeichen												
					break;  // erster Ort wird in zeile1
							// abgespeichert
				}
			}

			b.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return zeile1;
	}

	public static boolean pruefeString(String postcode1) {

		try {
			Integer.parseInt(postcode1); // Überprüfung ob die String-Variable
											// nur Ziffern enthält / String zu int
			ziffer = true;
		} catch (NumberFormatException e) {
			ziffer = false;
		}
		return ziffer;
	}

	public void setPostcode(int definePostcode) {

		Scanner s = new Scanner(System.in);
		System.out.println("Geben Sie die PLZ ein: ");
		String postcode1 = s.next();				//String-Variable zur Überprüfung der Eingabe

		for (int i = 0; i < postcode1.length(); i++) { // Überprüfung auf Anzahl
														// der eingegebenen
														// Zeichen
			numberOfLetters++;
		}

		ziffer = Address.pruefeString(postcode1); // boolesche Methode wird
													// eingelesen

		if (ziffer && numberOfLetters == 5) { // Überprüfung auf beide
												// Bedingungen
			definePostcode = Integer.parseInt(postcode1); //int zu String

			Address a = new Address(); //Objekt a der Klasse Address erzeugen

			int postcode = definePostcode; 	//definePostcode mit postcode gleichsetzen

			Address.postcodeToCity(postcode);	//Klassenmethode aufrufen

			a.setAddress("Im Paradies 1a", postcode, Address.postcodeToCity(postcode)); //Instanzmethode setAddress aufrufen

			a.name = "Max Mustermann"; //Name festlegen über Objekt a

			System.out.println(a.fullAddress()); //Ausgabe des Rückgabewerts der Instanzmethode
												 //fullAddress()

		} else {
			System.out.println("falsche Eingabe \nzuviele Zeichen oder Buchstaben verwendet");
		}

	}
}
