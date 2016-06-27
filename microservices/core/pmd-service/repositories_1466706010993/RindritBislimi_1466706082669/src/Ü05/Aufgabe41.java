package Ü05;
import java.io.*;
import java.util.Scanner;

public class Aufgabe41 {
	public static void main(String[] args) throws FileNotFoundException {
		
		java.io.File here = new java.io.File(".");
		System.out.println(here.getAbsolutePath());
		File postcodeFile = new File("C://Test//OpenGeoDB-plz-ort-de.csv");
		System.out.println(postcodeFile.getAbsolutePath());
		if (postcodeFile.exists() && postcodeFile.canRead()) {
			System.out.println("File exists and can be read");
		}
		
		java.util.Scanner s = new java.util.Scanner(postcodeFile, "UTF-8");
		
		
		int postcode;
		Scanner b = new Scanner (System.in);
		System.out.println("Geben Sie eine gültige Postleitzahl ein");
		postcode = b.nextInt();
		
		int aPostcode2;
		String aTown = "";
		
		while(b.hasNextLine())	{
			aPostcode2 = b.nextInt();
			aTown = b.nextLine();
			if (postcode == aPostcode2)	{
				System.out.println(aPostcode2 + " " + aTown);
			}
			
		}
		s.close();
		b.close();
		
		}

	
	}
