
public class DistilledBeverage implements Ingredient,Alcohol{
	String name;
	int weight;
	double weight1;
	double price;

	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public double getWeight() {
		
		return weight1;
	}

	@Override
	public void setWeight(double weight) {
		this.weight1= weight;;

	}

	@Override
	public double getPrice() {
		return price;
	}

	@Override
	public void setpPrice(double price) {
		this.price = price;
		

	}

	@Override
	public void setName(double d) {
		// TODO Auto-generated method stub
		
	}
}
