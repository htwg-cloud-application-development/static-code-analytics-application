package Aufgabe56bis62;

public class DistilledBeverage implements Ingredient, Alcohol {


	String name;
	double price;
	double weight;

	DistilledBeverage(){
		this.name = "NoName";
		this.price = -1;
		this.weight = -1;
		
	}
	
	
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
