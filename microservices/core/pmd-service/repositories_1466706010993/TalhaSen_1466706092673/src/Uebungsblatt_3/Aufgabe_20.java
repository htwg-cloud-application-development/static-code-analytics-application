package Uebungsblatt_3;

import java.util.Scanner;

public class Aufgabe_20 {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int z;

		System.out.println("Geben sie eine Ganzzahl ein!");
		z = s.nextInt();

		if (z >= 0 && z < 20) {
			String[] Einer = { "null", "eins", "zwei", "drei", "vier", "fünf", "sechs", "sieben", "acht", "neun",
					"zehn", "elf", "zwölf", "dreizehn", "vierzehn", "fünfzehn", "sechzehn", "siebzehn", "achtzehn",
					"neunzehn" };
			String a = Einer[z];
			System.out.println(a);

		} else if (z > 20 && z % 10 != 0) {
			String[] Einer = { "null", "ein", "zwei", "drei", "vier", "fünf", "sechs", "sieben", "acht", "neun" };
			String a = Einer[z % 10];

			String[] Zehner = { "", "zehn", "zwanzig", "dreißig", "vierzig", "fünfzig", "sechzig", "siebzig", "achtzig",
					"neunzig" };
			String b = Zehner[z / 10];
			System.out.println(a + "und" + b);

		} else if (z % 10 == 0) {
			String[] Zehner = { "", "zehn", "zwanzig", "dreißig", "vierzig", "fünfzig", "sechzig", "siebzig", "achtzig",
					"neunzig" };
			String c = Zehner[z / 10];
			System.out.println(c);

		}

	}

}
