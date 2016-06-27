package Übungsblatt6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class postCodeAufgabe50 {

	public int postcode;
	public String city;

	public void setPostcode(int PostcodeX) throws FileNotFoundException {

		int y;
		this.postcode = PostcodeX;
		File a = new File("//Users/srgntrkylmz/Documents/OpenGeoDB-plz-ort-de.csv");
		Scanner s = new Scanner(a, "UTF-8");
		String zeile = "";

		while (s.hasNextLine()) {

			y = s.nextInt();
			zeile = s.nextLine();
			if (PostcodeX == y) {
				this.city = zeile;
			}
		}
	}
}
