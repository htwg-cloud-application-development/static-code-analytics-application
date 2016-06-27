package Aufgabe48;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainClass {
	public static void main(String[] args) throws FileNotFoundException {
		
		int bPostcode;
		Scanner scan = new Scanner(System.in);

		System.out.println("Please enter a zip code: ");
		bPostcode = scan.nextInt();
		scan.close();
		
		Address Town= new Address();
		Town.postcode=bPostcode;
		Town.city= Address.postcodeToCity(bPostcode);
		
}
}