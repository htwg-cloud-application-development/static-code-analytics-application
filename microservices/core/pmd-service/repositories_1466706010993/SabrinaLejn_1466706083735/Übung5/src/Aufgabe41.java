
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Aufgabe41 {

	public static void main(String[] args) throws FileNotFoundException {

		// java.io.File here = new java.io.File(".");
		// System.out.println(here.getAbsolutePath());
		java.io.File postcodeFile = new java.io.File("OpenGeoDB-plz-ort-de.csv");
		System.out.println(postcodeFile.getAbsolutePath());
		if (postcodeFile.exists() && postcodeFile.canRead()) {
			System.out.println("File exists and can be read");
		}
		java.util.Scanner scanner = new java.util.Scanner(postcodeFile, "UTF-8");

		int bPostcode;
		Scanner scan = new Scanner(System.in);

		System.out.println("Please enter a zip code: ");
		bPostcode = scan.nextInt();

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
		scan.close();

	}

}
