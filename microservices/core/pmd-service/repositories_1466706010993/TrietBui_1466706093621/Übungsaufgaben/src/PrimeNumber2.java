/**Aufgabe49*/
import java.util.Scanner;

public class PrimeNumber2 {
	Scanner s = new Scanner(System.in);
	int value = s.nextInt();

	public static void main(String args[]) {
		PrimeNumber2 ausgabe = new PrimeNumber2();
		System.out.println(ausgabe.isPrime());
	}

	public boolean isPrime() {
		boolean isPrim = false;
		for (int i = 2; i <value; i++) {
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