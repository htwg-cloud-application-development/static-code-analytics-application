package Übungsblatt3;

import java.util.Scanner;

public class Aufgabe_20_1 {

	public static void main(String[] args) {

		int zahl;

		Scanner s = new Scanner(System.in);

		System.out.println("Geben Sie eine Ganzzahl zwischen 1 bis 9999 ein: ");
		zahl = s.nextInt();

		if (zahl > 999 && zahl < 10000) {

			double a = zahl / 1000;

			switch ((int) a) {

			case 1:
				System.out.print("eintausend");
				break;
			case 2:
				System.out.print("zweitausend");
				break;
			case 3:
				System.out.print("dreitausend");
				break;
			case 4:
				System.out.print("viertausend");
				break;
			case 5:
				System.out.print("fünftausend");
				break;
			case 6:
				System.out.print("sechstausend");
				break;
			case 7:
				System.out.print("siebentausend");
				break;
			case 8:
				System.out.print("achttausend");
				break;
			case 9:
				System.out.print("neuntausend");
				break;

			}

			double b = (zahl / 100) % 10;

			switch ((int) b) {

			case 1:
				System.out.print("einhundert");
				break;
			case 2:
				System.out.print("zweihundert");
				break;
			case 3:
				System.out.print("dreihundert");
				break;
			case 4:
				System.out.print("vierhundert");
				break;
			case 5:
				System.out.print("fünfhundert");
				break;
			case 6:
				System.out.print("sechshundert");
				break;
			case 7:
				System.out.print("siebenhundert");
				break;
			case 8:
				System.out.print("achthundert");
				break;
			case 9:
				System.out.print("neunhundert");
				break;
			}

			double c = (zahl / 1) % 10;

			switch ((int) c) {

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

			double d = (zahl / 10) % 10;

			switch ((int) d) {

			case 1:
				System.out.print("undzehn");
				break;
			case 2:
				System.out.print("undzwanzig");
				break;
			case 3:
				System.out.print("unddreißig");
				break;
			case 4:
				System.out.print("undvierzig");
				break;
			case 5:
				System.out.print("undfünfzig");
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

		else if (zahl > 99 && zahl < 1000) {

			double a = (zahl / 100) % 10;

			switch ((int) a) {

			case 1:
				System.out.print("einhundert");
				break;
			case 2:
				System.out.print("zweihundert");
				break;
			case 3:
				System.out.print("dreihundert");
				break;
			case 4:
				System.out.print("vierhundert");
				break;
			case 5:
				System.out.print("fünfhundert");
				break;
			case 6:
				System.out.print("sechshundert");
				break;
			case 7:
				System.out.print("siebenhundert");
				break;
			case 8:
				System.out.print("achthundert");
				break;
			case 9:
				System.out.print("neunhundert");
				break;
			}

			double b = (zahl / 1) % 10;

			switch ((int) b) {

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

			double c = (zahl / 10) % 10;

			switch ((int) c) {

			case 1:
				System.out.print("undzehn");
				break;
			case 2:
				System.out.print("undzwanzig");
				break;
			case 3:
				System.out.print("unddreißig");
				break;
			case 4:
				System.out.print("undvierzig");
				break;
			case 5:
				System.out.print("undfünfzig");
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

		else if (zahl > 19 && zahl < 100) {

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
					System.out.print("fünfzig");
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
					System.out.print("unddreißig");
					break;
				case 4:
					System.out.print("undvierzig");
					break;
				case 5:
					System.out.print("undfünfzig");
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
		}

		else if (zahl > 9 && zahl < 20) {

			double a = (zahl / 1) % 10;

			switch ((int) a) {

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

			if (zahl % 10 == 1 || zahl % 10 == 2) {

				double d = (zahl / 1) % 10;

				switch ((int) d) {

				case 1:
					System.out.print("elf");
					break;
				case 2:
					System.out.print("zwölf");
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
		}

		else if (zahl >= 0 && zahl < 10) {

			switch (zahl) {

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
		}

	}
}
