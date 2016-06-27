import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import org.apache.commons.io.FileUtils;

	public class Addressen {

		public static void main(String[] args) throws IOException {
			Scanner s = new Scanner(System.in);
			String suchPlz = s.next();

			Addressen plz = new Addressen();

			plz.postcodeToCity(suchPlz);
		}

		public void postcodeToCity(String suchPlz) throws IOException {
			File datei = new File("uk-postcode-database-csv.csv");
			File datei2 = new File("OpenGeoDB-plz-ort-de.csv");
			String text = FileUtils.readFileToString(datei);
			String text2 = FileUtils.readFileToString(datei2);
			String[] arrText = text.split("\n");
			String[] arrText2 = text2.split("\n");

			for (int i = 0; i < arrText.length; i++) {
				if (arrText[i].contains(suchPlz) == true) {
					String ausgabe = arrText[i].substring(41, arrText[i].length());
					System.out.println(ausgabe);
				}

			}
			String Plz = String.valueOf(suchPlz);

			int counter = 0;
			for (int i = 0; i < Plz.length(); i++) {
				Plz.charAt(i);
				counter++;
			}
			if (counter == 5) {
				for (int i = 0; i < arrText2.length; i++) {
					if (arrText2[i].contains(suchPlz) == true) {
						String ausgabe = arrText2[i].substring(5, arrText2[i].length());
						System.out.println(ausgabe);
					}
				}
			}
		}
	}
