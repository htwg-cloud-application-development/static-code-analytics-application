package Uebungsblatt_3;
import java.util.Scanner;

public class Aufgabe_13_2 {

	public static void main(String[] args) {
		Scanner s = new Scanner (System.in);
		
		int a; // ertse beliebige Zahl.
		int b; // zweite beliebige Zahl.
		int c; // dritte beliebige Zahl.
		boolean d = true; // Die Bedingung ist wahr. 
		boolean e = false; // Die Bedingung ist falsch.
		
		System.out.println("Eingabe der ersten Zahl");
		a = s.nextInt();
		System.out.println("Eingabe der zweiten Zahl");
		b = s.nextInt();
		System.out.println("Eingabe der dritten Zahl");
		c = s.nextInt();
		
		boolean i = ((a<b) && (b<c)) ? d:e;
		System.out.println("Die Eingabe ist:"+i);
	
	}

}
