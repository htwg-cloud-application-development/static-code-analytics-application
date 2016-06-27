package Aufgabe11;

import java.util.Scanner;

public class Aufgabe23_6 {

	public static void main(String[] args) {

		Scanner s = new Scanner(System.in);

		int a; // a ist meine Obergrenze
		int b = 0;// es fängt bei 0 an zu zählen

		System.out.println("Geben Sie eine Obergrenze ein: ");

		a = s.nextInt();

		for (int i = 1; i <= a; i++) { 

			b = b + i*i; // i*i --> eingabe 
			System.out.println(i);

		}

		System.out.println("Das Ergebnis der Summe ist:" + b);
		System.out.println("Der Durschnitt lautet: " + (float) b / a);

	}

}