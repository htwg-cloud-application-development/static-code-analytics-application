import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

public class Aufgabe48 {

	public String name;
	public String street;
	public String city;
	public int postcode;
	public String email;
	public String comment;
	private static Scanner s;

	/** Constructor */
	public Aufgabe48() {
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
	public static void main (String args[]) throws IOException{
		
		s = new Scanner(System.in);
		String suchPlz = s.next();
		Aufgabe48.postcodeToCity(suchPlz);
		
	
		
	}

	/** Class method 
	 * @throws IOException */
	public static String postcodeToCity(String suchPlz) throws IOException {
		File datei = new File("OpenGeoDB-plz-ort-de.csv");
		String text = FileUtils.readFileToString(datei);
		String[] arrText=text.split("\n");
		
		for (int i = 0; i < arrText.length; i++) {
			if(arrText[i].contains(suchPlz)==true){
				String ausgabe = arrText[i].substring(5, arrText[i].length());
			System.out.println(ausgabe);	
	
		}
	}
		return text;
	}
}
