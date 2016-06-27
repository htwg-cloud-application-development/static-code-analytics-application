package Übungsblatt6;

import java.util.Scanner;

public class Aufgabe_49 {
	
	public static void main(String[] args) {
		
		Scanner s = new Scanner(System.in);
		
		PrimeNumber_49 prime = new PrimeNumber_49(); //Objekt prime der 
													// Klasse PrimeNumber_49 erzeugen
		
		do{
		System.out.println("Geben Sie eine Zahl ein: ");
		prime.value = s.nextInt(); //über das Objekt auf variable zugreifen
		
		}while(prime.value <= 0);
		
		System.out.println(prime.value+" ist eine Primzahl ---> "+prime.isPrime()); //Instanzmethode
																					// aufrufen
		
		
		
	}
}


	

