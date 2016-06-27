package aufgabe1;

import java.util.Scanner;

/**
 * @author Robin
 * @version 24.04.2016
 */

public class Array28 {

	/**
	 * Konstruktor wird nicht verwendet.
	 */
	private Array28() { }
	
	/**
	 * Scanner.
	 */
	private static final Scanner EINGABE = new Scanner(System.in);
	
	/**
	 * main.
	 * @param args wird nicht verwendet
	 */
	public static void main(String[] args) {
		System.out.println("Bitte ganzzahligen Wert eingeben: ");
		int arraysize = 0;
		if (EINGABE.hasNextInt()) {
			arraysize = EINGABE.nextInt();
		} else {
			System.out.println("Falsche Eingabe! Bitte ganzzahlige postiven Wert eingeben.");
		}
		if (arraysize > 0) {
			int[] anArray = new int[arraysize];
		
			for (int i = 0; i < anArray.length; i++) {
				anArray[i] = (int)(Math.random() * 201) - 100;
			}
		
			System.out.print("Werte: {");
			int sum = 0;
			int max = Integer.MIN_VALUE; //-101
			int min = Integer.MAX_VALUE; //101
			int minPos = 0;
			int maxPos = 0;
			
			for (int i = 0; i < anArray.length; i++) {
				sum = sum + anArray[i];
				if (min > anArray[i]) {
					min = anArray[i];
					minPos = i;
				}
				if (max < anArray[i]) {
					max = anArray[i];
					maxPos = i;
				}
				if(i < anArray.length - 1) {
					System.out.printf("%d, ", anArray[i]);
					//System.out.println("Werte: " + anArray[i]);
				} else {
					System.out.printf("%d}\nsize: %d\n", anArray[i], anArray.length);
				}
			}
			double durchschnitt = (double) sum / anArray.length;
			System.out.println("Summe: " + sum);
			System.out.println("Durchschnitt: " + durchschnitt);
			System.out.printf("Max: %d, Stelle: %d\nMin: %d, Stelle: %d",
					max, maxPos, min, minPos);
		
			} else {
				System.out.println("Falsche Eingabe! Bitte ganzzahlige postiven Wert eingeben.");
			}

	}

}
