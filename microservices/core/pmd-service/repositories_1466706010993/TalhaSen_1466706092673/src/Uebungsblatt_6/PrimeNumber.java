package Uebungsblatt_6;

public class PrimeNumber {
	
	public static boolean isPrime(int a){
		if (a<0){ // wenn a kleiner als null ist(negative Zahlen)--> Keine Primzahl
			return false;
		}
		if (a==0 | a==1){ // wenn a null oder eins ist --> Keine Primzahl
			return false;
		}
		if (a==2){
			return true; // die Zahl Zwei ist Ausnhame.
		}
		for (int i=2;i<=Math.sqrt(a);i++){ // Math.sqrt = Die Wurzel der Zahl a.
			if (a % i == 0){
				return false;
			}
		}
		return true;
	}
}
