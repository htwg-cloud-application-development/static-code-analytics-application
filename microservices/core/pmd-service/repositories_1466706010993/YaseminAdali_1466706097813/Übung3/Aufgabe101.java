import java.util.Scanner;

public class Aufgabe101 {


	public static void main(String[] args) {

		Scanner scanner = new Scanner (System.in);
		System.out.println("Geben Sie 2 Ganzzahlen ein:");
		int a = scanner.nextInt();
		int b = scanner.nextInt();
		
		double ergebnis = (double) a/b;
		System.out.println(ergebnis);
		
	}

}
