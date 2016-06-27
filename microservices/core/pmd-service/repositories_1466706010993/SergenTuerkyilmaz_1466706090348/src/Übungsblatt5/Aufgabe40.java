package Übungsblatt5;

import java.util.Scanner;
import java.io.*;

public class Aufgabe40 {

	public static void main(String[] args) throws FileNotFoundException {

		java.io.File aFile = new java.io.File("//Users/srgntrkylmz/Documents/faust.webarchive");
		Scanner s = new java.util.Scanner(aFile, "ISO-8859-1");
		Scanner y = new Scanner(System.in);
	

		System.out.println("Geben Sie einen Buchstaben ein: ");
		
		final String a = y.next();
		char b = a.charAt(0);

		int number = 0;

		while (s.hasNextLine()) {
			String word = s.nextLine();

			char[] text = new char[word.length()];

			for (int i = 0; i < text.length; i++) {
				text[i] = word.charAt(i);
			}

			for (int i = 0; i < text.length; i++) {

				if (b == text[i]) {
					number++;

				}

			}

		}
		
		System.out.println("Im Text befinden sich " + number + " " + b + "´s");
	}

}
