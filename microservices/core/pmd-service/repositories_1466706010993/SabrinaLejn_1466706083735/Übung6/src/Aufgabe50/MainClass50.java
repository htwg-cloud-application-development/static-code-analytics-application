package Aufgabe50;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainClass50 {
public static void main(String[] args) throws FileNotFoundException {
		
		int cPostcode;
		Scanner scan = new Scanner(System.in);

		System.out.println("Please enter a zip code: ");
		cPostcode = scan.nextInt();
		scan.close();
		
		Address Town= new Address();
		
		Town.setPostcode(cPostcode);
		System.out.print(Town.city);
		
}
}
