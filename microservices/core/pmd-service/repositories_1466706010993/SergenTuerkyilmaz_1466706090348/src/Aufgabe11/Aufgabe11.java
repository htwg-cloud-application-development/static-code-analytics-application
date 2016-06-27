package Aufgabe11;

//Schreiben Sie ein Programm, das drei Ganzzahlen einliest und in sortierter Reihenfolge ausgibt (kleinster Wert zuerst).
import java.util.Scanner;

public class Aufgabe11 {

	public static void main(String[] args) {

		Scanner s = new Scanner(System.in);

		System.out.println("Hier drei Zahlen eingeben: ");
		int a = s.nextInt();
		int b = s.nextInt();
		int c = s.nextInt();

		if (a < b && a < c && b < c) {

			System.out.println(a + "," + b + "," + c);

		}

		else {

			if (b < c && b < a && a < c)
				System.out.println(b + "," + a + "," + c);

			else {

				if (c < b && c < a && a < b) {
					System.out.println(c + "," + a + "," + b);

				} else {

					if (c > b && c < a && a > b) {
						System.out.println(b + "," + c + "," + a);

					} else {

						if (c < b && c < a && a > b) {
							System.out.println(c + "," + b + "," + a);

						} else {

							if (c < b && c > a && a < b) {
								System.out.println(a + "," + c + "," + b);

							}
						}
					}
				}
			}
		}
	}
}