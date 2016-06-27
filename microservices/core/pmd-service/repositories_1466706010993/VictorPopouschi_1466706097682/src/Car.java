

public class Car extends Aufgabe46 {
public String colour;
public int maxSpeed;
public String manufacturer;
//Aufgabe 52
public Car(){
this.colour="silber";
this.maxSpeed=150;
this.manufacturer="VW";

}
//Aufgabe 46
public String Description(String col,int max,String man){
	this.colour=col;
	this.manufacturer=man;
	this.maxSpeed=max;
	String satz="Deises "+manufacturer+" in der Farbe "+colour+" fährt bis zu "+maxSpeed+" km/h";
	return satz;
}
public String Description(){
	String satz="Deises "+manufacturer+" in der Farbe "+colour+" fährt bis zu "+maxSpeed+" km/h";
	return satz;
}
}
