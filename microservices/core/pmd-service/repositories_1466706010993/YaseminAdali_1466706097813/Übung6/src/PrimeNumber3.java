import java.util.Scanner;

// Aufgabe 53
// Siehe andere PrimeNumber aufgaben
public class PrimeNumber3 {

	boolean isPrime;
	
	public static void main(String args[]) {

		System.out.println("Geben Sie eine Zahl ein:");
		
		Scanner s = new Scanner(System.in);
		
		int value = s.nextInt();
		PrimeNumber3 ausgabe = new PrimeNumber3(value);
		System.out.println(ausgabe.isPrime);

	}

	public PrimeNumber3(int primzahl) {

		int value = 0;
		for (int i = 2; i < primzahl; i++) {
			if (primzahl % i == 0) {
				isPrime = false;
				break;

			} else {
				isPrime = true;
					primzahl = value;
			}

		}

	}
}
