package Aufgabe11;

import java.util.Scanner;

public class Aufgabe13 {
	// 13.1
	public static void main(String[] args) {

		System.out.println("Hier das alter eingeben : ");
		Scanner s = new Scanner(System.in);

		int age = s.nextInt();
		int ticketpriceA = 20;
		int ticketpriceB = 10;

		int ticketprice = (age >= 16) ? ticketpriceA : ticketpriceB;

		System.out.println("Sie zahlen für das Ticket: " + ticketprice + "€");

		// 13.2

		System.out.println("Hier drei ganze Zahlen eingeben: ");
		int numberOne = s.nextInt();
		int numberTwo = s.nextInt();
		int numberThree = s.nextInt();
		String inRow = "Ja!";
		String notInRow = "Nein!";

		String row = (numberOne < numberTwo && numberTwo < numberThree && numberThree > numberOne) ? inRow : notInRow;

		System.out.println("Wurden die Zahlen in aufsteigender Reheinfolge eingegeben? " + row);

	}

}
