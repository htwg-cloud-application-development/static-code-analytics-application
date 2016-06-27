package Versand;

import java.util.*;
public class Address {

	public String getAdressLabel(){
		Scanner sc = new Scanner(System.in);
		System.out.println("Vorname:");
		String name = sc.nextLine();
		System.out.println("Nachname: ");
		String nachname = sc.nextLine();
		System.out.println("Straﬂe + Hausnummer: ");
		String adresse = sc.nextLine();
		System.out.println("Land: ");
		String land = sc.nextLine();
		System.out.println("Postleitzahl eingeben: ");
		int plz = sc.nextInt();
		System.out.println("Ort eingeben");
		String ort = sc.nextLine();
		return  name + " " + nachname + "\n" + adresse + "\n" + plz + "\n" + ort +"\n" + land;
	}
}
