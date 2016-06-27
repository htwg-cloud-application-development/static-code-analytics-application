import java.text.DecimalFormat;
import java.util.Scanner;

public class Aufgabe10 {
	public static void main(String[] args) {
		
		int Zahl1 = 10;
		int Zahl2 = 2;
		double Ergebnis = Zahl1/Zahl2;
		
		System.out.println(Ergebnis);
		
		int Zahl3 = 14;
		int Zahl4 = 8;
		double Ergebnis1 = Zahl3/Zahl4;
		
		System.out.println(Ergebnis1);
		
		int Zahl5 = 1;
		int Zahl6 = 2;
		double Ergebnis2 = (double)Zahl5/Zahl6;
		
		System.out.println(Ergebnis2);
		
		int Zahl7 = -2;
		int Zahl8 = 42;
		double Ergebnis3 = (double)Zahl7/Zahl8;
		
		System.out.println(Ergebnis3);
		
		int Zahl9 = 0;
		int Zahl10 = 1;
		double Ergebnis4 = (double)Zahl9/Zahl10;
		
		System.out.println(Ergebnis4);

		//10.2
		double Zahl;
		double Zahl11;
		Scanner meinScanner = new Scanner (System.in);
		System.out.println("Geben Sie die erste Gleitzahl ein");
		
		Zahl=meinScanner.nextDouble();
		
		System.out.println("Geben Sie die zweite Gleitzahl ein");
		
		Zahl11=meinScanner.nextDouble();
		double Ergebnis5 = Zahl*Zahl11;
		int Ergebnis10 = (int)Ergebnis5;
		System.out.println("Gleitkomma "+Ergebnis5+"   Ganzzahl "+Ergebnis10);
		double Nachkomma=Ergebnis5-Ergebnis10;
		System.out.println("Nachkommastellen   "+Nachkomma);
		
		
}
}