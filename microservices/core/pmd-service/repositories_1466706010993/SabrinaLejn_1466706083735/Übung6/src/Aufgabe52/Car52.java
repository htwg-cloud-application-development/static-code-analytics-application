package Aufgabe52;

public class Car52 {
			
		String Colour; 
		int MaxSpeed;
		String Manufacturer;
		
		public Car52(){						//Konstruktor
			this.Colour="Siber ";
			this.MaxSpeed=150;
			this.Manufacturer="Volkswagen";
		}
		

		public String Description(){
			return "Dieses "+ Manufacturer + " -Auto in " + Colour 
					+ " fährt bis zu "+ MaxSpeed + " km/h schnell."; 
		}
	}


