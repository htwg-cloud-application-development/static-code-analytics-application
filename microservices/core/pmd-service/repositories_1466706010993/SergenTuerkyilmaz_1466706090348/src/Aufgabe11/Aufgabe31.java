package Aufgabe11;

import java.util.Scanner;
//Aufgabe 31 und 32
public class Aufgabe31 {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		String sprache = "";
		while (sprache.equals("DE") == false && sprache.equals("EN") == false) { // Die Eingabe DE und EN wird geprüft
			System.out.print("Sprache wählen DE/EN: ");
			sprache = s.next();
		}

		String[][] board = new String[8][8]; // ein 2dimensionales Array wird angelegt. Jeweils eins für eine Seite des Schachbretts
		String[][] figures = new String[8][8];// hier werden 2 Arrays für die jeweiligen Figuren ertstellt

		for (int i = 0; i < 8; i++) { //zählt bis 7 hoch
			for (int j = 0; j < 8; j++) { // wie oben
				board[i][j] = (char) (j + 97) + "" + (8 - i) + ":"; //
			}
		}

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				figures[i][j] = "°"; // nicht belegte Felder
			}
		}

		figures[0][0] = "T "; // Turm
		figures[0][1] = "S "; // Springer
		figures[1][1] = "B "; // Bauer

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {

				System.out.print(board[i][j]);
				if (sprache.equals("EN")) {

					if (figures[i][j].equals("T ")) // wenn im array an den stellen i und j von "figures" T/X/S/B steht dann gibt er T/X/S/B aus
						System.out.print("T ");
				
					if (figures[i][j].equals("X "))// 
						System.out.print("X ");
					
					if (figures[i][j].equals("S "))
						System.out.print("H ");
					
					if (figures[i][j].equals("B "))
						System.out.print("P ");
				}
				else {
					System.out.print(figures[i][j] + " "); // wenn es Deutsch bleibt gibt er es wie gehabt aus
				}
					
			}
			System.out.println(""); 					// Formatierung
		}

	}
}