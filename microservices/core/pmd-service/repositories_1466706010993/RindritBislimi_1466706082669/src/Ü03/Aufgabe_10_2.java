package Ü03;

import java.util.Scanner;

public class Aufgabe_10_2 {

	public static void main(String[] args) {
		
		
	float a;
	float b;
	
	float n;
	
	Scanner s = new Scanner (System.in);
	
	System.out.println("Enter first number");
	a = s.nextFloat();
	System.out.println("Enter second number");
	b = s.nextFloat();
	
	n = a * b;
	
	System.out.println((byte)n + " Rest " + (n - (byte)n));
	
	
	}

}
