package uebungsblatt6;

public class Car {

 public String colour;
 public double maxSpeed; 
 public String manufactorer;

 String  description() {
	 return "Dieses" + this.manufactorer + "Auto in" + this.colour + "fährt bis zu" + this.maxSpeed + "km/h schnell";
 	}
}
