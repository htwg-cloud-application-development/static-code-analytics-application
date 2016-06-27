package Übungsblatt8;


	public abstract class Recipe {
		
		Ingredient[] Zutatenliste = new Ingredient[4];
		double[] mengedouble = new double[4];
		int a = 0;

		public void addIngredient(Ingredient zutaten, double amount) {
			Zutatenliste[a] = zutaten;
			mengedouble[a] = amount;
			a++;
		}

		public double getTotalPrice() {
			double preis = 0;
			for (int i = 0; i < Zutatenliste.length; i++) {
				preis = preis + (mengedouble[i] * Zutatenliste[i].getPrice());
			}
			return preis;
		}

		public double getTotalWeight() {
			double weight = 0;
			for (int i = 0; i < Zutatenliste.length; i++) {
				weight = weight + (mengedouble[i] * Zutatenliste[i].getWeight());
			}
			return weight;
		}

		public boolean containsAlcohol() {

			boolean alcohol = false;
			for (int j = 0; j < Zutatenliste.length; j++) {

				if (Zutatenliste[j] instanceof Alcohol) {
					alcohol = true;
				}
			}
		return alcohol;
		}

	}