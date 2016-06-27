package Ü06;

public class Aufgabe46_Teil2 {

	public String colour;
	public int maxSpeed;
	public String manufacturer;
	
	public Aufgabe46_Teil2 () {
		this.colour = "silber";
		this.maxSpeed = 150;
		this.manufacturer = "Volkswagen";
	}
	
	public Aufgabe46_Teil2 (String acolour, int amaxSpeed, String amanufacturer) {
		this.colour = acolour;
		this.maxSpeed = amaxSpeed;
		this.manufacturer = amanufacturer;
	}

		public String description() {
			return "Der "  + manufacturer + " in " + colour  + " fährt " + ((Integer) maxSpeed).toString() + "." ;
		}

}
