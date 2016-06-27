package Übungsblatt6;

public class Aufgabe_46 {

	public static void main(String[] args) {

		// Car myCar = new Car("schwarz", 240, "Audi"); //über Konstruktor Werte festlegen

		Car myCar = new Car();	//Objekt erzeugen

//		System.out.println(myCar.Description("schwarz", 240, "Audi"));
//		System.out.println(myCar.Description("rot", 350, "Ferari"));
		
		myCar.Colour = "schwarz"; //einzeln über das Objekt Variable bestimmen
		myCar.MaxSpeed = 240;
		myCar.Manufacturer = "Audi";
		
		System.out.println(myCar.Description()); //Ausgabe über Instanzmethdoe
		
		Car myCar1 = new Car();
		
		myCar1.Colour = "rot";
		myCar1.MaxSpeed = 350;
		myCar1.Manufacturer = "Ferrari";
		
		System.out.println(myCar1.Description());

		// Car myCar1 = new Car("rot", 350, "Ferrari");
		// System.out.println(myCar1.Description());

	}

}
