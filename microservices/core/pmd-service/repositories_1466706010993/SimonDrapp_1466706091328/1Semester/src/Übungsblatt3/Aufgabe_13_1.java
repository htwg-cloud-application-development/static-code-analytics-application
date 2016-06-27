package Übungsblatt3;

import java.util.Scanner;

public class Aufgabe_13_1 {

	public static void main(String[] args) {

		double ticketPrice = 20;
		double ticketPrice1 = 10;
		int age;

		Scanner s = new Scanner(System.in);

		System.out.print("Enter your age: ");
		age = s.nextInt();

		double price = (age >= 16) ? ticketPrice : ticketPrice1;

		System.out.println("Your ticketprice is: " + price + "€");

		s.close();

	}

}
