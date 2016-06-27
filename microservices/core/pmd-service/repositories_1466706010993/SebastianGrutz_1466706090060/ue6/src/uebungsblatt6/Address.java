package uebungsblatt6;
//48
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
//hier beginnt die Šnderung
	
	public static void main (String args[]) throws IOException{
		Scanner s = new Scanner(System.in);
		String suchPlz = s.next();
		Address_2.postcodeToCity(suchPlz);
	}

	/** Class method 
	* @throws IOException */
	
	public static String postcodeToCity(String suchPlz) throws IOException {
		File datei = new File("OpenGeoDB-plz-ort-de.csv");
		String text = FileUtils.readFileToString(datei);
		String[] arrText=text.split("\n");
		
		for (int i = 0; i < arrText.lenght; i++) {
			if(arrText[i].contains(suchPlz)==true){
			String ausgabe = arrText[i].substring(5, arrText[i].lenght());
			System.out.println(ausgabe);
			
			}
		}
		return text;
	}
}
				