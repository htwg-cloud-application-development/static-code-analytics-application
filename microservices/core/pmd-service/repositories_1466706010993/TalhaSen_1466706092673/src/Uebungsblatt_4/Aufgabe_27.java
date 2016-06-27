package Uebungsblatt_4;
import java.util.Scanner;

public class Aufgabe_27 {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		System.out.println("Geben sie die Feldlänge ein!");
		int l = s.nextInt(); // selbstbestimmte Feldgröße
		int[] arrayA; //Array deklariert.
		arrayA = new int [l];// Array den Wert l zugewiesen. 
		int b = 0;
		
		for (int i = 0; i<arrayA.length;i++){
			double zufall = Math.random()*(100- -100)+ -100; //Zufallszahl für die Komponenten des Arrays.
			arrayA[i] = (int) zufall;
			System.out.println(arrayA[i] +" ");
			b = b + arrayA[i];
		}
		System.out.println("Die Summe lautet:"+b);
		System.out.println("Der Durchschnitt lautet:"+((float)b/l));

	}

}
