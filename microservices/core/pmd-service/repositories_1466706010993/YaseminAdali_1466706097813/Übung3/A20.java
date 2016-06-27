import java.util.Scanner;

public class A20 {

	private static Scanner scanner;

	public static void main(String[] args) {
		
		double Zahl;
		Scanner Scanner = new Scanner (System.in);
		System.out.println("Geben Sie die erste Gleitzahl ein");
		
		Zahl=Scanner.nextDouble();
		
		if ((int)Zahl >=0 && Zahl>=10); {  // Vorlesungsfolie5
			//--->"String" Vorlesungsfolie6
			String[]Zahlenreihe= {"Null komma","Eins komma","Zwei komma","Drei komma","Vier komma","Fünf Komma","Sechs Komma","Sieben Komma","Acht Komma","Neun Komma","Zehn Komma"};
			String Zahl1=Zahlenreihe[(int) Zahl];
			String[]Zahlenreihe1 ={"Null","Ein","Zwei","Drei","Vier","Fünf","Sechs","Sieben","Acht","Neun"};
			String Zahl2=Zahlenreihe1[(int) (Zahl-Math.floor(Zahl))*10];
			System.out.println(Zahl1+ Zahl2);
			}

	}

}
