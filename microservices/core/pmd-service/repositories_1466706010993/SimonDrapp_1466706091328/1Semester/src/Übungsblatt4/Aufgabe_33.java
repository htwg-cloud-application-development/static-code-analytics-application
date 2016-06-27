package Übungsblatt4;

import java.util.Scanner;

public class Aufgabe_33 {

	static int laenge = 9;
	static int hoehe = 9;
	static int sprache;
	static String[][] schachbrett = new String[laenge][hoehe];
	static String[] alphabet = { "a", "b", "c", "d", "e", "f", "g", "h" };
	static String[] zahlen = { "1", "2", "3", "4", "5", "6", "7", "8" };
	static int reis = 0;

	static Scanner s = new Scanner(System.in);

	public static void main(String[] args) {

		initSchachbrett();

		initFiguren();

		reiskörner();

		printSchachbrett();

	}

	public static void initSchachbrett() {

		for (int i = 0; i < hoehe; i++) {
			for (int j = 0; j < laenge; j++) {

			}

		}

	}

	public static void initFiguren() {

		for (int i = 1; i <= 8; i++) {

			schachbrett[0][i] = (alphabet[(i - 1)] + "\t");
			continue;
		}
		for (int i = 1; i <= 8; i++) {

			schachbrett[i][0] = zahlen[(i - 1)];
			continue;
		}

	}

	// for (int i = 0; i < 8; i++) {
	// schachbrett[1][i] = 'B';
	// schachbrett[6][i] = 'b';
	// }

	// schachbrett[3][0] = 'T';
	// //schachbrett[0][1] = 'P';
	// //schachbrett[0][2] = 'L';
	// schachbrett[0][3] = 'K';
	// schachbrett[0][4] = 'D';
	// //schachbrett[0][5] = 'L';
	// schachbrett[2][5] = 'P';
	// //schachbrett[0][7] = 'T';
	//
	// //schachbrett[7][0] = 't';
	// schachbrett[5][2] = 'p';
	// //schachbrett[7][2] = 'l';
	// schachbrett[7][3] = 'k';
	// schachbrett[7][4] = 'd';
	// //schachbrett[7][5] = 'l';
	// //schachbrett[7][6] = 'p';
	// //schachbrett[7][7] = 't';
	// }
	//
	public static void reiskörner() {

		for (int i = 1; i < 9; i++) {
			for (int j = 1; j < 9; j++) {

				reis = reis + 1;

				schachbrett[i][j] = ("2^" + reis + "\t");

			}

		}
	}

	public static void printSchachbrett() { // nur die Ausgabe

		for (int i = 0; i < hoehe; i++) {
			System.out.println();
			for (int j = 0; j < laenge; j++) {
				schachbrett[0][0] = " ";

				System.out.print(schachbrett[i][j] + " ");

			}

		}

	}
}