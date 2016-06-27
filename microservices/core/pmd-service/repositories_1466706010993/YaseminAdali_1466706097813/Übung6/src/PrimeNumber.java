import java.util.Scanner;

//Aufgabe 47


 public class PrimeNumber {
	public static void main (String args[]){
		System.out.println("Geben Sie eine Zahl ein:");
		Scanner s = new Scanner(System.in);
		int primzahl = s.nextInt();
		
		System.out.println(PrimeNumber.isPrime(primzahl));
	}

	public static boolean isPrime(int primzahl){ //Aufgabe29 ü4
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