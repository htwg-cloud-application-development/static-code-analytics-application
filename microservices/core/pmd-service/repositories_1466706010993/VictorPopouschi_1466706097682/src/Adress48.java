

//Aufgabe 48+50
import java.util.Scanner;
import java.io.*;

public class Adress48 {
	public String name;
	public String street;
	 public String city;
	public int postcode;
	public String email;
	public String comment;
	public String aLine = "";

	/** Constructor */
	public Adress48() {
		this.name = "";
		this.street = "";
		this.city = "";
		this.postcode = 0;
		this.email = "";
		this.comment = "";
	}

	public static void main(String args[]) throws FileNotFoundException {
		Adress48 adress = new Adress48();
		adress.setPostCode(76646);
		postcodeToCity(String.valueOf(adress.getPostCode()));
	}

	/** Instance method */
	public void setAddress(String aStreet, int aPostcode, String aCity) {
		this.street = aStreet;
		this.postcode = aPostcode;
		this.city = aCity;
	}

	public void setPostCode(int ad) {
		this.postcode = ad;
	}

	public int getPostCode() {
		return postcode;
	}
	

	/** Instance method */
	public String fullAddress() {
		return name + ", " + street + ", " + ((Integer) postcode).toString() + " " + city;
	}
  
	/**
	 * Class method
	 * 
	 * @throws FileNotFoundException
	 */
	public static String postcodeToCity(String aPostcode) throws FileNotFoundException {
		String aLine = "";
		String plztocity="";
		java.io.File aFile = new java.io.File(
				"C:/Users/Gwythyr/workspace/HTWG/bin/Programmierkurs/OpenGeoDB-plz-ort-de.csv");
		System.out.println(aFile.getAbsolutePath());
		if (aFile.exists() && aFile.canRead()) {
			System.out.println("File exists and can be read!");
		}
		Scanner book = new Scanner(aFile, "UTF-8");
		String[] adr = {};
		while (book.hasNext()) {
			aLine = book.nextLine();
			if (aLine.contains(aPostcode)) {
				adr = aLine.split("\\s+");
				 //System.out.println(aLine);
			}
		}
		book.close();
		String eineAdresse = adr[1];
		plztocity = eineAdresse;
		System.out.println(eineAdresse);
		return eineAdresse;
	}

}