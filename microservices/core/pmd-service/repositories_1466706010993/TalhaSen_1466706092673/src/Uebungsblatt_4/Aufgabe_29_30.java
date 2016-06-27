package Uebungsblatt_4;

import java.util.ArrayList;
import java.util.Scanner;

public class Aufgabe_29_30 {

	public static void main(String[] args) {

			Scanner s = new Scanner(System.in);

			boolean[] primeNumber = new boolean[1001]; //booleean für Primzahl wahr oder falsch

			for (int i = 1; i <primeNumber.length; i++) {

				if (i < 2) {
					primeNumber[i] = false; // die 1 wird herausgenommen, da keine Primzahl
				} else {
					primeNumber[i] = true; // ich gehe davon aus das zu Anfang alle
											// Zahlen Primzahlen sind

					for (int z = 2; z < i; z++) {

						if (i % z == 0) { //Formel für Primzahlaussortierung

							primeNumber[i] = false; //wenn false dann ist es eine Primzahl! 
													

						}

					}
				}
			}

			for (int i = 1; i < primeNumber.length; i++) { // hier beginnt die Primfaktorzerlegung

				if (primeNumber[i]) {

					System.out.println("Primzahl:" + i); //hier wird die Primzahl ausgegeben
					System.out.println("             ");

				} else { 
					if (i > 1) {
						System.out.println("keine Primzahl: " + i); 

						if (i != 0) {

							int a = i;
							int b = 0;

							ArrayList<Integer> list = new ArrayList<Integer>(); //hier wird eine Liste angelegt

							while (a != 1) {
								if (primeNumber[b] && a % b == 0) { //
									a = a / b;
									list.add(b); // hier wird das ausgegebene j in die liste gespeichert

								} else

								{
									b++;

								}

							}

							System.out.println("Primzahlzerlegung-> kleinste Primzahl: " + list.get(0)+ " ,größte Primzahl: " + list.get(list.size() - 1));
							System.out.println("                                               ");

						}
					}
				}

			}
			System.out.println("");
			System.out.println("Geben sie eine Zahl ein. Diese wird dann im folgenden Verlauf überprüft.");
			int k = s.nextInt();
			
			if (primeNumber[k]==true){
				System.out.println("Die eingegebene Zahl ist eine Primzahl.");
				}
			else {
				if (k > 1) {
					System.out.println("keine Primzahl: " + k); 

					if (k != 0) {

						int a = k;
						int b = 0;

						ArrayList<Integer> list = new ArrayList<Integer>(); //hier wird eine Liste angelegt

						while (a != 1) {
							if (primeNumber[b] && a % b == 0) { //
								a = a / b;
								list.add(b); // hier wird das ausgegebene j in die liste gespeichert

							} else

							{
								b++;

							}

						}

						System.out.println("Primzahlzerlegung-> kleinste Primzahl: " + list.get(0)+ " ,größte Primzahl: " + list.get(list.size() - 1));
						System.out.println("                                      ");

					}
				}
			}

			}
	}
	