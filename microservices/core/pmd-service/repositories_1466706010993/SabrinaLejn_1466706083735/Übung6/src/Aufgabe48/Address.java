package Aufgabe48;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Address {

	public String name;
	public String street;
	public String city;
	public int postcode;
	public String email;
	public String comment;

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
		return name + ", " + street + ", " + ((Integer) postcode).toString()
				+ " " + city;
	}

	/** Class method */
	//public static String postcodeToCity(int aPostcode) {
//		switch (aPostcode) {
//		case 12345:
//			return "Berlin";
//		case 78462:
//			return "Konstanz";
//		default:
//			return "";
			
	public static String postcodeToCity(int bPostcode) throws FileNotFoundException {
	
				java.io.File postcodeFile = new java.io.File("OpenGeoDB-plz-ort-de.csv");
				
				java.util.Scanner scanner = new java.util.Scanner(postcodeFile);

				int aPostcode;
				String aTown = "";
				while (scanner.hasNextLine()) {
					aPostcode = scanner.nextInt();
					aTown = scanner.nextLine();
					if (aPostcode == bPostcode) {
						System.out.println(aPostcode + " " + aTown);
					}
				}
				scanner.close();
				return aTown;
		}
	}

