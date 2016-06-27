package Übungsblatt5;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Aufgabe_40 {

	static char currentletter;
	static char letter;
	static int count;

	static Scanner s = new Scanner(System.in);

	public static void main(String[] args) {

		System.out.println("Bitte geben Sie einen Buchstaben ein: ");
		letter = s.next().charAt(0); // erstes Zeichen der Eingabe wird ausgewählt

		leseDatei();

		System.out.println("Die Anzahl von dem Buchstaben " + letter + " beträgt: " + count);

	}

	public static void leseDatei() {

		try {
			
			File faust = new File("C:\\Users\\simon\\Desktop\\Programmieren\\faust.txt");
//			File faust = new File("C:\\Users\\simon\\Desktop\\Programmieren\\Alphabet.txt");

			FileReader f = new FileReader(faust); // Anschlussstrom für Zeichen, der sich
												  // mit einer Textdatei verbindet

			BufferedReader b = new BufferedReader(f); // kehrt erst dann wieder zum Lesen der Datei zurück,
													  // wennd der Puffer leer ist (weil das Programm alles daraus gelesen hat)
			
			String zeile = null; // String-Variable die jeweils die Zeile aufnimmt, während sie gelesen wird

			while ((zeile = b.readLine()) != null) {	// Variable wird erste Zeile übergeben und darf nicht null sein
				for (int i = 0; i < zeile.length(); i++) { // alle Zeichen werden überprüft

					currentletter = zeile.charAt(i); //Variable wird jeweils dem entsprechenden Zeichen übergeben

					if (currentletter == letter) { //nach Zeichen überprüfen
						count++;
					}
				}
			}
			b.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	
}
