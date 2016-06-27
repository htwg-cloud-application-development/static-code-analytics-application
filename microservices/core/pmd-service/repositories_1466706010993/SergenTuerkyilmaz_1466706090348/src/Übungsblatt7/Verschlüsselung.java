package Übungsblatt7;

import java.util.Scanner;

public class Verschlüsselung {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Geben Sie die erste Walzennummer ein:\n");
		int walze1 = scanner.nextInt();
		System.out.print("Geben Sie die zweite Walzennummer ein:\n");
		int walze2 = scanner.nextInt();
		System.out.print("Geben Sie die dritte Walzennummer ein:\n");
		int walze3 = scanner.nextInt();
		System.out.print("Geben Sie einen Schlüssel ein:\n");
		String schlüssel = scanner.next().toUpperCase();

		Aufgabe54_Enigma enigma = new Aufgabe54_Enigma(walze1, walze2, walze3, schlüssel);

		System.out.print("Möchten Sie Verschlüsseln oder Entschlüsseln?"
				+ " Wählen Sie für das verschlüsseln die 1 und für das entschlüsseln die 2"
				+ "\n 1. Verschlüsseln \n 2. Entschlüsslen\n");
		int x = scanner.nextInt();

		switch (x) {
		case 1:
			System.out.print("Geben Sie einen Text zur Verschlüsselung ein:\n");
			String text = scanner.next().toUpperCase();
			System.out.print("Verschlüsselter Text:");
			System.out.println(enigma.encrypt(text));
			break;
		case 2:
			System.out.print("Geben Sie einen Text zur Entschlüsselung ein:\n");
			String text2 = scanner.next().toUpperCase();
			System.out.println("Entschlüsselter Text:");
			System.out.println(enigma.decrypt(text2));
			break;
		default:
			System.out.println("Falsche Eingabe");
		}

		scanner.close();
	}
}
