package Aufgabe53;

public class PrimeNumber {

	int value;
	boolean isPrime;

	// Konstruktor//
	public PrimeNumber(int value) {
		this.value = value;
		this.isPrime = true;

		if (value <= 1) {
			this.isPrime = false;
		} // wenn 1 und kleiner eins--> keine Primzahl

		for (int i = 2; i < value; i++) {	// For-Schleife solange i kleiner
											// value ist
											// also gilt nicht für eingaben 1
											// und kleiner, d.h. 2 ist
											// automatisch true
			if (value % i == 0) { 			// Wenn Zahl restlos teilbar ist
				this.isPrime = false; 		// Value keine Primzahl und schleife
											// kann aufhören
			}
		}
		//this.isPrime = true;
	}

}
