package Aufgabe11;
import java.util.Scanner;
public class Aufgabe18 {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		
		System.out.print("Bitte geben Sie das Gewicht Ihres Packets an: ");
		double packageweight = s.nextFloat();
		
		
		
		if (packageweight<=2)
			System.out.println("Der Online-Preis beträgt 4,99€.(Max: 60x30x15cm)");
		
		else
		
		if (packageweight<=5)
			System.out.println("Der Filial-Preis beträgt 6,99€ und der Online Preis beträgt 5,99€.(Max: 120x60x60 cm)");
		
		else
		
		if (packageweight<=10)
			System.out.println("Der Filialpreis beträgt 8,99€ und der Online Preis beträgt 7,99€.(Max: 120x60x60 cm)");
		
		else
		
		if (packageweight<=31.5)
			System.out.println("Der Filialpreis beträgt 14,99€ und der Online Preis beträgt 13,99€.(Max: 120x60x60 cm)");
		
		else 

		if(packageweight>31.5)
			System.out.println("Das eingegebene Gewicht überschreitet das zulässige Maximalgewicht!");
	}

}

