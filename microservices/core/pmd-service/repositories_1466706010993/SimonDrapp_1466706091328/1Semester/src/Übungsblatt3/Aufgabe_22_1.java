package Übungsblatt3;

import java.util.Scanner;

public class Aufgabe_22_1 {

	public static void main(String[] args) {

		int zahl;
		int div = 0;

		Scanner s = new Scanner(System.in);

		System.out.println("Geben sie eine Ganzzahl ein: ");
		zahl = s.nextInt();

		for (int i = 1; i <= zahl; i++) {

			if (zahl % i == 0) {
				div++;
			}

		}

		if (div == 2) {
			System.out.println(zahl + " ist eine Primzahl");
		} else {
			System.out.println(zahl + " ist KEINE Primzahl");
		}

	}

}