package Aufgabe11;

import java.util.Scanner;
//20.1
public class Aufgabe20 {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		System.out.println("Geben Sie eine Dezimalzahl ein: ");

		int y = s.nextInt();

		if (y >= 0 && y < 13) {

			String[] Einer = { "null", "eins", "zwei", "drei", "vier", "fünf", "sechs", "sieben", "acht", "neun","zehn", "elf", "zwölf" };
			String a = Einer[y];
			
			System.out.print(a);
		}

		else if (y > 13 && y % 10 != 0) {

			String[] Einer = { "null", "eins", "zwei", "drei", "vier", "fünf", "sechs", "sieben", "acht", "neun", "zehn", "elf", "zwölf" };
			String a = Einer[y % 10];

			String[] Zehner = { "", "zehn", "zwanzig", "dreißig", "vierzig", "fünfzig", "sechzig", "siebzig", "achtzig", "neunzig" };
			String b = Zehner[y / 10];
		
			System.out.println(a + "und" + b);
			
		} else if (y % 10 == 0) {

			String[] Zehner = { "", "zehn", "zwanzig", "dreißig", "vierzig", "fünfzig", "sechzig", "siebzig", "achtzig", "neunzig" };
			String c = Zehner[y / 10];
			
			System.out.println(c);

		}
	}
}