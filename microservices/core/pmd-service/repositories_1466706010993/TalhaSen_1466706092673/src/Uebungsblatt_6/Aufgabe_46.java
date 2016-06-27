package Uebungsblatt_6;

public class Aufgabe_46 {

	public static void main(String[] args) {
		
		Car aCar = new Car(); // Verweis auf Datei CAR.
		aCar.colour="rot";
		aCar.Manufacturer="Audi";
		aCar.MaxSpeed=280;
		System.out.println(aCar.Description()); // Ausgabe des Textes aus CAR.

	}

}
