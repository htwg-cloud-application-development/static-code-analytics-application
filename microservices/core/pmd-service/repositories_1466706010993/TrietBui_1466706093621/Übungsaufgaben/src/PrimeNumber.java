/** Aufgabe 47*/
import java.util.Scanner;

public class PrimeNumber {
	public static void main (String args[]){
		Scanner s = new Scanner(System.in);
		int primzahl = s.nextInt();
		
		System.out.println(PrimeNumber.isPrime(primzahl));
	}

	public static boolean isPrime(int primzahl){
		boolean isPrim= false;
		for (int i = 2 ; i< primzahl ; i++){
			if (primzahl % i ==0){
				isPrim = false;
				break;
			} else{ isPrim = true;
			
		}
	}
		return isPrim;
}
	
}


