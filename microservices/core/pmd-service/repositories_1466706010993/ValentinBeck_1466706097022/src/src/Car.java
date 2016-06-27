
public class Car {
	
	String color;
	int maxSpeed;
	String manufacturer;
	
	public Car(){
		String color = "silber";
		int maxSpeed = 150;
		String manufacturer = "vw";	
	}
	
	public String description(String color, int maxSpeed, String manufacturer){
		this.color = color;
		this.maxSpeed = maxSpeed;
		this.manufacturer = manufacturer;
		String result = "Dieses " + manufacturer + " -Auto in "+ color + " fährt " + maxSpeed +"schnell";
		return result;
	}

	
	
}
