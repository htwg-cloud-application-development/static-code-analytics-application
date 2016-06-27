package Ü03;

import java.util.Scanner;

public class Aufgabe_14 {

	public static void main (String[] args) {
		
		Scanner s = new Scanner (System.in);
		
		// While Schleife
		int wid = 20;
		while (wid > 0) {
			System.out.println(wid);
			wid -= 2;
		}
		
		// Do-While Schleife
		
		do	{System.out.println(wid);
		wid -= 2;
		}
		while (wid > 0);
		
		s.close();
		
		
		
		}
		
}