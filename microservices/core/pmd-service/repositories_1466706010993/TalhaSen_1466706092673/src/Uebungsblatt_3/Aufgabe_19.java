package Uebungsblatt_3;

import java.util.Scanner;

public class Aufgabe_19 {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		double umrechnungskurs; // Umrechnungskurs.
		double betrag; //Währungsbetrag.
		int zielrichtung; // Wahl der Ziehlrichtung ($-€ oder €-$)

		System.out.println("Bitte geben sie den Umrechnungskurs an");
		umrechnungskurs = s.nextDouble();
		
		System.out.println("Bitte wählen sie die Zielrichtung");
		zielrichtung = s.nextInt();

		
		
		if (zielrichtung == 1) {
			System.out.println("Die Richtung ist von USD in Euro");
			betrag = s.nextDouble();
			System.out.println(betrag * umrechnungskurs +"€");
		}
		else
			System.out.println("Die Richtung ist von Euro in USD");
			betrag = s.nextDouble();
			System.out.println(betrag * umrechnungskurs +"$");
		
	}

}
