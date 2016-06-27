import java.util.Arrays;
import java.util.Scanner;

public class Aufgabe26 {

	public static void main(String[] args) {

		Scanner s = new Scanner(System.in);
		System.out.println("Wie groß soll der Array sein?");
		int groesse = s.nextInt();

		int[] zahlenArray = new int[groesse];

		// Ausgabe des unsortierten Arrays
		System.out.print("unsortierter Array: ");
		for (int i = 0; i < zahlenArray.length; i++) {
			zahlenArray[i] = (int) (Math.random() * 201) - 100;
			System.out.print(zahlenArray[i] + " ");

		}
		int kleinsteZahl = 101;
		int groessteZahl = -101;
		int indexkZ = 0;
		int indexgZ = 0;
		for (int i = 0; i < zahlenArray.length; i++) {

			// Prüfen auf kleinste Zahl
			if (zahlenArray[i] < kleinsteZahl) {
				kleinsteZahl = zahlenArray[i];
				indexkZ = i;
			}
			// Prüfen auf größte Zahl
			if (zahlenArray[i] > groessteZahl) {
				groessteZahl = zahlenArray[i];
				indexgZ = i;
			}

		}
		// Ausgabe von kleinsten und größten Zahl mit jeweiligem Index
		System.out.println(" ");
		System.out.println("kleinste Zahl: " + kleinsteZahl + " an der Stelle: " + indexkZ);

		System.out.println("größte Zahl: " + groessteZahl + " an der Stelle: " + indexgZ);

		System.out.println(" ");
		// Mit Arrays.sort wird der Array automatisch sortiert
		Arrays.sort(zahlenArray);
		System.out.print("sortierter Array: ");
		for (int i = 0; i < zahlenArray.length; i++) {
			System.out.print(+zahlenArray[i] + " ");
		}
		// Durchschnittsberechnung mit for-Schleife
		double durchschnitt;
		int summe = 0;
		for (int i = 0; i < zahlenArray.length; i++) {
			summe = zahlenArray[i] + summe;

		}
		durchschnitt = (double) summe / zahlenArray.length;

		// Ausgabe Durchschnitt und Summe
		System.out.println(" ");
		System.out.println("Summe: " + summe);
		System.out.println("Durchschnitt: " + durchschnitt);
	}

}
