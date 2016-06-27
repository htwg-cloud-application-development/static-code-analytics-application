package Uebungsblatt_3;
import java.util.Scanner;

public class Aufgabe_18 {

	public static void main(String[] args) {
	Scanner s = new Scanner (System.in);
	
	double packetWeight;
	System.out.println("Bitte geben sie ihr Packetgewicht ein");
	packetWeight = s.nextDouble();
	
	if (packetWeight<=2){
		System.out.println("Die Kosten betragen 4,99€");
		}
	else if (packetWeight<=5)
		System.out.println("Die Kosten betragen 5,99€");
	else if (packetWeight<=10)
		System.out.println("Die Kosten betragen 7,99€");
	else if (packetWeight<=31.5)
		System.out.println("Die Kosten betragen 13,99€");
	else if (packetWeight>31.5)
		System.out.println("Das Gewicht befindet sich über dem erlaubten Maximalgewicht");
	
		
	

	}

}
