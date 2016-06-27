package Uebungsblatt_3;
import java.util.Scanner;

public class Aufgabe_10 {

	public static void main(String[] args) {
		Scanner s = new Scanner (System.in); //Scanner für Zahleingabe
			
	int numberone; 
	int numbertwo;
	double numberthree;
	double numberfour;
	
	// Aufgabe 10.1
	
	System.out.println("Eingabe einer Ganzzahl");
	numberone = s.nextInt();
	
	System.out.println("Eingabe der zweiten Ganzzahl");
	numbertwo = s.nextInt();
	
	System.out.println("Die Division der Ganzzahlen:"+(double)numberone/(double)numbertwo);
	
	// Aufgabe 10.2
	
	System.out.println("Eingabe einer Gleitkommazahl:");
	numberthree = s.nextDouble();
	
	System.out.println("Eingabe der zweiten Gleitkommazahl:");
	numberfour = s.nextDouble();
	
	System.out.println("Die Multiplikation der Gleitzahlen:"+(int)(numberthree*numberfour));
	
	double x = (numberthree*numberfour);
	int y = (int)(numberthree*numberfour);
	
	double z = x-y;
	System.out.println("Die Nachkommastelle der letzten Zahl:"+z);
		
	}

}
