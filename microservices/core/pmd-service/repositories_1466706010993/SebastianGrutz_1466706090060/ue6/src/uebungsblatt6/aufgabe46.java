package uebungsblatt6;

public class aufgabe46 {

	public static void main(String[] args) {
		
		// Objekt-Instanzierung
		Car bmw = new Car();
		
		//Attribut-Zuwei§ung
		bmw.colour = "blau";
		bmw.maxSpeed = 300.5;
		bmw.manufactorer = "bmw";
		
		System.out.println(bmw.description());
		
	}
}
