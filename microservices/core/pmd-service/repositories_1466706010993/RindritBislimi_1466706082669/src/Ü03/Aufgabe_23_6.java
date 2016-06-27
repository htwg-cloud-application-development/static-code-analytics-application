package Ü03;

import java.util.Scanner;

public class Aufgabe_23_6 {

	public static void main (String[] args) {
		
	Scanner s = new Scanner (System.in);
	
	
	
	int x;
	int z = 0;
	
	System.out.println("Geben Sie eine Obergrenze ein?");
	x = s.nextInt();
	
	for (int i=1 ; i<=x; i++) {
		z = (z + i*i);
	
	}
		System.out.println("Die Summe beträgt " + z );
//		System.out.println("Der Mittelwert beträgt " + (z/2));
//		System.out.println("Durchschnitt " + ((float)z/x));
		
		s.close();
	

	}
}
