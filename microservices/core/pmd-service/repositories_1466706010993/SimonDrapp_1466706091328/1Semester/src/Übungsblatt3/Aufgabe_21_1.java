package Übungsblatt3;

import java.util.Scanner;

public class Aufgabe_21_1 {

	public static void main(String[] args) {

		int zahl;

		Scanner s = new Scanner(System.in);

		System.out.println("Geben Sie eine Ganzzahl zwischen 1 und 5999");
		zahl = s.nextInt();

		// if(zahl > 999 && zahl < 6000){

		String[] a = { "", "M", "MM", "MMM", "MMMM", "MMMMM" };
		String b = a[(int) (zahl / 1000)];
		System.out.print(b);

		String[] c = { "", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM" };
		String d = c[(int) ((zahl / 100) % 10)];
		System.out.print(d);

		String[] e = { "", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC" };
		String f = e[(int) ((zahl / 10) % 10)];
		System.out.print(f);

		String[] g = { "", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX" };
		String h = g[(int) ((zahl / 1) % 10)];
		System.out.print(h);

		// if(zahl > 99 && zahl < 1000){
		//
		//
		// String[]c = {"","C","CC","CCC","CD","D","DC","DCC","DCCC","CM"};
		// String d = c[(int) ((zahl / 100)%10)];
		// System.out.print(d);
		//
		// String[]e = {"","X","XX","XXX","XL","L","LX","LXX","LXXX","XC"};
		// String f = e[(int)((zahl / 10)%10)];
		// System.out.print(f);
		//
		// String[]g = {"","I","II","III","IV","V","VI","VII","VIII","IX"};
		// String h = g[(int)((zahl / 1)%10)];
		// System.out.print(h);
		// }
		//
		// if(zahl > 9 && zahl < 100){
		//
		// String[]e = {"","X","XX","XXX","XL","L","LX","LXX","LXXX","XC"};
		// String f = e[(int)((zahl / 10)%10)];
		// System.out.print(f);
		//
		// String[]g = {"","I","II","III","IV","V","VI","VII","VIII","IX"};
		// String h = g[(int)((zahl / 1)%10)];
		// System.out.print(h);
		// }
		//
		// if(zahl > 0 && zahl < 10){
		//
		// String[]g = {"","I","II","III","IV","V","VI","VII","VIII","IX"};
		// String h = g[(int)((zahl / 1)%10)];
		// System.out.print(h);
		// }

	}

}