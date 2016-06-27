package Ü06;
import java.util.Scanner;
import java.io.*;
public class Aufgabe53 {

	public static void main(String[] args) throws FileNotFoundException {
		
		// Aufgabe 53
		
		
		
		Scanner s = new Scanner(System.in);
		
		System.out.println("Geben Sie die zu überprüfende Zahl ein.");
		int x = s.nextInt();
		PrimeNumber49 primTest = new PrimeNumber49(x);
		System.out.println(primTest.isPrime2);
	}
}
