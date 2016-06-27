package Aufgabe11;

import java.util.Scanner;

public class Aufgabe23_2 {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int a; // a ist meine Obergrenze
		int b = 0;// es fängt bei 0 an zu zählen

		System.out.println("Geben Sie eine Obergrenze ein: ");

		a = s.nextInt();

		int i = 1;

		while (i <= a) {

			System.out.println(i);
			b = b+ i;	// B wird als Summe von b und i deklariert
			i++;		//Zähler

		}
			

			System.out.println("Das Ergebnis der Summe ist:" + b);	// das "summierte" b wird ausgegeben
			System.out.println("Der Durschnitt lautet: " + (float)b / a); // der durchschnitt wird ausgegeben als fließkommazahl

		}

}


