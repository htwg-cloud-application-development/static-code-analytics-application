import java.util.Scanner;

public class Aufgabe18 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		double Gewicht;
		Scanner Scanner = new Scanner(System.in);

		System.out.println("Geben Sie das Gewicht Ihrer Paketsendung ein:");

		Gewicht = Scanner.nextDouble();

		if (Gewicht >= 0.0 && Gewicht <= 2) {
			System.out.println("Ihre Sendung Kostet 4,99€");
		}

		if (2 < Gewicht && 5>=Gewicht) {
			System.out.println("Ihre Sendung Kostet 6,99€");
		}

		if (Gewicht > 5 && Gewicht <= 10) {
			System.out.println("Ihre Sendung Kostet 8,99€");
		}

		if (Gewicht > 10.1 && Gewicht <= 31.5) {
			System.out.println("Ihre Sendung Kostet 14,99€");
		}

		if (Gewicht > 31.6) {
			System.out.println("Ihre Sendung überschreitet das Maximal Gewicht!");
		}
	}
}