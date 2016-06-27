package uebungsblatt7;

import java.util.Scanner;
import java.io.*;

public class Address {
	public String nachname;
	public String vorname;
	public String strasse;
	public String hausnummer;
	public String ort;
	public int postleitzahl;
	public String land;
	
	Address(String nachname, String vorname, String strasse, String hausnummer ){
		this.nachname = nachname;
		this.vorname = vorname; 
		this.strasse = strasse;
		this.hausnummer = hausnummer;
	}

	String getAddressLabel() {

		boolean locationValidated;
		try {
			locationValidated = validateLocation(postleitzahl, ort);

			if (locationValidated == true) {
				return "Vorname:" + this.vorname + "Nachname:" + this.nachname
						+ "\n" + "Stra§e:" + this.strasse + "Hausnummer:"
						+ this.hausnummer + "\n" + "Postleitzahl:"
						+ this.postleitzahl + "Ort:" + this.ort + "\n"
						+ "Land:" + this.land;
			} else {
				return "Bitte versuchen sie es noch einmal";

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error occured";

		}
	}

	boolean validateLocation(int postleitzahl, String ort)
			throws FileNotFoundException {
		// java.io.File here = new java.io.File(".");
		// System.out.println(here.getAbsolutePath());
		java.io.File postcodeFile = new java.io.File("OpenGeoDB-plz-ort-de.csv");
		System.out.println(postcodeFile.getAbsolutePath());
		if (postcodeFile.exists() && postcodeFile.canRead()) {
			System.out.println("File exists and can be read");
		}
		java.util.Scanner scanner = new java.util.Scanner(postcodeFile, "UTF-8");

		// int lastPostcode = -1;
		// int aPostcode = -1;

		int aPostcode;
		String aTown;

		while (scanner.hasNextLine()) {
			aPostcode = scanner.nextInt();
			aTown = scanner.nextLine();

			if (aPostcode == postleitzahl && aTown == ort) {
				return true;
			}
		}
		scanner.close();
		return false;
	}
}
