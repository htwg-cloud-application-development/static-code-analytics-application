
	package Aufgabe63bis68;
	import java.io.*;


	public class Meth extends Address {
		
		boolean CombiUK (String zipCode, String city) throws FileNotFoundException{
		java.io.File postcodeFile = new java.io.File("uk-postcode-database-csv.csv");
		System.out.println(postcodeFile.getAbsolutePath());
		
		java.util.Scanner scanner = new java.util.Scanner(postcodeFile, "UTF-8");

		
		String aPostcode = "";
		String aTown = "";
		while (scanner.hasNextLine()) {
			aPostcode = scanner.next();
			aTown = scanner.nextLine();
			aTown = aTown.trim();
			if (aPostcode.equals(zipCode) && aTown.equals(city) ) {
				
				return true;
			}
		}

		scanner.close();
		return false;
		

	}
		boolean CombiDE (String zipCode, String city) throws FileNotFoundException{
			java.io.File postcodeFile = new java.io.File("OpenGeoDB-plz-ort-de.csv");
			System.out.println(postcodeFile.getAbsolutePath());
			
			java.util.Scanner scanner = new java.util.Scanner(postcodeFile, "UTF-8");

			
			String aPostcode = "";
			String aTown = "";
			while (scanner.hasNextLine()) {
				aPostcode = scanner.next();
				aTown = scanner.nextLine();
				aTown = aTown.trim();
				if (aPostcode.equals(zipCode) && aTown.equals(city) ) {
					
					return true;
				}
			}

			scanner.close();
			return false;
		}
		
		public String plzDE (String zipCode) throws FileNotFoundException{
			java.io.File postcodeFile = new java.io.File("OpenGeoDB-plz-ort-de.csv");
		
		
			java.util.Scanner scanner = new java.util.Scanner(postcodeFile, "UTF-8");

			String city = "";
			String aPostcode = "";
			String aTown = "";
			while (scanner.hasNextLine()) {
				aPostcode = scanner.next();
				aPostcode = aPostcode.trim();
				aTown = scanner.nextLine();
				if (aPostcode.equals(zipCode)) {
					city = aTown.trim();
					break;
					
				}
			}

			scanner.close();
			return city;
			
		
	}
		public String plzUK (String zipCode) throws FileNotFoundException{
			java.io.File postcodeFile = new java.io.File("uk-postcode-database-csv.csv");
			
			java.util.Scanner scanner = new java.util.Scanner(postcodeFile, "UTF-8");

			String city = "";
			String aPostcode = "";
			String aTown = "";
			while (scanner.hasNextLine()) {
				aPostcode = scanner.next();
				aPostcode = aPostcode.trim();
				aTown = scanner.nextLine();
				if (aPostcode.equals(zipCode)) {
					city = aTown.trim();
					break;
					
				}
			}

			scanner.close();
			return city;
		
		
		
	}
		
		public String ortUK (String city) throws FileNotFoundException{
			
			java.io.File postcodeFile = new java.io.File("uk-postcode-database-csv.csv");
			
			java.util.Scanner scanner = new java.util.Scanner(postcodeFile, "UTF-8");
			
			String plz = "";
			
			
			String aPostcode = "";
			String aTown = "";
			while (scanner.hasNextLine()) {
				aPostcode = scanner.next();
				aTown = scanner.nextLine();
				
				if (aTown.trim().equals(city)) {
					plz = aPostcode.trim();
					
					break;
					
				}
			}

			scanner.close();
			return plz;
		}
	
	public String ortDE (String city) throws FileNotFoundException{
		
		java.io.File postcodeFile = new java.io.File("OpenGeoDB-plz-ort-de.csv");
		
		java.util.Scanner scanner = new java.util.Scanner(postcodeFile, "UTF-8");
		
		String plz = "";
		
		
		String aPostcode = "";
		String aTown = "";
		while (scanner.hasNextLine()) {
			aPostcode = scanner.next();
			aTown = scanner.nextLine();
			if (aTown.trim().equals(city)) {
				plz = aPostcode.trim();
				
				break;
				
			}
		}

		scanner.close();
		return plz;
	}
}
		

