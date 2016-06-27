package Ü03;

import java.util.Scanner;

public class Aufgabe_13_2 {
	
	public static void main (String[] args) {
		
		Scanner s = new Scanner (System.in);
		
		
		// Aufgabe 13.2
		
		int a;
		int b;
		int c;
		
		String x ="Richtige Reihenfolge";
		String y = "Falsche Reihenfolge";
		
		System.out.println("1.Zahl");
		a = s.nextInt();
		System.out.println("2. Zahl");
		b = s.nextInt();
		System.out.println("3. Zahl");
		c = s.nextInt();
		
		String d = (a<b && b<c)?x:y;
				System.out.println(d);
		
		s.close();
		
		
		
	}
}
