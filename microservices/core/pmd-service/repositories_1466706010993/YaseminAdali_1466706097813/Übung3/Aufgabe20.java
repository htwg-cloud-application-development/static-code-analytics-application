import java.util.Scanner;

public class Aufgabe20 {

	public static void main(String[] args) {

		Scanner scanner= new Scanner (System.in);
		System.out.println("Geben Sie eine Zahlen ein");
		int Zahl;
		Zahl= scanner.nextInt();
		System.out.println(Zahl);
		

		if (Zahl >=0 && Zahl<=19) {  // Vorlesungsfolie5
			//--->"String" Vorlesungsfolie6
			String[]Zahlenreihe= {"Null","Eins","Zwei","Drei","Vier","Fünf","Sechs","Sieben","Acht","Neun","Zehn","Elf","Zwölf","Dreizehn","Vierzehn","Fünfzehn","Sechszehn","Siebzehn","Achtzehn","Neunzehn"};
			String Zahl1=Zahlenreihe[Zahl];
			System.out.println(Zahl1);
			}
		
		else if ( Zahl%10==0){
		String[]Zahlenreihe2={"","","zwanzig","dreißig","vierzig","fünfzig","sechzig","siebzig","achtzig","neuzig"};
		String Zahl2=Zahlenreihe2[Zahl/10];
		System.out.println(Zahl2);
		}
		
		else if (Zahl >19 && Zahl%10!=0);
		String[]Zahlenreihe ={"","Ein","Zwei","Drei","Vier","Fünf","Sechs","Sieben","Acht","Neun"};
		String Zahl1=Zahlenreihe[Zahl%10];
		String[]Zahlenreihe2={"","","zwanzig","dreißig","vierzig","fünfzig","sechzig","siebzig","achtzig","neuzig"};
		String Zahl2=Zahlenreihe2[Zahl/10];
		
		System.out.println(Zahl1+"und"+Zahl2);
		
			}

}
