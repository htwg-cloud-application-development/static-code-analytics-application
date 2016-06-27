package Übungsblatt4;

import java.util.Scanner;

public class Aufgabe_32 {

	static int laenge = 8;
	static int hoehe = 8;
	static int sprache;
	static char[][] schachbrett = new char[laenge][hoehe];
	static char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };

	static Scanner s = new Scanner(System.in);

	public static void main(String[] args) {

		initSchachbrett();

		initFiguren();

		printSchachbrett();

		System.out.println();
		System.out.println();

		positionsausgabe();

	}

	public static void initSchachbrett() {

		for (int i = 0; i < hoehe; i++) {
			for (int j = 0; j < laenge; j++) {

			}

		}

	}

	public static void initFiguren() {

		// for (int i = 0; i < 8; i++) {
		// schachbrett[1][i] = 'B';
		// schachbrett[6][i] = 'b';
		// }

		schachbrett[3][0] = 'T';
		// schachbrett[0][1] = 'P';
		// schachbrett[0][2] = 'L';
		schachbrett[0][3] = 'K';
		schachbrett[0][4] = 'D';
		// schachbrett[0][5] = 'L';
		schachbrett[2][5] = 'P';
		// schachbrett[0][7] = 'T';

		// schachbrett[7][0] = 't';
		schachbrett[5][2] = 'p';
		// schachbrett[7][2] = 'l';
		schachbrett[7][3] = 'k';
		schachbrett[7][4] = 'd';
		// schachbrett[7][5] = 'l';
		// schachbrett[7][6] = 'p';
		// schachbrett[7][7] = 't';
	}

	public static void positionsausgabe() {

		System.out.println("Wählen Sie eine Sprache");
		System.out.println("0 für Deutsch");
		System.out.println("1 für Englisch");
		System.out.print("Eingabe: ");
		sprache = s.nextInt();
		System.out.println();

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {

				if (sprache == 0) {

					switch (schachbrett[i][j]) {

					case 'B':
						System.out.println("Schwarzer Bauer auf: " + alphabet[j] + (i + 1));
						break;
					case 'b':
						System.out.println("Weißer Bauer auf: " + alphabet[j] + (i + 1));
						break;

					case 'T':
						System.out.println("Schwarzer Turm auf: " + alphabet[j] + (i + 1));
						break;
					case 't':
						System.out.println("Weißer Turm auf: " + alphabet[j] + (i + 1));
						break;

					case 'L':
						System.out.println("Schwarzer Läufer auf: " + alphabet[j] + (i + 1));
						break;
					case 'l':
						System.out.println("Weißer Läufer auf: " + alphabet[j] + (i + 1));
						break;

					case 'K':
						System.out.println("Schwarzer König auf: " + alphabet[j] + (i + 1));
						break;
					case 'k':
						System.out.println("Weißer König auf: " + alphabet[j] + (i + 1));
						break;

					case 'D':
						System.out.println("Schwarze Dame auf: " + alphabet[j] + (i + 1));
						break;
					case 'd':
						System.out.println("Weiße Dame auf: " + alphabet[j] + (i + 1));
						break;

					default:
						break;

					}

				}

				else if (sprache == 1) {

					switch (schachbrett[i][j]) {

					case 'B':
						System.out.println("black pawn on: " + alphabet[j] + (i + 1));
						break;
					case 'b':
						System.out.println("white pawn on: " + alphabet[j] + (i + 1));
						break;

					case 'T':
						System.out.println("black rook on: " + alphabet[j] + (i + 1));
						break;
					case 't':
						System.out.println("white rook on: " + alphabet[j] + (i + 1));
						break;

					case 'L':
						System.out.println("black bishop on: " + alphabet[j] + (i + 1));
						break;
					case 'l':
						System.out.println("white bishop on: " + alphabet[j] + (i + 1));
						break;

					case 'K':
						System.out.println("black king on: " + alphabet[j] + (i + 1));
						break;
					case 'k':
						System.out.println("white king on: " + alphabet[j] + (i + 1));
						break;

					case 'D':
						System.out.println("black queen on: " + alphabet[j] + (i + 1));
						break;
					case 'd':
						System.out.println("white queen on: " + alphabet[j] + (i + 1));
						break;

					default:
						break;

					}
				}
			}
		}
		if (sprache > 1 || sprache < 0) {

			System.out.println("Falsche Eingabe!");
		}

	}

	public static void printSchachbrett() { // nur die Ausgabe

		for (int i = 0; i < hoehe; i++) {
			System.out.println();
			for (int j = 0; j < laenge; j++) {
				System.out.print(schachbrett[i][j] + " ");

			}

		}

	}
}