package Übungsblatt6;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class PostPost {

	int PLZ = 78467;

	public void setPostcode(int Postcode) throws FileNotFoundException {

		java.io.File postcodeFile = new java.io.File("//Users/srgntrkylmz/Documents/OpenGeoDB-plz-ort-de.csv");
		System.out.println(postcodeFile.getAbsolutePath());
		if (postcodeFile.exists() && postcodeFile.canRead()) {
			System.out.println("File exists and can be read");
		}

		java.util.Scanner scanner = new java.util.Scanner(postcodeFile, "UTF-8");

	}

}
