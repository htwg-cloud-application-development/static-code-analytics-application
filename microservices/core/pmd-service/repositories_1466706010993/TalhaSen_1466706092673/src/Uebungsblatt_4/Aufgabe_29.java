package Uebungsblatt_4;

public class Aufgabe_29 {

	public static void main(String[] args) {
		boolean[] primeNumber = new boolean[101];
		System.out.println("Die Primzahlen lauten:");

		for (int i = 1; i <primeNumber.length; i++) {
			// Erst sind alle Zahlen im Intervall Primzahlen.
			primeNumber[i] = true;

			for (int z = 2; z < i; z++) { // Wenn Restwert null dann keine
											// Primzahl
				if (i % z == 0) {
					primeNumber[i] = false; // "false" wenn kein Restwert übrig.
					break;

				}
			}
		}
		for (int i = 2; i < primeNumber.length; i++) {
			if (primeNumber[i]) {
				System.out.println(i); // Ausgabe der Primzahlen.

			} else {
				System.out.println("Keine Primzahl ist:" + i); // Ausgabe der
																// Nicht-Primzahlen.

			}
		}
		
		
	}
}
		