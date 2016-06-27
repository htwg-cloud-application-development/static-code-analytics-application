
import java.util.Scanner;
public class Aufgabe_28 {

	public static void main(String[] args) {
		
		//Aufgabe 28
		
		Scanner s = new Scanner (System.in);
		
		byte x;
		
		do {
			System.out.println("Geben Sie eine Feldgröße an.");
			x = s.nextByte();
		}
		while (x<1);
		
		int[] array = new int [x];
		int y;
		int sum = 0;
		
		for (int z = 0; z < array.length; z++) {
			y = (int)(Math.random() * 201) -100;
			array[z] = y;
			System.out.print(y + " ");
		}
		
		for (int z = 0; z < array.length; z++) {
			sum = array[z] + sum;
		}
		
		System.out.println("\nDie Summe der Komponenten ist " + sum);
		System.out.println("Der Durchschnitt beträgt " + ((float)sum/x));
		
		int min = array[0];
		int max = array[0];
		int indexmin = 0;
		int indexmax = 0;
		
		for (int z = 0; z < array.length; z++) {
			if (array[z] < min) {
				min = array[z];
				indexmin = z;
			}
		}
		
		for (int z = 0; z < array.length; z++) {
			if (array[z] > max) {
				max = array[z];
				indexmax = z;
			}
		}
		
		System.out.println("Die kleinste Zahl ist " + min + " und ist im Index " + indexmin);
		System.out.println("Die größte Zahl ist " + max + " und ist im Index " + indexmax);
		
		s.close();
	}
}
