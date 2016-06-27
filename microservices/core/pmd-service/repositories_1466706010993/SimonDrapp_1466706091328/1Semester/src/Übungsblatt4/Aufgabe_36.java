package Übungsblatt4;

import java.io.*;
import java.util.Scanner;

public class Aufgabe_36 {

	static int post;

	public static void main(String[] args) throws IOException {

		Scanner s = new Scanner(System.in);

		System.out.println("Bitte geben Sie eine Postleitzahl ein: ");
		post = s.nextInt();
		System.out.println();

		if (post == 78464) {
			lade1Datei();
		}

		else if (post == 78462) {
			lade2Datei();
		} else if (post == 78467) {
			lade3Datei();
		} else if (post == 78465) {
			lade4Datei();
		} else {
			System.out.println("Postleitzahl nicht korrekt!");
		}
	}

	public static void lade1Datei() {

		try {
			File meineDatei = new File("C:\\Users\\simon\\Desktop\\Programmieren\\first.txt");
			FileReader f = new FileReader(meineDatei);

			BufferedReader b = new BufferedReader(f);

			String zeile = null;

			while ((zeile = b.readLine()) != null) {
				System.out.println(zeile);
			}
			b.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void lade2Datei() {

		try {
			File meineDatei = new File("C:\\Users\\simon\\Desktop\\Programmieren\\second.txt");
			FileReader f = new FileReader(meineDatei);

			BufferedReader b = new BufferedReader(f);

			String zeile = null;

			while ((zeile = b.readLine()) != null) {
				System.out.println(zeile);
			}
			b.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void lade3Datei() {

		try {
			File meineDatei = new File("C:\\Users\\simon\\Desktop\\Programmieren\\third.txt");
			FileReader f = new FileReader(meineDatei);

			BufferedReader b = new BufferedReader(f);

			String zeile = null;

			while ((zeile = b.readLine()) != null) {
				System.out.println(zeile);
			}
			b.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void lade4Datei() {

		try {
			File meineDatei = new File("C:\\Users\\simon\\Desktop\\Programmieren\\fourth.txt");
			FileReader f = new FileReader(meineDatei);

			BufferedReader b = new BufferedReader(f);

			String zeile = null;

			while ((zeile = b.readLine()) != null) {
				System.out.println(zeile);
			}
			b.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

// FileReader f = new
// FileReader("C:\\Users\\simon\\Desktop\\Programmieren\\Baeckerei.txt");
// BufferedReader b = new BufferedReader(f);
// int n = 0;
// String cnt = b.readLine();
//
// while(cnt != null){ // wie viele Zeilen hat die Datei
// System.out.println(cnt);;
// cnt = b.readLine();
// }
// f.close();
//
// String[] content = new String[n]; // String länge der Zeilen
// f = new
// FileReader("C:\\Users\\simon\\Desktop\\Programmieren\\Baeckerei.txt");
// b = new BufferedReader(f);
//
// for (int i = 0; i < n ; i++) {
//
// content[i] = b.readLine();
// }
// f.close();
// for(String x:content){
// System.out.println();
// }
