package Aufgabe11;

import java.util.ArrayList;
import java.util.Scanner;

public class Aufgabe29 {

	public static void main(String[] args) {

		Scanner s = new Scanner(System.in);

		boolean[] primeNumber = new boolean[101]; //booleean für Primzahl wahr oder falsch

		for (int i = 1; i <= 100; i++) {

			if (i < 2) {
				primeNumber[i] = false; // die 1 wird herausgenommen, da keine Primzahl
			} else {
				primeNumber[i] = true; // ich gehe davon aus das zu Anfang alle
										// Zahlen Primzahlen sind

				for (int j = 2; j < i; j++) {

					if (i % j == 0) { //Formel für Primzahlaussortierung

						primeNumber[i] = false; // alles zahlen die ohne rest teilbar sind sind keine Primzahlen								
					}
				}
			}
		}

		for (int i = 1; i < primeNumber.length; i++) { // hier beginnt die Primfaktorzerlegung

			if (primeNumber[i]) {

				System.out.println("Primzahl:" + i); //hier wird die Primzahl ausgegeben
				System.out.println("------------");

			} else { 
				if (i > 1) {
					System.out.println("keine Primzahl: " + i); 

					if (i != 0) {

						int a = i;
						int j = 0;

						ArrayList<Integer> list = new ArrayList<Integer>(); //hier wird eine Liste angelegt. In diese werden später die primzahlzerlegungen gespeichert

						while (a != 1) {	
							if (primeNumber[j] && a % j == 0) { //das ist die Bedingung für die Zerlegung der Primzahlen
								a = a / j; //hier wird die Primzahl zerlegt
								list.add(j); // hier wird das ausgegebene j in die liste gespeichert, j= die primzahlzerlegung
							} 
							
							else{
								
								j++; //hier wird "j" bis zur abbruchbedingung hochgezählt 

							}
						}
						
						//hier wird die Primfaktorzerlegung ausgegeben, einmal von der niedrigsten möglichen der liste (0), einmal die höchste Stelle (101)-1
						System.out.println("Primzahlzerlegung-> kleinste Primzahl: " + list.get(0)+ " ,größte Primzahl: " + list.get(list.size() - 1)); 
						System.out.println("------------------------------------------------------------");

					}
				}
			}
		}
	}
}