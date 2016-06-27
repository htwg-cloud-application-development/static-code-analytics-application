package Übungsblatt5;

import java.io.*;
import java.util.Scanner;

public class Aufgabe43 {

	public static void main(String[] args) throws FileNotFoundException {

		java.io.File txtdateiA = new java.io.File("//Users/srgntrkylmz/Documents/HTWG/Programmieren/Fächer.txt");
		Scanner y = new java.util.Scanner(txtdateiA, "ISO-8859-1");

		java.io.File txtdateiB = new java.io.File("//Users/srgntrkylmz/Documents/HTWG/Programmieren/Noten.txt");
		Scanner a = new java.util.Scanner(txtdateiB, "ISO-8859-1");

		String faecher[] = new String[28];
		double noten[] = new double[28];
		int ectsp[] = new int[28];

		System.out.println();

		for (int i = 0; i < 28; i++) {
			String kette = y.nextLine();
			String note = a.nextLine();

			kette = kette.replace('"', ' ');
			faecher[i] = kette;
			System.out.print(faecher[i] + " ");

			note = note.replace(',', '.');
			note = note.substring(0, note.length());
			double notez = Double.parseDouble(note);
			noten[i] = notez;
			System.out.print(notez);
			System.out.println("");
		}

		for (int i = 28; i < 56; i++) {
			String ectspk = y.nextLine();
			ectspk = ectspk.substring(0, ectspk.length() - 1);
			int ects = Integer.parseInt(ectspk);
			ectsp[i - 28] = ects;
		}

		double noteg;
		double gesnote = 0;
		double noteZ;
		double gesnoteZ = 0;

		for (int i = 0; i < faecher.length; i++) {
			noteg = noten[i] * ectsp[i] / 198;
			gesnote = gesnote + noteg;
		}

		for (int i = 0; i < 10; i++) {
			noteZ = noten[i] * ectsp[i] / 67;
			gesnoteZ = gesnoteZ + noteZ;
		}

		System.out.println("");
		double bnote = ((int) (gesnote * 10));
		double bgesnote = bnote / 10;

		double bzwnote = ((int) (gesnoteZ * 10));
		double bznote = bzwnote / 10;

		System.out.println(" Die Zwischenprüfungsnote ist: " + bznote);
		System.out.println(" Die Bachelorgesamtnote ist: " + bgesnote);

	}

}
