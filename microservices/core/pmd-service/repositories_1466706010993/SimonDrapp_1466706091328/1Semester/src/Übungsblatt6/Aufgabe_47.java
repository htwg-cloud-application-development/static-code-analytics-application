package Übungsblatt6;

import java.util.Scanner;

public class Aufgabe_47 {

	static Scanner s = new Scanner(System.in);

	public static void main(String[] args) {

		int zahl;

		do {													// do-while - Schleife, damit
			System.out.println("Geben Sie eine Zahl ein: ");	// keine negativen Zahlen und 0 
			zahl = s.nextInt();									// erlaubt sind

		} while (zahl <= 0);

		System.out.println(zahl + " ist eine Primzahl --> " + PrimeNumber.isPrime(zahl));
									
	}															// Klassenmethoden werden über die 
																// Klasse aufgerufen 
}																// zahl als Übergabeparameter
