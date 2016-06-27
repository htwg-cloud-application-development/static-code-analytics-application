
public abstract class Caipi extends Recipe implements Ingredient {

	public Caipi() {

		Miscellaneous misc = new Miscellaneous();
		misc.setName("Rum");
		misc.setName(1.0);

		Sugar sugar = new Sugar();
		sugar.setName(2.2);
		sugar.setName("Rohrzucker");
		sugar.setWeight(0.8);

		Fruit fruit = new Fruit();
		fruit.setName("Limette");
		fruit.setName(1);
		fruit.setWeight(0.6);

		addIngredient(misc, 1);
		addIngredient(sugar, 0.8);

	}


}
