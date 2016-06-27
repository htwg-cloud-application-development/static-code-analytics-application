
public class Caipirinha extends Recipe {


	public Caipirinha() {
		String name = "Caipirinha";
		
		DistilledBeverage cachaca = new DistilledBeverage();
		cachaca.setName("Cachaca");
		cachaca.setPrice(10.0);
		cachaca.setWeight(1.0);

		Sugar brownSugar = new Sugar();
		brownSugar.setName("Sugar brown");
		brownSugar.setPrice(10.0);
		brownSugar.setWeight(1.0);

		Fruit lime = new Fruit();
		lime.setName("Lime");
		lime.setPrice(10.0);
		lime.setWeight(5.0);

		Miscellaneous ice = new Miscellaneous();
		ice.setName("Ice crushed");
		ice.setPrice(0.5);
		ice.setWeight(1.0);

		addIngredient(cachaca, 6);
		addIngredient(brownSugar, 2);
		addIngredient(lime, 0.5);
		addIngredient(ice, 3);
	}

}
