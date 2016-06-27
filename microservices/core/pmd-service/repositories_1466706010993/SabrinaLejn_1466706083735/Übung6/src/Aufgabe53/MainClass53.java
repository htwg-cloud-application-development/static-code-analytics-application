package Aufgabe53;

import java.util.Scanner;

public class MainClass53 {
	public static void main(String args[]) {
		Scanner s = new Scanner(System.in);  // neuer Scanner
						
				
				System.out.print("Please enter an integer: ");
				int eingabeUser = s.nextInt();
				s.close();
				PrimeNumber anInteger = new PrimeNumber(eingabeUser);
				
			if (anInteger.isPrime){
				System.out.println(anInteger.value + " is Prime");			}
			else {System.out.println(anInteger.value + " is not Prime");
			}
		
													
		
		
	
}}
														



