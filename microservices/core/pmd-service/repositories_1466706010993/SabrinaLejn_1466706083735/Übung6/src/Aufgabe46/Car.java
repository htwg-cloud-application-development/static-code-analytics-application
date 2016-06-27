package Aufgabe46;

public class Car {
	
	String Colour; 
	int MaxSpeed;
	String Manufacturer;
	
/*	public String Description(int MaxSpeed, String Manufacturer, String Colour){ 
		this.Manufacturer=Manufacturer;
		this.MaxSpeed= MaxSpeed;
		this.Colour=Colour;
		return "Dieses "+ Manufacturer + " -Auto in " + Colour 
				+ " fährt bis zu "+ MaxSpeed + " km/h schnell."; 
		}*/

	public String Description(){
		return "Dieses "+ Manufacturer + " -Auto in " + Colour 
				+ " fährt bis zu "+ MaxSpeed + " km/h schnell."; 
	}
}
