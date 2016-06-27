package Uebungsblatt_4;
import java.util.Scanner;

public class Aufgabe_28 {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		System.out.println("Geben sie die Feldlänge ein!");
		int l = s.nextInt(); // selbstbestimmte Feldgröße
		int[] arrayA;
		arrayA = new int [l];
	
		for (int i = 0; i<arrayA.length;i++){
			double zufall = Math.random()*(100- -100)+ -100;
			arrayA[i] = (int) zufall;
			System.out.println(arrayA[i] +" ");
		}
		int min = arrayA[0];
		int max = arrayA[0];
		int indexmin = 0;
		int indexmax = 0;
		
		for (int i = 0; i<arrayA.length; i++ ){
			if (arrayA[i]<min){
				min = arrayA[i];
				indexmin = i;
			}
		}
		for (int i = 0; i<arrayA.length;i++){
			if (arrayA[i]>max){
				max = arrayA[i];
				indexmax = i;
			}
		}
		System.out.println("Die größte Zahl lautet:"+max+" und hat den Index:"+indexmax);
		System.out.println("Die kleinste Zahl lautet:"+min+" und hat den Index:"+indexmin);
	}

}
