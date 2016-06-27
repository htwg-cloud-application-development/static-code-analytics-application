package Aufgabe49; 											 
															 								

class PrimeNumber {
	 	
	int value = 0;
	
	

	public boolean isPrime() { 				// Instanzmethode(ohne static)
		if (value <= 1) {
			return false;
		} 									// wenn 1 und kleiner eins--> keine Primzahl

		for (int i = 2; i < value; i++) { 	// For-Schleife solange i kleiner
											// value ist
											// also gilt nicht für eingaben 1
											// und kleiner, d.h. 2 ist
											// automatisch true
			if (value % i == 0) { 			// Wenn Zahl restlos teilbar ist
				return false; 				// Methode hat erkannt, dass n keine Primzahl ist
											// und kann aufhören
			}
		}
		return true;
	}
	
}
