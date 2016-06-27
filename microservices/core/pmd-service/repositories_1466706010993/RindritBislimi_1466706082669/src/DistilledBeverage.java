
public class DistilledBeverage implements Ingredient {

	private String name;
	private double price, weight;
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;

	}

	@Override
	public Double getPrice() {
		return price;
	}

	@Override
	public void setPrice(Double price) {
		this.price = price;

	}

	@Override
	public Double getWeight() {
		return weight;
	}

	@Override
	public void setWeight(Double weight) {
		this.weight = weight;
	}

}
