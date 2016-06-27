package Uebungsblatt_4;

import java.util.Scanner;

public class Aufgabe_26 {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		System.out.println("Geben sie die Feldlänge ein!");
		int l = s.nextInt(); // selbstbestimmte Feldgröße "L".
		int[] arrayA; // Array deklariert.
		arrayA = new int [l];//Array den Wert L zugewiesen.
	
		for (int i = 0; i<arrayA.length;i++){
			double zufall = Math.random()*(100- -100)+ -100;
			arrayA[i] = (int) zufall;
			System.out.println(arrayA[i] +" ");
		}
		
		
	}
}
