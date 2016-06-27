package snakesAndLadders;

import java.util.Scanner;

public class SnakesAndLadders {

	static Scanner s = new Scanner(System.in);
	static int wurfSp1;
	static int wurfSp2;
	static int positionSp1 = 1;
	static int positionSp2 = 1;
	static String name1;
	static String name2;

	public static void main(String[] args) {

		Spielstart();
		while (positionSp1 != 36 && positionSp2 != 36) {

			wurfSp1 = Wuerfel();
			wurfSp2 = Wuerfel();
			String wurf = "";
			WurfSp1(wurf);
			LeiterSp1();
			SchlangeSp2();
			Ueber36Sp1();
			WurfSp2(wurf);
			LeiterSp2();
			SchlangeSp2();
			Ueber36Sp2();
			Gewinner();
		}
	}

	public static void Spielstart() {
		System.out.println("+ + + Willkommen zu Snakes and Ledders + + +");
		System.out.println();
		System.out.println("Spieler 1 Name: ");
		name1 = s.nextLine();
		System.out.println();
		System.out.println("Spieler 2 Name: ");
		name2 = s.nextLine();
		System.out.println();
		System.out.println("+ + + Viel Glück " + name1 + " und " + name2 + " + + +");
	}

	public static int Wuerfel() {
		int zahl;
		zahl = (int) ((Math.random() * 11) + 2);
		return zahl;
	}

	public static void WurfSp1(String wurf) {

		System.out.println("\n" + name1 + " würfelt (etwas beliebiges eingeben)");
		wurf = s.next();
		System.out.println(name1 + " hat " + wurfSp1 + " gewürfelt");
		positionSp1 = positionSp1 + wurfSp1;
		System.out.println("#" + name1 + " befindet sich nun an Stelle " + positionSp1 + "#");

	}

	public static void WurfSp2(String wurf) {
		System.out.println("\n" + name2 + " würfelt (etwas beliebiges eingeben)");
		wurf = s.next();
		System.out.println(name2 + " hat " + wurfSp2 + " gewürfelt");
		positionSp2 = positionSp2 + wurfSp2;
		System.out.println("#" + name2 + " befindet sich nun an Stelle " + positionSp2 + "#");
	}

	public static void LeiterSp1() {

		int[] leitern = { 2, 5, 9, 18, 25 };
		int[] leiternEnde = { 15, 7, 27, 29, 35 };

		for (int i = 0; i < leitern.length; i++) {
			if (positionSp1 == leitern[i]) {
				positionSp1 = leiternEnde[i];
				System.out.println("#Eine Leiter! " + name1 + " rückt vor an Stelle " + positionSp1 + "#");
			}
		}
	}

	public static void LeiterSp2() {

		int[] leitern = { 2, 5, 9, 18, 25 };
		int[] leiternEnde = { 15, 7, 27, 29, 35 };

		for (int i = 0; i < leitern.length; i++) {
			if (positionSp2 == leitern[i]) {
				positionSp2 = leiternEnde[i];
				System.out.println("#Eine Leiter! " +name2 + " rückt vor an Stelle " + positionSp2 + "#");
			}

		}
	}

	public static void SchlangeSp1() {
		int[] schlangen = { 17, 20, 24, 32, 34 };
		int[] schlangen2 = { 4, 6, 16, 30, 12 };

		for (int i = 0; i < schlangen.length; i++) {
			if (positionSp1 == schlangen[i]) {
				positionSp1 = schlangen2[i];
				System.out.println("#Eine Schlange! " + name1 + " geht zurück an Stelle " + positionSp1 + "#");
			}
		}
	}

	public static void SchlangeSp2() {
		int[] schlangen = { 17, 20, 24, 32, 34 };
		int[] schlangen2 = { 4, 6, 16, 30, 12 };

		for (int i = 0; i < schlangen.length; i++) {
			if (positionSp2 == schlangen[i]) {
				positionSp2 = schlangen2[i];
				System.out.println("#Eine Schlange!" + name2 + "  geht zurück an Stelle " + positionSp2 + "#");
			}
		}
	}

	public static void Gewinner() {

		if (positionSp1 == 36) {
			System.out.println("+ + + " + name1 + " Gewinnt + + +");
		} else if (positionSp2 == 36) {
			System.out.println("+ + + " + name2 + " Gewinnt + + +");

		}

	}

	public static void Ueber36Sp1() {

		if (positionSp1 > 36) {
			positionSp1 = positionSp1 - wurfSp1;
			System.out.println("#Zu hoch gewürfelt, " + name1 + " zurück an Stelle " + positionSp1 + "#");
		}
	}

	public static void Ueber36Sp2() {

		if (positionSp2 > 36) {
			positionSp2 = positionSp2 - wurfSp2;
			System.out.println("#Zu hoch gewürfelt, " + name2 + " zurück an Stelle " + positionSp2 + "#");
		}
	}
}
