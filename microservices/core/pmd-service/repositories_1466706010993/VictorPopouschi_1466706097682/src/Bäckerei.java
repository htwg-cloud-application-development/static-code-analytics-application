
import java.util.Scanner;

public class Bäckerei {

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		System.out.println("Geben Sie die PLZ ein:  ");
		System.out.println("z.B. 78467,78464,78462 ,78465");
		String plz = input.nextLine();
		String[] split = plz.split("");
		if (split.length == 5) {
			if ((split[0] == "7") && (split[1] == "8") && (split[2] == "4")) {
				switch (plz) {
				case "78467":
					System.out.println("Sternenbäckerei : Fürstenbergstr. 91 ");
					System.out.println("Paradiesbäckerei : Max-Stromeyerstr. 55 ");
					System.out.println("Meisterbäckerei : Carl-Benz-Straße 18-22");
					System.out.println("Holsteins-Backhaus : Moltkestr.3 ");
					System.out.println("Holsteins-Backhaus : Holstein's Backhaus GmbH  Carl Benz Str.13");
					System.out.println("Holsteins-Backhaus : Holstein's Backhaus Im Kaufland Zähringerplatz 7 ");
					break;
				case "78464":
					System.out.println("Meisterbäckerei : Zähringerplatz 9 ");
					System.out.println("Meisterbäckerei : Kanzleistraße 10 ");
					break;
				case "78462":
					System.out.println("Bodanstr. 1-3 ");
					System.out.println("Paradiesbäckerei : Gottlieber Straße 42 Konstanz");
					System.out.println("Paradiesbäckerei : Brauneggerstraße 14");
					System.out.println("Reginbrot : Münzgasse 16 Konstanz");
					System.out.println("Holsteins-Bäckerei : Holsteins Backhaus Hussenstr.21-23 ");
					System.out.println("Holsteins-Bäckerei : Rosgartenstr. 22");
					break;
				case "78465":
					System.out.println("Bäckerei Fricke : Martin-Schleyer-Straße 26");
					break;
				default:
					System.out.println("Die angegebene PLZ ist nicht gültig! \n Bitte neue PLZ angeben:");

				}
			}

		} else {
			System.out.println("eine PLZ hat die länge 5");
		}
	}

}