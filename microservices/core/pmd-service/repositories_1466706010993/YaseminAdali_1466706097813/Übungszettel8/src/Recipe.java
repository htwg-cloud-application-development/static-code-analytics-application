
public abstract class Recipe {
	private Ingredient[] myIngredients = new Ingredient[20];
	private double[] myAmount = new double[20];

	int ingredientsIndex = 0;

	void addIngredient(Ingredient ingredient, double amount) {

		myIngredients[ingredientsIndex] = ingredient;
		myAmount[ingredientsIndex] = amount;
		ingredientsIndex++;
	}

	void addIngredient(Ingredient ingredient, int amount) {

		addIngredient(ingredient, (double) amount);
	}

	double getTotalPrice() {
		double sum = 0.0;
		for (int i = 0; i < myIngredients.length; i++) {
			Ingredient myIng = myIngredients[i];

			sum += myIng.getPrice() * myAmount[i];
		}
		return sum;
	}

	 double getTotalWeight()

	{
		double sum = 0.0;
		for (int j = 0; j < myIngredients.length; j++) {
			Ingredient myIng = myIngredients[j];
			sum += myIng.getWeight() * myAmount[j];
		}
		return sum;
	}

	public boolean containsAlcohohl() {
		for (int i = 0; i < myIngredients.length; i++) {
			Ingredient currentIng = myIngredients[i];
			if (currentIng instanceof Alcohol) {
				return true;
			}
		}
		return false;
	}
}
