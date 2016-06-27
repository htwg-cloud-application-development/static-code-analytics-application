package Uebungsblatt_3;
import java.util.Scanner;

public class Aufgabe_23_3 {

	public static void main(String[] args) {
	Scanner s = new Scanner (System.in);
	
	int a = 0;
	int b; // Obergrenze 
	System.out.println("Geben sie die Obergrenze ein:");
	b = s.nextInt();
	
	int i = 1;
		do {
			System.out.println(i);
			a=a+i;
			i++;
			
		}
		while (i <= b);
		System.out.println("Die Summe lautet:"+a);
		System.out.println("Der Mittelwert lautet:"+(float)a/2);
		System.out.println("Der Durchschnitt lautet:"+(float)a/b);
	
	}

}
