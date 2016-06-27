package Aufgabe11;

import java.util.Scanner;
//Aufgabe 26+27+28
public class Aufgabe26 {

	public static void main(String[] args) {

		Scanner s = new Scanner(System.in);

		System.out.println("Hier die Feldgröße eingeben: ");

		int a = s.nextInt(); // a ist die Feldgröße
		int sum = 0; // hier wird später die Summe der Zahlen eingefüllt
		int b = 0; //smallest
		int c= 0;	// biggest
		
		int[] array = new int[a]; // hier wird das array angelegt / durch a wird die Feldgröße in das Array geschrieben

		for (int i = 0; i <= array.length - 1; i++) {

			double random = Math.random() * (100 - (-100)) + (-100); // eine zufällige zahl zwischen 0,0 und 1,0 multipliziert mit den min-max werten

			array[i] = (int) random; 
			System.out.println(array[i] + "");

			sum = sum + (int) random;  // die summe aller zahlen in der Feldgröße wird berechnet
		}

		int smallest = array[0];
		int biggest = array[0];

		for (int x = 1; x <= array.length - 1; x++) {

			if (array[x] < smallest) {
				smallest = array[x];	
				b =x; // b wird als smallest deklariert
			}

		}
		for (int x = 1; x <= array.length - 1; x++) {

			if (array[x] > biggest) {
				biggest = array[x];
				c=x; // c wird als biggest deklariert
			}
		}
		System.out.println("Die Summer dieser Zahlen lautet: " + sum);

		System.out.println("Der Durschnitt dieser Zahlen lautet: " + sum / (double) a);

		System.out.println("smallest: " + smallest + " steht an der " +b+"ten Stelle im Index:");
		
		System.out.println("biggest: " + biggest + " steht an der " +c+"ten Stelle im Index");
		
	}
}
