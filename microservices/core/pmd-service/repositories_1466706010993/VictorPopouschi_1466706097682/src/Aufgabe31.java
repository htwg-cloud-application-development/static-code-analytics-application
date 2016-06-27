

import java.util.Scanner;

public class Aufgabe31 {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		// String[8][8] brett ={{"A1","A2","A3","A4","A5","A6","A7","A8",},
		// {"B1","B2","B3","B4","B5","B6","B7","B8"},
		// {"C1","C2","C3","C4","C5","C6","C7","C8"},
		// {"D1","D2","D3","D4","D5","D6","D7","D8"},
		// {"E1","E2","E3","E4","E5","E6","E7","E8"},
		// {"F1","F2","F3","F4","F5","F6","F7","F8"},
		// {"G1","G2","G3","G4","G5","G6","G7","G8"},
		// {"H1","H2","H3","H4","H5","H6","H7","H8"}};
		String[] row = { "A", "B", "C", "D", "E", "F", "G", "H" };
		String[] col = { "1", "2", "3", "4", "5", "6", "7", "8" };
		
		System.out.println("Wählen sie die gewünschte sprache:"+"\n"+"1. Deutsch"+"\n"+"2. English");
		int wahl = input.nextInt();
		switch(wahl){
		case 1:{
		
		for (int i = 0; i < row.length; i++) {
			System.out.println("\n");
			for (int j = 0; j < col.length; j++) {
				System.out.print(row[i] + col[j] + "\t");
				//Bauer
				if(col[j]=="2"){
					System.out.print("Bauer"+" ");
				}
				//Turm
				if((row[i]=="H" && col[j]=="1")){
					System.out.print("Turm"+" ");
				}
				if(row[i]=="A" && col[j]=="1" ){
					System.out.print("Turm"+" ");
				}
				//Springer
				
				if((row[i]=="B" && col[j]=="1")){
					System.out.print("Springer"+" ");
				}
				if(row[i]=="G" && col[j]=="1" ){
					System.out.print("Springer"+" ");
				}
                //Läufer
				if((row[i]=="C" && col[j]=="1")){
					System.out.print("Läufer"+" ");
				}
				if(row[i]=="F" && col[j]=="1" ){
					System.out.print("Läufer"+" ");
				}
                //Königin
				if((row[i]=="E" && col[j]=="1")){
					System.out.print("Königin"+" ");
				}
				//König
				if((row[i]=="D" && col[j]=="1")){
					System.out.print("König"+" ");
				}
			}
		
			System.out.println("\n");
		}
		}
		break;
		case 2:
			for (int i = 0; i < row.length; i++) {
				System.out.println("\n");
				for (int j = 0; j < col.length; j++) {
					System.out.print(row[i] + col[j] + "\t");
					//Bauer
					if(col[j]=="2"){
						System.out.print("Pawn"+" ");
					}
					//Turm
					if((row[i]=="H" && col[j]=="1")){
						System.out.print("Rook"+" ");
					}
					if(row[i]=="A" && col[j]=="1" ){
						System.out.print("Rook"+" ");
					}
					//Springer
					
					if((row[i]=="B" && col[j]=="1")){
						System.out.print("Knight"+" ");
					}
					if(row[i]=="G" && col[j]=="1" ){
						System.out.print("Knight"+" ");
					}
	                //Läufer
					if((row[i]=="C" && col[j]=="1")){
						System.out.print("Bishop"+" ");
					}
					if(row[i]=="F" && col[j]=="1" ){
						System.out.print("Bishop"+" ");
					}
	                //Königin
					if((row[i]=="E" && col[j]=="1")){
						System.out.print("Queen"+" ");
					}
					//König
					if((row[i]=="D" && col[j]=="1")){
						System.out.print("King"+" ");
					}
				}
			
				System.out.println("\n");
			}
			break;
			}
		// Aufgabe 32

		System.out.println("Wählen Sie eine Figurine: ");
		String fig = input.next();
		System.out.println("Auf welche Position? ");
		String ausgabe = input.next();
		String array[] = ausgabe.split("");
		int one = Integer.parseInt(array[0]);
		int two = Integer.parseInt(array[1]);
		System.out.println(fig + " auf " + row[one-1]+col[two-1] );
		
		
	}
}
