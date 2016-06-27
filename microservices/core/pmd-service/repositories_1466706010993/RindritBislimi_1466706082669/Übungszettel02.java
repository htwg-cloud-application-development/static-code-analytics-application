import  java.util.Scanner;		//Aufgabe 8.2


public class Übungszettel02 {
	
	public static void main(String[] args) {
		
		
		// Aufgabe 4+5
		
		
		byte winStudierende = 40;
		short htwgStudierende = 5000;
		int euBevölkerung = 742000000;
		long weltBevölkerung = 7000000000L; 
		byte studieren = 7;
		char buchstabe = 'B';
		float note = 3.5F;
		boolean schaltjahr = false;
		
		System.out.println(winStudierende);
		
		
		
		//Aufgabe 6
		
		double euBevölkerung2 = euBevölkerung;
		double weltBevölkerung2 = weltBevölkerung;
		
		System.out.println((euBevölkerung2/weltBevölkerung2)*100+"%");
		
		
		
		// Aufgabe 7
		
		double binär = 2;
		double oktal = 8;
		double dezimal = 10;
		double hexal = 16;
		double sexal = 60;
		
		System.out.println(Math.pow(binär, 0));
		System.out.println(Math.pow(binär, 1));
		System.out.println(Math.pow(binär, 2));
		System.out.println(Math.pow(binär, 3));
		
		System.out.println(Math.pow(oktal, 0));
		System.out.println(Math.pow(oktal, 1));
		System.out.println(Math.pow(oktal, 2));
		System.out.println(Math.pow(oktal, 3));
		
		System.out.println(Math.pow(dezimal, 0));
		System.out.println(Math.pow(dezimal, 1));
		System.out.println(Math.pow(dezimal, 2));
		System.out.println(Math.pow(dezimal, 3));
		
		System.out.println(Math.pow(hexal, 0));
		System.out.println(Math.pow(hexal, 1));
		System.out.println(Math.pow(hexal, 2));
		System.out.println(Math.pow(hexal, 3));
		
		System.out.println(Math.pow(sexal, 0));
		System.out.println(Math.pow(sexal, 1));
		System.out.println(Math.pow(sexal, 2));
		System.out.println(Math.pow(sexal, 3));
				
		// oder
		System.out.println(Math.pow(8,2));
		
		
		
		// Aufgabe 8
		

		System.out.println("Hallo Welt; Rindrit Bislimi");
		

		String vorname; 
		String nachname; 
		Scanner in = new Scanner (System.in);
		
		System.out.println("Enter your first name");
		vorname = in.nextLine();
		
		System.out.println("Enter your last name");
		nachname = in.nextLine();
		
		System.out.println (nachname + " "+ vorname);
		System.out.println (vorname + " "+ nachname);
		
				
		
	}
}