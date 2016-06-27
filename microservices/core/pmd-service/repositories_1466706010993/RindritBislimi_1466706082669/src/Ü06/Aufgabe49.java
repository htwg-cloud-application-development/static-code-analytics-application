package Ü06;
import java.util.Scanner;
public class Aufgabe49 {

	public static void main(String[] args) {

	// Aufgabe 49
	
		PrimeNumber49 primTest = new PrimeNumber49(0);
		
		Scanner s = new Scanner(System.in);
		
		System.out.println("Geben Sie die zu überprüfende Zahl ein.");
		int x = s.nextInt();
		
		System.out.println(primTest.isPrime(x));
	}	
}
