import java.util.Scanner;

//Augabe49
// wie aufgabe 47 nur andere namen
public class PrimeNumber2 {

	
	Scanner s = new Scanner(System.in);
	int value = s.nextInt(); 

	public static void main(String args[]) {
		PrimeNumber2 ausgabe = new PrimeNumber2();
		System.out.println(ausgabe.isPrime());
	}

	public boolean isPrime() { //Aufgabe 29 ü4
		boolean isPrim = false;
		for (int i = 2; i <value; i++) {// statt primzahl value
			if (value % i == 0) {
				isPrim = false;
				break;
			} else {
				isPrim = true;
			}
		}
		return isPrim;

	}
}