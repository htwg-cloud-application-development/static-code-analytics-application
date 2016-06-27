package Ü03;

import java.util.Scanner;

public class Aufgabe_15 {

	public static void main (String[] args) {
		
		Scanner s = new Scanner (System.in);
		
		byte x;
	
		do {
		System.out.println("Please enter a number from 1 to 7");
		x = s.nextByte();
		}
		while (x != 1 && x != 2 && x != 3 && x != 4 && x != 5 && x != 6 && x != 7);
	
		
		switch (x) {
		case 1:
			System.out.println("Montag");
			break;
		case 2:
			System.out.println("Dienstag");
			break;
		case 3:
			System.out.println("Mittwoch");
			break;
		case 4:
			System.out.println("Donnerstag");
			break;
		case 5:
			System.out.println("Freitag");
			break;
		case 6:
			System.out.println("Samstag");
			break;
		case 7:	
			System.out.println("Sonntag");
			
		}
		 s.close();
		
	}
}
