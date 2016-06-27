package Aufgabe63bis68;

import java.io.FileNotFoundException;


public class MainAddress {

	public static void main(String[] args) throws FileNotFoundException {
		
		Address a = new Address();
		
		a.setVornameNachname("Muster Mann");
		a.setStrasseHausnummer("Musterstraﬂe. 5");
		a.setPlz("78464");
		a.setOrt("Litzelstette");
		a.setLand("Deutschland");
		
		
		System.out.println(a.getAdressLabel());
		
		System.out.println(a.getLand());
		System.out.println();
		System.out.println(a.getOrt());
		System.out.println();
		System.out.println(a.getPlz());
		
		
		
		
	}
	 
	
	
	}


