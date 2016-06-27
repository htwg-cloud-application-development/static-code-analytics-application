package Übungsblatt6;
import java.util.Scanner;

public class Aufgabe47 {

	public static void main(String[] args) {
		
		Scanner s = new Scanner (System.in);
		
		System.out.println("Geben Sie hier eine Zahl ein: ");
		
		
		PrimeNumber primeNumber = new PrimeNumber();
		
		primeNumber.value = s.nextInt();

		System.out.println(primeNumber.isPrime());

		s.close();
	}
}
