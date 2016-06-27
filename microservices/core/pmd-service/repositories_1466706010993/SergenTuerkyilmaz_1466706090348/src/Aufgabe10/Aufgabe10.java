
//Aufgabe 10

package Aufgabe10;

import java.util.Scanner;//Zugriff auf die Scanner Bib wird ermöglicht

public class Aufgabe10 {

	public static void main(String[] args) {
		
		Scanner s = new Scanner(System.in);
		
		//Aufgaben 10.1/ 10.2
		
		int numberOne;
		int numberTwo;
		double numberThree;
		double numberFour;
		
		
		System.out.println("Geben Sie eine Zahl ein :");//Int-Wert bzw. ganze Zahl wird eingelesen--> deshalb s.next"Int"
		numberOne = s.nextInt();
		
		System.out.println("Geben Sie die zweite Zahl ein: ");//Int-Wert bzw. ganze Zahl wird eingelesen--> deshalb s.next"Int"
		numberTwo = s.nextInt();
		
		System.out.println("Geben Sie eine Gleitkommazahl ein:");//Gleitkommazahl wird eingelesen--> deshalb s.next"Double"
		numberThree = s.nextDouble();
		
		System.out.println("Geben Sie die zweite Gleitkommazahl ein:");//Gleitkommazahl wird eingelesen--> deshalb s.next"Double"
		numberFour = s.nextDouble();
		
		System.out.println("Die Division dieser Zahlen ergibt: "+(double)numberOne/(double)numberTwo);//die eingegebenen Nummern werden dividiert
		System.out.println("Die Multiplikation der Gleitkommazahlen ergibt: "+ (int)(numberThree*numberFour));//die eingegebenen Nummern werden multipliziert
		
		//10.2
		double x = (numberFour*numberThree);
		int y =  (int) (numberThree* numberFour);
		double z = x-y;
		
		System.out.println("Die Nachkommastelle des letzten Ergebnisses lautet:" +z) ;
		
		
		
	}

}

