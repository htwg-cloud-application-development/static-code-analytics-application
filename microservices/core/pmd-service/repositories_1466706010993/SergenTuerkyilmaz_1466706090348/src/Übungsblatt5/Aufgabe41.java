package Übungsblatt5;

import java.util.Scanner;
import java.io.*;

public class Aufgabe41 {

	public static void main(String[] args) throws FileNotFoundException {

		Scanner y = new Scanner(System.in);

		System.out.println("Geben Sie eine Postleitzahl ein: ");
		int Eingabe = y.nextInt();

		java.io.File postcodeFile = new java.io.File("//Users/srgntrkylmz/Documents/OpenGeoDB-plz-ort-de.csv");
		System.out.println(postcodeFile.getAbsolutePath());
		if (postcodeFile.exists() && postcodeFile.canRead()) {
			System.out.println("File exists and can be read");
		}
		
		java.util.Scanner scanner = new java.util.Scanner(postcodeFile, "UTF-8");
		

		int aPostcode = -1;
		String aTown = "";
		while (scanner.hasNextLine()) {
			aPostcode = scanner.nextInt();
			aTown = scanner.nextLine();
			if (Eingabe == aPostcode) {

				System.out.println(aPostcode + " " + aTown);

			}

		}
		scanner.close();
	}
}