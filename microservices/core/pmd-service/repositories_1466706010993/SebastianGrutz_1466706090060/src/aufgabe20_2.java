import java.util.Scanner;
public class aufgabe20_2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		double zahl;

		Scanner s = new Scanner(System.in);

		System.out.println("Geben Sie eine Gleitkommazahl zwischen 0 und 100 mit hšchstens sechs Nachkommastellen ein: ");
		zahl = s.nextDouble();

		if (zahl > 19.99 && zahl < 100) {

			double a = (zahl / 1) % 10;

			switch ((int) a) {

			case 1:
				System.out.print("ein");
				break;
			case 2:
				System.out.print("zwei");
				break;
			case 3:
				System.out.print("drei");
				break;
			case 4:
				System.out.print("vier");
				break;
			case 5:
				System.out.print("fünf");
				break;
			case 6:
				System.out.print("sechs");
				break;
			case 7:
				System.out.print("sieben");
				break;
			case 8:
				System.out.print("acht");
				break;
			case 9:
				System.out.print("neun");
				break;
			}

			if (zahl % 10 == 0) {

				double b = zahl / 10;

				switch ((int) b) {

				case 1:
					System.out.print("zehn");
					break;
				case 2:
					System.out.print("zwanzig");
					break;
				case 3:
					System.out.print("dreißig");
					break;
				case 4:
					System.out.print("vierzig");
					break;
				case 5:
					System.out.print("fuenfzig");
					break;
				case 6:
					System.out.print("sechzig");
					break;
				case 7:
					System.out.print("siebzig");
					break;
				case 8:
					System.out.print("achtzig");
					break;
				case 9:
					System.out.print("neunzig");
					break;
				}
			}

			else {

				double b = (zahl / 10) % 10;

				switch ((int) b) {

				case 1:
					System.out.print("undzehn");
					break;
				case 2:
					System.out.print("undzwanzig");
					break;
				case 3:
					System.out.print("unddrei§ig");
					break;
				case 4:
					System.out.print("undvierzig");
					break;
				case 5:
					System.out.print("undfuenfzig");
					break;
				case 6:
					System.out.print("undsechzig");
					break;
				case 7:
					System.out.print("undsiebzig");
					break;
				case 8:
					System.out.print("undachtzig");
					break;
				case 9:
					System.out.print("undneunzig");
					break;

				}
			}

			System.out.print(" ");

			System.out.print("Komma");

			System.out.print(" ");

			for (int i = 1; i <= 6; i++) {

				double c = (zahl * Math.pow(10, i)) % 10;

				switch ((int) c) {

				case 0:
					System.out.print("null");
					break;
				case 1:
					System.out.print("eins");
					break;
				case 2:
					System.out.print("zwei");
					break;
				case 3:
					System.out.print("drei");
					break;
				case 4:
					System.out.print("vier");
					break;
				case 5:
					System.out.print("fuenf");
					break;
				case 6:
					System.out.print("sechs");
					break;
				case 7:
					System.out.print("sieben");
					break;
				case 8:
					System.out.print("acht");
					break;
				case 9:
					System.out.print("neun");
					break;
				default:
					System.out.println("");

				}

				System.out.print(" ");

			}
		}

		else if (zahl > 9.99 && zahl < 20) {

			double a = (zahl / 1) % 10;

			switch ((int) a) {

			case 3:
				System.out.print("drei");
				break;
			case 4:
				System.out.print("vier");
				break;
			case 5:
				System.out.print("fuenf");
				break;
			case 6:
				System.out.print("sechs");
				break;
			case 7:
				System.out.print("sieben");
				break;
			case 8:
				System.out.print("acht");
				break;
			case 9:
				System.out.print("neun");
				break;
			}

			if ((int) zahl % 10 == 1 || (int) zahl % 10 == 2) {

				double d = (zahl / 1) % 10;

				switch ((int) d) {

				case 1:
					System.out.print("elf");
					break;
				case 2:
					System.out.print("zwoelf");
					break;
				}
			}

			else {

				double b = (zahl / 10) % 10;

				switch ((int) b) {

				case 1:
					System.out.print("zehn");
					break;

				}
			}

			System.out.print(" ");

			System.out.print("Komma");

			System.out.print(" ");

			for (int i = 1; i <= 6; i++) {

				double c = (zahl * Math.pow(10, i)) % 10;

				switch ((int) c) {

				case 0:
					System.out.print("null");
					break;
				case 1:
					System.out.print("eins");
					break;
				case 2:
					System.out.print("zwei");
					break;
				case 3:
					System.out.print("drei");
					break;
				case 4:
					System.out.print("vier");
					break;
				case 5:
					System.out.print("fuenf");
					break;
				case 6:
					System.out.print("sechs");
					break;
				case 7:
					System.out.print("sieben");
					break;
				case 8:
					System.out.print("acht");
					break;
				case 9:
					System.out.print("neun");
					break;
				default:
					System.out.println("");

				}

				System.out.print(" ");

			}
		}

		else if (zahl >= 0 && zahl < 10) {

			switch ((int) zahl) {

			case 0:
				System.out.print("null");
				break;
			case 1:
				System.out.print("eins");
				break;
			case 2:
				System.out.print("zwei");
				break;
			case 3:
				System.out.print("drei");
				break;
			case 4:
				System.out.print("vier");
				break;
			case 5:
				System.out.print("fuenf");
				break;
			case 6:
				System.out.print("sechs");
				break;
			case 7:
				System.out.print("sieben");
				break;
			case 8:
				System.out.print("acht");
				break;
			case 9:
				System.out.print("neun");
				break;

			}
			System.out.print(" ");

			System.out.print("Komma");

			System.out.print(" ");

			for (int i = 1; i <= 6; i++) {

				double c = (zahl * Math.pow(10, i)) % 10;

				switch ((int) c) {

				case 0:
					System.out.print("null");
					break;
				case 1:
					System.out.print("eins");
					break;
				case 2:
					System.out.print("zwei");
					break;
				case 3:
					System.out.print("drei");
					break;
				case 4:
					System.out.print("vier");
					break;
				case 5:
					System.out.print("fuenf");
					break;
				case 6:
					System.out.print("sechs");
					break;
				case 7:
					System.out.print("sieben");
					break;
				case 8:
					System.out.print("acht");
					break;
				case 9:
					System.out.print("neun");
					break;
				default:
					System.out.println("");

				}

				System.out.print(" ");

			}	

}
}