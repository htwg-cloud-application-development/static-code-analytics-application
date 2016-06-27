import java.util.Scanner;
import java.util.ArrayList;

public class Aufgabe22 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Number is prime number?: ");
		int n = scanner.nextInt();
		scanner.close();
		if (prime(n) == true) // "==true" kann man auch weglassen
			System.out.println("Number is a prime number");
		else {
			System.out.println("Number is not a prime number");
			primeDivision(n); // code von unten wird aufgerufen
		}
		morePrimes(n);
		}

	public static void morePrimes(int n) {
		ArrayList<Integer> liste2 = new ArrayList<Integer>();
		for (int i = 2; i < n; i++) {
			if (prime(i) == true) {
				liste2.add(i); // fügt i in die Liste2 ein
			}
		}
		System.out.print("prime numbers smaller than number: "+liste2);

	}

	public static void primeDivision(int n) {
		int anfang = n;
		// int[] liste = new int[9];
		//erzeugt eine neue ArrayList vom typ Integer
		ArrayList<Integer> liste = new ArrayList<Integer>();

		for (int i = 2; i <= n; i++) {
			if (n % i == 0) { // Wenn Zahl durch andere Zahl teilbar ist
				liste.add(i); // Fügt der Liste die Zahl i hinzu
				n = n / i;
			}
		}

		System.out.println("the prime-dessection of " + anfang + " is " + liste);
	}

	/**
	 * Gibt mir aus, ob Primzahl Eingabe: Zahl n Ausgabe: wahr oder falsch
	 */
	public static boolean prime(int n) {
		for (int i = 2; i < n; i++) {
			if (n % i == 0) { // Wenn Zahl restlos teilbar ist
				return false; // Methode hat erkannt, dass n keine Primzahl ist
								// und kann aufhören
			}
		}
		return true;
	}
}
