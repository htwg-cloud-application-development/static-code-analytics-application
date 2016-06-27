
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Aufgabe40 {

	public static void main(String[] args) throws FileNotFoundException {
		java.io.File faustTxt = new java.io.File("faust.txt");

		if (faustTxt.exists() && faustTxt.canRead()) {
			System.out.println("File exists and can be read");
		}
		java.util.Scanner scannerTxt = new java.util.Scanner(faustTxt);
		Scanner scannerInput = new Scanner(System.in);
		System.out.print("Please enter a letter: ");

		char searchLetter;
		int sumLetter = 0;
		String line;

		searchLetter = scannerInput.next().charAt(0);

		while (scannerTxt.hasNextLine()) {
			line = scannerTxt.nextLine();
			for (int i = 0; i < line.length(); i++) {
				if (line.charAt(i) == searchLetter) {
					sumLetter++;
				}
			}
		}
		System.out.println("Sum letter: "+sumLetter);
		scannerTxt.close();
		scannerInput.close();
	}

}
