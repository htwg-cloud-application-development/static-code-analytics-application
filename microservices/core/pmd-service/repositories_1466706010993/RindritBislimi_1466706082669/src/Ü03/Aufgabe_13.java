package Ü03;

import java.util.Scanner;

public class Aufgabe_13 {

	// Aufgabe 13.1
	
	public static void main (String[] args) {
		
		Scanner s = new Scanner (System.in);
		
		System.out.println("Enter your age");
		int age = s.nextInt();
		int ticketPrice;
		
	ticketPrice = age >= 16?20:10; System.out.println("Ticket Price: " + ticketPrice);
		
	s.close();
	
	}
}
