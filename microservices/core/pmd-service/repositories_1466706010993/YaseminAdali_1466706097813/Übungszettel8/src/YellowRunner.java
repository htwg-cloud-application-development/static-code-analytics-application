
public class YellowRunner extends Recipe {

	private Ingredient myIngredients[] = new Ingredient[YellowRunnerE.values().length];
	private int counter = 0;

	public YellowRunner() {

		Ingredient juice = new Juice();
		juice.setName("Annanassaft");
		juice.setName(0.5);
		juice.setName("Grapefruitsaft");
		juice.setName(0.9);
		juice.setName("Zitronensaft");
		juice.setName(0.3);

		addIngredient(juice, 1);

		Sugar sugar = new Sugar();
		sugar.setName("Mandelsyrup");
		sugar.setName(1);

	}

	public void addIngredient(Ingredient juice, double amount) {
		juice.setWeight(amount);
		myIngredients[counter] = juice;
		counter++;
	}

	public Ingredient[] getIngredients() {

		return myIngredients;
	}

	@Override
	double getTotalPrice() {
		double sum = 0.0;
		for (int i = 0; i < myIngredients.length; i++) {
			Ingredient myIng = myIngredients[i];
			sum += myIng.getPrice();
		}
		return sum;
	}

	@Override
	double getTotalWeight() {
		double sum = 0.0;
		for (int i = 0; i < myIngredients.length; i++) {
			Ingredient myIng = myIngredients[i];
			sum += myIng.getWeight();
		}
		return sum;
	}

}

