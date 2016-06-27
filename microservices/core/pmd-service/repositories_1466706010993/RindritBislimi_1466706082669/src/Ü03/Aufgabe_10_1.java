package Ü03;

import java.util.Scanner;

public class Aufgabe_10_1 {

	
	public static void main(String[] args) {
		
	// Aufgabe 10.1
		
		byte x;
		byte y;
		
		float z;
		
		Scanner s = new Scanner (System.in);
		
		System.out.println("Zähler:");
		x = s.nextByte();
		System.out.println("Nenner:");
		y = s.nextByte();
		
		if (y == 0) {
			System.out.println("Durch 0 darf nicht geteilt werden");
		}
		
		else {
			z = (float)x / (float)y;
			System.out.println(z);
			
		}
		
	}

}