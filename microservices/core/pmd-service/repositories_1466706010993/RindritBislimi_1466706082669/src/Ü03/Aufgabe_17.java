package Ü03;

import java.util.Scanner;

public class Aufgabe_17 {

	public static void main (String[] args) {
		
		Scanner s = new Scanner (System.in);
		
		float x;
		float y;
		float z;
		
		System.out.println("Please enter your heigt");
		x = s.nextFloat();
		System.out.println("Please enter your weight");
		y = s.nextFloat();
	
		
		z = ((float)y / ((float)x * (float)x ));
		System.out.println(z);	

		if (z < 18.5)	{
			System.out.println("Under weight");
		}
		
		else{
			if	(z < 24.9)	{
				System.out.println("Normal");
				
			}
		
		else {
			if (z < 30)	{
				System.out.println("Fat");
	
			} 
		else {
			if (z < 40)	{
				System.out.println("Extrem Fat");	
			}
		else {
			System.out.println(" ");
			
		}
			}
		}
		}
		
			
		s.close();
		
	}
}
