package Aufgabe50;

import java.io.FileNotFoundException;

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
	public static String postcodeToCity(int aPostcode) {
		switch (aPostcode) {
		case 12345:
			return "Berlin";
		case 78462:
			return "Konstanz";
		default:
			return "";}
		}
			

	
	public void setPostcode(int cPostcode)throws FileNotFoundException {
		this.postcode=cPostcode;
		
		java.io.File postcodeFile = new java.io.File("OpenGeoDB-plz-ort-de.csv");
		
		java.util.Scanner scanner = new java.util.Scanner(postcodeFile, "UTF-8");

		int aPostcode;
		String aTown = "";
		while (scanner.hasNextLine()) {
			aPostcode = scanner.nextInt();
			aTown = scanner.nextLine();
			if (aPostcode == cPostcode) {
				
				this.city=aTown;
			}
		}
		scanner.close();
	}
	}


