package Uebungsblatt4;
import java.util.Scanner;
import java.util.*;
public class Aufgabe29_30_neu {

	public static void main(String[] args) {
		

		{
			Scanner s = new Scanner(System.in);
			// eingabe durch die Konsole primzahl oder nicht primzahl wird dann von der Liste ausgegeben
			boolean[] primeNumber = new boolean[1001]; //booleean für Primzahl wahr oder falsch

			for (int i = 1; i <= 1000; i++) {
			if (i < 2) {
			primeNumber[i] = false; // die 1 wird herausgenommen, da keine Primzahl
			} else {
			primeNumber[i] = true; // man geht davon aus, dass alle zahlen primzahlen sind
			for (int j = 2; j < i; j++) {
			if (i % j == 0) { //Formel für Primzahlaussortierung
			primeNumber[i] = false; //wenn false dann ist es eine Primzahl! 

			}
			}
			}
			}

			for (int i = 1; i < primeNumber.length; i++) { // hier beginnt die Primfaktorzerlegung
			if (primeNumber[i]) {
			System.out.println("Primzahl:" + i); //primzahl wird ausgegeben
			System.out.println("------------");
			} else { 
			if (i > 1) {
			System.out.println("keine Primzahl: " + i); 
			if (i != 0) {
			int a = i;
			int j = 0;
			ArrayList<Integer> list = new ArrayList<Integer>(); //liste anlegen
			while (a != 1) {
			if (primeNumber[j] && a % j == 0) { //
			a = a / j;
			list.add(j); // hier wird das ausgegebene j in die liste gespeichert
			} else
			{
			j++;
			}
			}
			System.out.println("Primzahlzerlegung-> kleinste Primzahl: " + list.get(0)+ " ,größte Primzahl: " + list.get(list.size() - 1));
			System.out.println("------------------------------------------------------------");
			}
			}
			}
			}


			System.out.println("");
			System.out.println("Geben Sie eine Zahl ein, die dann überprüft wird ob es eine Primzahl ist: ");
			int y = s.nextInt();

			if(primeNumber[y] == true)
			{
			System.out.println("Die eingegebene Zahl ist eine Primzahl");
			}
			else
			{
			if (y > 1) {
			System.out.println("keine Primzahl!"); 
			if (y != 0) {
			int a = y;
			int j = 0;
			ArrayList<Integer> list = new ArrayList<Integer>(); //hier wird eine Liste angelegt
			while (a != 1) {
			if (primeNumber[j] && a % j == 0) { //
			a = a / j;
			list.add(j); // hier wird das ausgegebene j in die liste gespeichert
			} else
			{
			j++;
			}
			}
			System.out.println("Primzahlzerlegung-> kleinste Primzahl: " + list.get(0)+ " ,größte Primzahl: " + list.get(list.size() - 1));
			System.out.println("------------------------------------------------------------");
			}
			}
			}


			s.close();
			}


			}
		
	}


