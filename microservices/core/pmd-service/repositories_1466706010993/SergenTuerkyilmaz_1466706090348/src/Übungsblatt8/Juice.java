package Übungsblatt8;

public class Juice implements Ingredient { //Juice implementiert die methoden von "Zutaten"
	
		String name;
		double price;
		double weight;

		@Override
		public String getName() {
			return this.name;

		}

		@Override
		public void setName(String X) {
			this.name = X;

		}

		@Override
		public double getPrice() {
			return this.price;
		}

		@Override
		public void setPrice(double a) {
			this.price = a;
		}

		@Override
		public double getWeight() {
			return this.weight;
		}

		@Override
		public void setWeight(double b) {
			this.weight = b;
		}

	}

