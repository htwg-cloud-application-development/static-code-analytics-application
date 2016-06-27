import java.util.Scanner;
import java.lang.Math;

public class Aufgabe20_2 {

	public static void main(String[] args) {
		
		Scanner s = new Scanner(System.in);
		System.out.println("Geben Sie eine Gleitkommazahl ein:");
		double zahl = s.nextDouble();
		
		
			
		String[] zahlena = {"","eins","zwei","drei","vier","fünf","sechs","sieben","acht","neun","zehn","elf","zwölf","dreizehn","vierzehn","fünfzehn","sechzehn","siebzehn","achtzehn","neunzehn"};
		String zahl1 = zahlena[(int)zahl];
		String[] zahlenb = {"","eins","zwei","drei","vier","fünf","sechs","sieben","acht","neun"};
		String zahl2 = zahlenb[(int) ((zahl-Math.floor(zahl))*10)];
		String[] zahlenc = {"null","eins","zwei","drei","vier","fünf","sechs","sieben","acht","neun"};
		String zahl3 = zahlenc[(int) (zahl-((Math.floor(10.0 * zahl)) )/ 10.0)*100];
		
		System.out.println(zahl1+"komma"+zahl2+zahl3);
		
		
		

	}
}
