package Übungsblatt6;

public class Car {

	static String Colour;
	static int MaxSpeed;
	static String Manufacturer;

	// public Car(String Colour, int MaxSpeed, String Manufacturer){
	//
	// this.Colour = Colour;
	// this.MaxSpeed = MaxSpeed;
	// this.Manufacturer = Manufacturer;
	// }
	
	public Car(){ 			//Default-Konstruktor
		Colour = "silber";	//-->mehrere Konstruktoren in einer Klasse 
		MaxSpeed = 150;		// == überladene Konstruktoren
		Manufacturer = "Volkswagen";
	}

	public String Description() {

	//	this.Colour = Colour; 			// this.Instanzvariablennname = Parametername
	//this.MaxSpeed = MaxSpeed;
	//	this.Manufacturer = Manufacturer;

		return "Dieses " + this.Manufacturer + "-Auto in " + this.Colour + " fährt bis zu " + this.MaxSpeed
				+ " km/h schnell";

	}

}
