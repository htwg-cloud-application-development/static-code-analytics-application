package Aufgabe49;
import java.util.Scanner;
public class PrimeNumber {
	Scanner s = new Scanner(System.in);
	int value = s.nextInt();
	public static void main(String args[]) {
		PrimeNumber ausgabe = new PrimeNumber();
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
