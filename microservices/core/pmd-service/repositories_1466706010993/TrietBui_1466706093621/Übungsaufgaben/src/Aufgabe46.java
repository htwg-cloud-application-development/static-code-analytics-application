public class Aufgabe46 {

public static void main(String[] args) {
}
public class car{
	String colour;
	int maxSpeed;
	String manufacturer;

	public void setCar (){
		this.colour = "mattschwarz";
		this.maxSpeed = 320;
		this.manufacturer = "Ferrari";
	}
	public String getCar(){
		return "Dieses" + manufacturer + "-Auto in" + colour + "fährt bis zu" + maxSpeed + "km/h schnell";
	}
}
}