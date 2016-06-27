
public class YellowRunner extends Recipe {

	public YellowRunner() {


		Miscellaneous mandelSirup = new Miscellaneous();
		mandelSirup.setName("Mandel-Sirup");
		mandelSirup.setPrice(10.0);
		mandelSirup.setWeight(1.0);
		
		Juice zitronenSaft = new Juice();
		zitronenSaft.setName("Zitronensaft");
		zitronenSaft.setPrice(10.0);
		zitronenSaft.setWeight(1.0);
		
		Juice ananasSaft = new Juice();
		ananasSaft.setName("Ananas-Saft");
		ananasSaft.setPrice(10.0);
		ananasSaft.setWeight(1.1);
		
		Juice grapeFruitJuice = new Juice();
		grapeFruitJuice.setName("Grapefruit-Saft");
		grapeFruitJuice.setPrice(10.0);
		grapeFruitJuice.setWeight(1.1);

		addIngredient(mandelSirup, 1);
		addIngredient(zitronenSaft, 1);
		addIngredient(ananasSaft, 10);
		addIngredient(grapeFruitJuice, 8);

	}
}
