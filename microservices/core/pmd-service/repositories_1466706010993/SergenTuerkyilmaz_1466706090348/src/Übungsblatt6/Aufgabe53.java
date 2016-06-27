package Übungsblatt6;

import java.util.Scanner;

public class Aufgabe53 {

	public static void main(String[] args) {

		Scanner y = new Scanner(System.in);
		int a = y.nextInt();

		konstrukt K = new konstrukt(a);

		System.out.println(K.isPrime);

	}
}
