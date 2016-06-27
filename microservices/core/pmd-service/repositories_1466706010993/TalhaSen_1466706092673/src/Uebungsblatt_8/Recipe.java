package Uebungsblatt_8;

public abstract class Recipe {
	Ingredient[] Rezeptliste = new Ingredient[30];
	double[] Menge = new double[30];
	int i = 0;

	void addIngredient(Ingredient Zutat, double amount) {
		Rezeptliste[i] = Zutat;
		Menge[i] = amount;
		i++;
	}

	void addIngredient(Ingredient Zutat, int amount) {
		Rezeptliste[i] = Zutat;
		Menge[i] = amount;
		i++;
	}

	double getTotalPrice() {
		double preis = 0;
		for (int i = 0; i < Rezeptliste.length; i++) {
			preis = preis + (Menge[i] + Rezeptliste[i].getPrice());
		}
		return preis;
	}

	double getTotalWeight() {
		double weight = 0;
		for (int i = 0; i < Rezeptliste.length; i++) {
			weight = weight + (Menge[i] + Rezeptliste[i].getWeight());
		}
		return weight;
	}
}
