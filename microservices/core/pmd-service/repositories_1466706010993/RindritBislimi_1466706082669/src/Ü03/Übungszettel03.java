package Ü03;

import java.util.Scanner;

public class Übungszettel03 {

	
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
		
		
			
		// Aufgabe 10.2
			
			float a;
			float b;
			
			float n;
			
			Scanner r = new Scanner (System.in);
			
			System.out.println("Enter first number");
			a = r.nextFloat();
			System.out.println("Enter second number");
			b = r.nextFloat();
			
			n = a * b;
			
			System.out.println((byte)n + " Rest " + (n - (byte)n));
			
			
			
		
	}
}
