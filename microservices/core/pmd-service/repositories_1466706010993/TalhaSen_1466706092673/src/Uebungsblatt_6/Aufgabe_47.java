package Uebungsblatt_6;
import java.util.Scanner;
public class Aufgabe_47 {

	public static void main(String[] args) {
		PrimeNumber aNumber = new PrimeNumber();
		System.out.println("Bitte geben sie eine Zahl ein:");
		Scanner s = new Scanner(System.in);
		int number = s.nextInt();
		System.out.println("Ist die Zahl " + number + " eine Primzahl?");
		System.out.println(aNumber.isPrime(number));
	
		s.close();
	}
}
