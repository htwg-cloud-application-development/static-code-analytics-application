package Aufgabe53;
import java.util.Scanner;

public class PrimeNumber {
	int value;
	boolean isPrime;
	
	public static void main (String args []){
		Scanner s = new Scanner(System.in);
		int value2 = s.nextInt();
		PrimeNumber ausgabe = new PrimeNumber(value2);
		System.out.println(ausgabe.isPrime);
	
	
	}
	public PrimeNumber(int primzahl) {
		for (int i = 2; i < primzahl; i++) {
			if (primzahl % i == 0) {
				isPrime = false;
				break;
			}else { isPrime= true;
					primzahl = value;
			}
		}
	}

}
