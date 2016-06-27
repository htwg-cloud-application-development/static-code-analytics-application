package Ü03;

import java.util.Scanner;


public class Aufgabe_18 {

	public static void main (String[] args) {
		
		Scanner s = new Scanner (System.in);
		
		double packetWeight;
		
		
		System.out.println("Please enter the packet weight");
		packetWeight = s.nextDouble();
		
		
		if (packetWeight < 2.0)	{
			System.out.println("The packet price is: 4.99€");
		}
		else {
			if (packetWeight < 5.0) {
				System.out.println("The packet Price is: 5.99€");
				}
		
		else{
			if (packetWeight < 10.0) {
				System.out.println("The packet price is: 7.99€");
		}
		else {
			if (packetWeight < 31.5) {
				System.out.println("The packet price is: 13.99€");	
			}
		else {	
				System.out.println("The packet is to heasy");
					
				
					}
				}
			
			}
		}
		
	}
}
