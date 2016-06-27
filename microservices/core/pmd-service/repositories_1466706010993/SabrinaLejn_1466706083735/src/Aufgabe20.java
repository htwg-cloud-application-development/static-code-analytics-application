import java.util.Scanner;

public class Aufgabe20 {
	public static void main(String[] args) {

		int Integer, Result = 0, x;
		Scanner scanner = new Scanner(System.in);

		System.out.println("Please enter a Integer (min: 1, max: 9999): ");
		Integer = scanner.nextInt();

		x = 1000;
		while (x >= 100) {
			Result = Integer / x;
			switch (Result) {
			case 1:
				System.out.print("ein");
				if (Integer >= 1000) {
					System.out.print("tausend");
					break;
				}
				if (Integer >= 100) {
					System.out.print("hundert");
					break;
				}
				break;
			case 2:
				System.out.print("zwei");
				if (Integer >= 1000) {
					System.out.print("tausend");
					break;
				}
				if (Integer >= 100) {
					System.out.print("hundert");
					break;
				}
				break;
			case 3:
				System.out.print("drei");
				if (Integer >= 1000) {
					System.out.print("tausend");
					break;
				}
				if (Integer >= 100) {
					System.out.print("hundert");
					break;
				}
				break;
			case 4:
				System.out.print("vier");
				if (Integer >= 1000) {
					System.out.print("tausend");
					break;
				}
				if (Integer >= 100) {
					System.out.print("hundert");
					break;
				}
				break;
			case 5:
				System.out.print("fünf");
				if (Integer >= 1000) {
					System.out.print("tausend");
					break;
				}
				if (Integer >= 100) {
					System.out.print("hundert");
					break;
				}
				break;
			case 6:
				System.out.print("sechs");
				if (Integer >= 1000) {
					System.out.print("tausend");
					break;
				}
				if (Integer >= 100) {
					System.out.print("hundert");
					break;
				}
				break;
			case 7:
				System.out.print("sieben");
				if (Integer >= 1000) {
					System.out.print("tausend");
					break;
				}
				if (Integer >= 100) {
					System.out.print("hundert");
					break;
				}
				break;
			case 8:
				System.out.print("acht");
				if (Integer >= 1000) {
					System.out.print("tausend");
					break;
				}
				if (Integer >= 100) {
					System.out.print("hundert");
					break;
				}
				break;
			case 9:
				System.out.print("neun");
				if (Integer >= 1000) {
					System.out.print("tausend");
					break;
				}
				if (Integer >= 100) {
					System.out.print("hundert");
					break;
				}
				break;
			}

			Integer = Integer % x;
			x = x / 10;

		}

		Result = Integer / 10;

		if (Integer < 20) {
			switch (Integer) {
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
			case 10:
				System.out.print("zehn");
				break;
			case 11:
				System.out.print("elf");
				break;
			case 12:
				System.out.print("zwölf");
				break;
			case 13:
				System.out.print("dreizehn");
				break;
			case 14:
				System.out.print("vierzehn");
				break;
			case 15:
				System.out.print("fünfzehn");
				break;
			case 16:
				System.out.print("sechszehn");
				break;
			case 17:
				System.out.print("siebzehn");
				break;
			case 18:
				System.out.print("achtzehn");
				break;
			case 19:
				System.out.print("neunzehn");
				break;
			}
		} else {
			Integer = Integer % x;
			switch (Integer) {
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
			System.out.print("und");
			switch (Result) {
			case 2:
				System.out.print("zwanzig");
				break;
			case 3:
				System.out.print("dreissig");
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

			System.out.println(" ");

			// 22.2

			float Float, decPlace;

			System.out.println("Please enter a float (min:1, max: 999): ");
			Float = scanner.nextFloat();

			Integer = (int) Float;

			decPlace = Float - Integer;
			System.out.println("Nachkommateil:\t" + decPlace);

			x = 1000;
			while (x >= 100) {
				Result = Integer / x;
				switch (Result) {
				case 1:
					System.out.print("ein");
					if (Integer >= 1000) {
						System.out.print("tausend");
						break;
					}
					if (Integer >= 100) {
						System.out.print("hundert");
						break;
					}
					break;
				case 2:
					System.out.print("zwei");
					if (Integer >= 1000) {
						System.out.print("tausend");
						break;
					}
					if (Integer >= 100) {
						System.out.print("hundert");
						break;
					}
					break;
				case 3:
					System.out.print("drei");
					if (Integer >= 1000) {
						System.out.print("tausend");
						break;
					}
					if (Integer >= 100) {
						System.out.print("hundert");
						break;
					}
					break;
				case 4:
					System.out.print("vier");
					if (Integer >= 1000) {
						System.out.print("tausend");
						break;
					}
					if (Integer >= 100) {
						System.out.print("hundert");
						break;
					}
					break;
				case 5:
					System.out.print("fünf");
					if (Integer >= 1000) {
						System.out.print("tausend");
						break;
					}
					if (Integer >= 100) {
						System.out.print("hundert");
						break;
					}
					break;
				case 6:
					System.out.print("sechs");
					if (Integer >= 1000) {
						System.out.print("tausend");
						break;
					}
					if (Integer >= 100) {
						System.out.print("hundert");
						break;
					}
					break;
				case 7:
					System.out.print("sieben");
					if (Integer >= 1000) {
						System.out.print("tausend");
						break;
					}
					if (Integer >= 100) {
						System.out.print("hundert");
						break;
					}
					break;
				case 8:
					System.out.print("acht");
					if (Integer >= 1000) {
						System.out.print("tausend");
						break;
					}
					if (Integer >= 100) {
						System.out.print("hundert");
						break;
					}
					break;
				case 9:
					System.out.print("neun");
					if (Integer >= 1000) {
						System.out.print("tausend");
						break;
					}
					if (Integer >= 100) {
						System.out.print("hundert");
						break;
					}
					break;
				}

				Integer = Integer % x;
				x = x / 10;

			}

			Result = Integer / 10;

			if (Integer < 20) {
				switch (Integer) {
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
				case 10:
					System.out.print("zehn");
					break;
				case 11:
					System.out.print("elf");
					break;
				case 12:
					System.out.print("zwölf");
					break;
				case 13:
					System.out.print("dreizehn");
					break;
				case 14:
					System.out.print("vierzehn");
					break;
				case 15:
					System.out.print("fünfzehn");
					break;
				case 16:
					System.out.print("sechszehn");
					break;
				case 17:
					System.out.print("siebzehn");
					break;
				case 18:
					System.out.print("achtzehn");
					break;
				case 19:
					System.out.print("neunzehn");
					break;
				}
			} else {
				Integer = Integer % x;
				switch (Integer) {
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
				System.out.print("und");
				switch (Result) {
				case 2:
					System.out.print("zwanzig");
					break;
				case 3:
					System.out.print("dreissig");
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

			if (decPlace != 0) {
				System.out.print(" komma ");
			}

			int numbInt;

			for (int id = 10; id > 0; id -= 1) {
				decPlace = decPlace * 10;
				numbInt = (int) decPlace;
				decPlace = decPlace - numbInt;
				switch (numbInt) {
				case 1:
					System.out.print("eins ");
					break;
				case 2:
					System.out.print("zwei ");
					break;
				case 3:
					System.out.print("drei ");
					break;
				case 4:
					System.out.print("vier ");
					break;
				case 5:
					System.out.print("fünf ");
					break;
				case 6:
					System.out.print("sechs ");
					break;
				case 7:
					System.out.print("sieben ");
					break;
				case 8:
					System.out.print("acht ");
					break;
				case 9:
					System.out.print("neun ");
					break;
				}
				scanner.close();
			}
		}
	}
}
