package Aufgabe11;
import java.util.Scanner;
public class Aufgabe17 {

	public static void main(String[] args) {
		
		Scanner s = new Scanner(System.in);
		
		System.out.print("Geben Sie Ihr Gewicht ein: ");
		
		float weight = s.nextFloat();					//Fließkommazahl wird eingelesen und in "weight" gespeichert
		
		System.out.print("Geben Sie Ihre Größe ein: ");//Fließkommazahl wird eingelesen und in "height" gespeichert
		float height = s.nextFloat();
	
		
		
		float BMI = weight / (height*height); // BMI-Formel wird berechnet
		
		
		System.out.println("Ihr BMI-Wert lautet: " + BMI); // Ausgabe des BMI-Wertes.
		
		if (BMI < 18.5)										//mit Hilfe von Else-Schleifen die Printline ausführen!
			System.out.println("Sie haben Untergewicht.");
		
		else 
			
		if (BMI > 18.5 && BMI < 25)
		System.out.println("Sie haben Normalgewicht.");
		
		
		else
			
		if (BMI>25 && BMI <31)
			System.out.println("Sie haben Übergewicht.");
		
		else
		
		if (BMI>30 && BMI <41)
			System.out.println("Sie haben starkes Übergewicht.(Adipositas)");
		
		else
		
		if (BMI>40)
		System.out.println("Sie haben extreme Adipositas.");
		
		}
	}

	
	
	

	
	

