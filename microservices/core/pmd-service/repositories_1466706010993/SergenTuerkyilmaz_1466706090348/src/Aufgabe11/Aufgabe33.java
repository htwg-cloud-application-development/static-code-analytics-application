package Aufgabe11;

public class Aufgabe33 {
	public static void main(String[] args) {
		
		Double[][] board = new Double[8][8]; // es wird ein Zweidimensionales Array angelegt mit der Anzahl der jeweiligen Felder
		
		board[0][0] = 1.0;
		
		for (int i = 0; i < 8; i++) {		
		
			for (int j = 1; j < 8; j++) { 
				board[i][j]=board[i][j-1]*2;//die Anzahl der "Reiskörner" werden verdoppelt
			}
			
			if (i<7)
				board[i+1][0]=board[i][7]*2; //die weiteren Feldwerte werden verdoppelt
		}
	
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				
			System.out.print(board[i][j] + "\t"); // hier werden die berechneten Arrays ausgegeben / in 8ter Schritten
					}		
			System.out.println("");
		}
	}
}