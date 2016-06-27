package Ü06;
import java.util.Scanner;
public class Aufgabe47 {
	
	
	public static void main(String[] args) {

	Scanner s = new Scanner(System.in);
	
	System.out.println("Geben Sie eine Zahl ein.");
	int x = s.nextInt();
	
	System.out.println(PrimeNumber47.isPrime(x));
	}
}
