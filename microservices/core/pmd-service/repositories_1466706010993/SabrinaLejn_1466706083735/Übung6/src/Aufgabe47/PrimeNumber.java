package Aufgabe47;
import java.util.Scanner;

public class PrimeNumber {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		
		System.out.print("Number is prime number: ");
		int n = scanner.nextInt();
		scanner.close();
				
			System.out.print(isPrime(n));
			//if (isPrime(n) == true) {System.out.println(isPrime(n));}
			//else {System.out.print(isPrime(n));}
	}
	
	/**
	 * Gibt mir aus, ob Primzahl Eingabe: Zahl n Ausgabe: wahr oder falsch
	 */
	public static boolean isPrime(int n) {
		if ((n==1)|(n<1)){return false;}
		for (int i = 2; i < n; i++) {
			if (n % i == 0) { // Wenn Zahl restlos teilbar ist
				return false; // Methode hat erkannt, dass n keine Primzahl ist
								// und kann aufhören
			}
		}
		return true;
	}
}
