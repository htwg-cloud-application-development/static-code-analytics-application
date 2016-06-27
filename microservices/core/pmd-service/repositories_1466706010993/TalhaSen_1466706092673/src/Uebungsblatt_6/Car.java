package Uebungsblatt_6;

public class Car {

	public String colour; // Attribute für Farbe 
	
	public int MaxSpeed; // Attribute für Maximalgeschwindigkeit
	
	public String Manufacturer; // Attribute für Hersteller	
	String Description(){ //Instanzmethode Description mit dem Rückabewert String
		String car="Dieses Auto der Marke " +this.Manufacturer+" in " +this.colour+ " fährt bis zu " +this.MaxSpeed+ " km/h schnell";
		return car;
	}
	
}
