package Uebungsblatt_3;
import java.util.Scanner;

public class Aufgabe_17 {

	public static void main(String[] args) {
		Scanner s = new Scanner (System.in);
		
		float bodyWeight; //Körpergewicht
		float bodyLenght; //Körpergröße
		float bmi; //Body-Maß-Index
		
	System.out.println("Geben sie Ihre Größe ein");
	bodyLenght = s.nextFloat();
	System.out.println("Geben sie ihr Gewicht ein");
	bodyWeight = s.nextFloat();
	
	bmi = bodyWeight/(bodyLenght*bodyLenght);
	System.out.println("Dein BMI ist:"+bmi);
	
	if(bmi<18.5){
		System.out.println("Sie haben Untergewicht");
		}
	else if (bmi<25)
		System.out.println("Sie haben Normalgewicht");
	else if (bmi<30)
		System.out.println("Sie haben Übergewicht");
	else if (bmi<40)
		System.out.println("Sie haben starkes Übergewicht");
	else if (bmi>40)
		System.out.println("Sie haben extreme Adipositas");
	
		
	}
	

}
