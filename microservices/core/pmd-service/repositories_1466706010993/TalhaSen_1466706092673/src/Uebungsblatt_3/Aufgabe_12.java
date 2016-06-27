package Uebungsblatt_3;
import java.util.Scanner;

public class Aufgabe_12 {

	public static void main(String[] args) {
		
		Scanner s = new Scanner (System.in);
		int a;
		int b;
		
	System.out.println("Geben sie die erste Zahl ein");
	a = s.nextInt();
	System.out.println("Geben sie die zweite Zahl ein");
	b = s.nextInt();
	
	if(!(!(a==42 && !(b==42)) && !(b==42 && !(a==42)))){
	System.out.println("Das ist die Antwort");
	}
	else {
	System.out.println("Versuchen sie es erneut");
	}
	s.close();
	}

}
