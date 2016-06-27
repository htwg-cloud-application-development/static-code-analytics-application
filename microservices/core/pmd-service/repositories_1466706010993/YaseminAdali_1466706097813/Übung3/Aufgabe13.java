import java.util.Scanner;

public class Aufgabe13 {
	public static void main(String[] args) {

		Scanner meinScanner = new Scanner(System.in);

		System.out.println("Geben Sie drei Zahlen ein");
		
		int Zahl = meinScanner.nextInt();
		int Zahl1 = meinScanner.nextInt();
		int Zahl3 = meinScanner.nextInt();
		
	
	
		String i2 = (Zahl < Zahl1 && Zahl1 < Zahl3) ?"ja": "nein";
		
		System.out.println(i2);

	}
}