package Übungsblatt6;

public class Car {

	String colour;
	double maxSpeed;
	String manufacturer;

	public Car(String c, double mS, String m) {

		colour= c;
		maxSpeed = mS;
		manufacturer = m;
	}

	
	String Description() {

		String auto = "Dieser " + this.manufacturer + " in " + this.colour + " fährt bis zu " + this.maxSpeed
				+ " km/h schnell";
		return auto;

	}
}