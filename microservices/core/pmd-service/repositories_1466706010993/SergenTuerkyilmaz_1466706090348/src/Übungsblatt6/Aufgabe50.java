package Übungsblatt6;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Aufgabe50 {

	public static void main(String[] args) throws FileNotFoundException {

		postCodeAufgabe50 Obj = new postCodeAufgabe50();

		System.out.println("Geben Sie eine PLZ ein: ");
		Scanner s = new Scanner(System.in);
		int a = s.nextInt();
		Obj.setPostcode(a);

		System.out.println(a + Obj.city);
	}
}
