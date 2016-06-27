package Uebungsblatt_3;
import java.util.Scanner;

public class Aufgabe_13 {

	public static void main(String[] args) {
	Scanner s = new Scanner (System.in);
	
	// Aufgabe 13.1
	
	int a = 10; // Preis 10 (über 15 Jahre)
	int b = 20; // Preis 20 (unter 15 Jahre)
	int c; // Eingabe des Alters
	
	System.out.println("Eingabe des Alters");
	c = s.nextInt();
	
	int i = (c<16) ? a:b;
	
	System.out.println("Der Preis lautet:"+i+"Euro");
	
	

	}

}
