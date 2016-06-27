package Aufgabe56bis62;

public class Juice implements Ingredient {

	String name="NoName";
	double price=-1;
	double weight=-1;
	
	
	
	@Override
	public String getName(){
		return name;
		
	}
	@Override
	public void setName(String name){
		this.name= name;
	}
	@Override
	public double getPrice(){
		return price;
	}
	@Override
	public void setPrice(double price){
		this.price=price;
	}
	@Override
	public double getWeight(){
		return weight;
	}
	@Override
	public void setWeight(double weight){
		this.weight=weight;
		
	}
}
