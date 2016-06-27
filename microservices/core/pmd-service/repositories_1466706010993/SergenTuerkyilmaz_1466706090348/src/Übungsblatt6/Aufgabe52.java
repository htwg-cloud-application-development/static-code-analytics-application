package Übungsblatt6;

public class Aufgabe52 {

	public static void main(String[] args) {

		Car auto = new Car("silber", 150, "Volkswagen");

		System.out.println("Dieser " + auto.manufacturer + " in " + auto.colour + " fährt bis zu " + auto.maxSpeed
				+ " km/h schnell");

	}
}
