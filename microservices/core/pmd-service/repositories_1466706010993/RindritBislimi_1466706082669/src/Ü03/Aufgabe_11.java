package Ü03;

import java.util.Scanner; 

public class Aufgabe_11 {

	public static void main (String[] args) {
	
	// Aufgabe 11
	
	int x;
	int y;
	int z;
	
	
	Scanner s = new Scanner (System.in);
	
	System.out.println("First Number");
	x = s.nextInt();
	System.out.println("Second Number");
	y = s.nextInt();
	System.out.println("Third Number");
	z = s.nextInt();
	
	
											// Bsp: 1,2,3
									// Reihenfolge: 1,2,3
	if ((x < y) && (y < z))	{	
		System.out.println(x + "," + y + "," + z);
	} 
									//Reihenfolge: 1,3,2 
	else if ((x < z) && (z < y)) {
		System.out.println(x + "," + z + "," + y);
	}
									//Reihenfolge: 3,1,2
	else if ((y < z) && (z < x)) {
		System.out.println(y + "," + z + "," + x);
	}
									//Reihenfolge: 3,2,1
	else if((y < x) && (z < y)) {
		System.out.println(z + "," + y + "," + x);
	}
									//Reihenfolge: 2,1,3
	else if ((x < z) && (y < z)) {
		System.out.println(y + "," + x + "," + z);
	}
									//Reihenfolge: 2,3,1
	else if ((x < y ) && (z < x)) {
		System.out.println(z + "," + x + "," + y);
	}
	
	s.close();
	
	}
}

