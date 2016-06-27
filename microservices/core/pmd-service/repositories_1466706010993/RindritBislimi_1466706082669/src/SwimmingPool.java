
public class SwimmingPool extends Recipe {


	
	public SwimmingPool() {
		
		Miscellaneous cream = new Miscellaneous();
		cream.setName("Cream");
		cream.setPrice(3.5);
		cream.setWeight(0.5);
		
		DistilledBeverage vodka = new DistilledBeverage();
		vodka.setName("Vodka");
		vodka.setPrice(10.0);
		vodka.setWeight(1.0);
		
		DistilledBeverage blueCuracao = new DistilledBeverage();
		blueCuracao.setName("Blue Curacao");
		blueCuracao.setPrice(10.0);
		blueCuracao.setWeight(1.0);
		
		Miscellaneous kokosSirup = new Miscellaneous();
		kokosSirup.setName("Kokossirup");
		kokosSirup.setPrice(5.0);
		kokosSirup.setWeight(1.2);
		
		Juice ananasJuice = new Juice();
		ananasJuice.setName("Ananas-Juice");
		ananasJuice.setPrice(5.0);
		ananasJuice.setWeight(1.1);
		
		Fruit ananas = new Fruit();
		ananas.setName("Ananas");
		ananas.setPrice(3.0);
		ananas.setWeight(2.0);
		
		Fruit cherry = new Fruit();
		cherry.setName("Cherry");
		cherry.setPrice(1.0);
		cherry.setWeight(0.7);
		
		addIngredient(cream, 2);
		addIngredient(vodka, 4);
		addIngredient(blueCuracao, 2);
		addIngredient(kokosSirup, 4);
		addIngredient(ananasJuice, 12);
		addIngredient(ananas, 0.1);
		addIngredient(cherry, 1);
	}

}
