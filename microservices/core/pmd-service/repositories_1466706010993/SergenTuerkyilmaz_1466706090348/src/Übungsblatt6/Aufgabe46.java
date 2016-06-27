package Übungsblatt6;

public class Aufgabe46 {

	public static void main(String[] args) {

		Car auto = new Car("blau",310,"3er-BMW");
		
		auto.colour ="blau";
		auto.manufacturer = "3er-BMW";
		auto.maxSpeed = 310;
		
		System.out.println(auto.Description());
		
	}
}
