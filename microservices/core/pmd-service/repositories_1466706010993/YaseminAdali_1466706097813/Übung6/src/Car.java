//Aufgabe 46 + 50
public class Car {

	
	String Colour;
	int MaxSpeed;
	String Manufacturer;
	int AnzReifen;
	
	public Car(){
		this.Colour = "silber";
		this.MaxSpeed = 150;
		this.Manufacturer = "Volkswagen";	
	}
	
	public String Description() {

		return " Dieses " + Manufacturer + " Auto in " + Colour + " fährt bis zu " + MaxSpeed + "km/h schnell.";

	}
	
}
