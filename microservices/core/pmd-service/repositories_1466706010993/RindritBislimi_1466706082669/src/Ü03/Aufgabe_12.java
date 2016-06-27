package Ü03;

import java.util.Scanner;

public class Aufgabe_12 {

	public static void main(String[] args) {
	
		
	long x;
	long y;
	
	
	Scanner s = new Scanner (System.in);
	
	System.out.println("First Number");
	x = s.nextLong();
	System.out.println("Second Number");
	y = s.nextLong();
	
	if (!((x == 42 && y != 42) == (y == 42 && x != 42))) {
	System.out.println("Das ist die Antwort");
		
	}
	
	else {
		System.out.println("Falsche Antwort");
		
		
	}
			
		
	}
}
