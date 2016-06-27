import java.util.Scanner;

public class Aufgabe20 {

	public static void main(String[] args) {
		
		Scanner s = new Scanner(System.in);
		System.out.println("Geben Sie eine Zahl ein");
		int zahl = s.nextInt();
		
		if (zahl >=0 && zahl <=19){
		
		String[] zahlena = {"null","eins","zwei","drei","vier","fünf","sechs","sieben","acht","neun","zehn","elf","zwölf","dreizehn","vierzehn","fünfzehn","sechzehn","siebzehn","achtzehn","neunzehn"};
		String zahl1 = zahlena[zahl];
		System.out.println(zahl1);
		
		}
		
		else if  (zahl > 19 && zahl%10!=0){
		String[] zahlena = {"","ein","zwei","drei","vier","fünf","sechs","sieben","acht","neun"};
		String zahl1 = zahlena[zahl%10];
		String[] zahlenb = {" "," ","zwanzig","dreißig","vierzig","fünfzig","sechzig","siebzig","achtzig","neunzig"};
		String zahl2 = zahlenb[zahl/10];
		
		System.out.println(zahl1+"und"+zahl2);
		}
		
		else if (zahl%10==0){
			String[] zahlenb = {" "," ","zwanzig","dreißig","vierzig","fünfzig","sechzig","siebzig","achtzig","neunzig"};
			String zahl2 = zahlenb[zahl/10];
			System.out.println(zahl2);
		}
		
		}
		

		
			
}