package Übungsblatt8;


public class DistilledBeverage implements Ingredient, Alcohol { // Alkohol Klasse welche die Zutaten implementiert

	public String name;
	public double priceAlcohol;
	public double weightAlcohol;

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public double getPrice() {
		return this.priceAlcohol;
	}

	@Override
	public void setPrice(double price) {
		this.priceAlcohol = price;
	}

	@Override
	public double getWeight() {
		return this.weightAlcohol;
	}

	@Override
	public void setWeight(double weight) {
		this.weightAlcohol = weight;
	}

	@Override
	public void containsAlcohol() { // wurde vererbt
	}

}
