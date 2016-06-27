package aufgabe1;

import java.util.Scanner;

/**
 * @author Robin
 * @version 24.04.2016
 */

public class Array26 {

	/**
	 * Konstruktor wird nicht verwendet.
	 */
	private Array26() { }
	
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
		if (EINGABE.hasNextInt() && EINGABE.nextInt() > 0) {
			arraysize = EINGABE.nextInt();
		} else {
			System.out.println("Falsche Eingabe! Bitte ganzzahlige postiven Wert eingeben.");
		}
		int[] anArray = new int[arraysize];
		
		for (int i = 0; i < anArray.length; i++) {
			anArray[i] = (int)(Math.random() * 201) - 100;
		}
		
		System.out.print("Werte: {");
		for (int i = 0; i < anArray.length; i++) {
			if(i < anArray.length - 1) {
				System.out.printf("%d, ", anArray[i]);
				//System.out.println("Werte: " + anArray[i]);
			} else {
				System.out.printf("%d}\nsize: %d\n", anArray[i], anArray.length);
			}
		}

	}

}
