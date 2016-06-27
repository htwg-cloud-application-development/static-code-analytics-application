public abstract class Recipe {

	Ingredients[] Zutaten = new Ingredients[5];
	double[] Menge = new double[5];
	int i = 0;

	public void addIngredient(Ingredients Zutat, double amount) {

		Zutaten[i] = Zutat;
		Menge[i] = amount;
		i++;
	}

	public void addIngredient(Ingredients Zutat, int amount) {
		addIngredient(Zutat, (double) amount);
	}

	public int getTotalPrice() {

		return 0;
	}

	public int getTotalWeight() {

		return 0;
	}

	public boolean containsAlcohol(Ingredients[] ing) {
		for (int i = 0; i < ing.length; i++) {
			Ingredients currentIng = ing[i];
			if (currentIng instanceof Alcohol) {
				return true;
			}
		}

		// }
		return true;
	}

}

