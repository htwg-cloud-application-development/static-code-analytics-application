package Übungsblatt4;

public class Aufgabe_31 {

	static int laenge = 8;
	static int hoehe = 8;
	static char[][] schachbrett = new char[laenge][hoehe];

	public static void main(String[] args) {

		initSchachbrett();

		initFiguren();

		printSchachbrett();

	}

	public static void initSchachbrett() {

		for (int i = 0; i < hoehe; i++) {
			for (int j = 0; j < laenge; j++) {

			}

		}

	}

	public static void initFiguren() {

		for (int i = 0; i < 8; i++) {
			schachbrett[1][i] = 'B';
			schachbrett[6][i] = 'b';
		}
		schachbrett[0][0] = 'T';
		schachbrett[0][1] = 'P';
		schachbrett[0][2] = 'L';
		schachbrett[0][3] = 'K';
		schachbrett[0][4] = 'D';
		schachbrett[0][5] = 'L';
		schachbrett[0][6] = 'P';
		schachbrett[0][7] = 'T';

		schachbrett[7][0] = 't';
		schachbrett[7][1] = 'p';
		schachbrett[7][2] = 'l';
		schachbrett[7][3] = 'k';
		schachbrett[7][4] = 'd';
		schachbrett[7][5] = 'l';
		schachbrett[7][6] = 'p';
		schachbrett[7][7] = 't';

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
