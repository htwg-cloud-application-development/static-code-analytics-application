package Uebungsblatt_4;

public class Aufgabe_33 {

	public static void main(String[] args) {
		Double[][] board = new Double[8][8]; // Zweidimensionales Array wird angelegt mit Anzahl der jeweiligen Felder.

		board[0][0] = 1.0;

		for (int i = 0; i < 8; i++) {

			for (int a = 1; a < 8; a++) {
				board[i][a] = board[i][a - 1] * 2;// Anzahl der "Reiskörner" werden verdoppelt.
													
			}

			if (i < 7)
				board[i + 1][0] = board[i][7] * 2; // die weiteren Feldwerte werden verdoppelt.
													
		}

		for (int i = 0; i < 8; i++) {
			for (int a = 0; a < 8; a++) {

				System.out.print(board[i][a] + "\t"); // hier werden die berechneten Arrays in 8ter Schritten ausgegeben.
													
			}
			System.out.println("");
		}
	}

}
