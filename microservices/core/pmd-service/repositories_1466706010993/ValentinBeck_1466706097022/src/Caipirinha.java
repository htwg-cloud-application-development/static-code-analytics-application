
public class Caipirinha extends Recipe {

	public Caipirinha(){
		
		Ingredients lime = new Fruit();
		lime.setWeight(100);
		lime.setPrice(0.5);
		addIngredients(lime, 1);
		
		Ingredients vodka = new Distilledbeverage();
		vodka.setWeight(0.4);
		vodka.setPrice(2);
		addIngredients(vodka, 2.0);
		
		Ingredients braunerzucker = new Sugar();
		braunerzucker.setWeight(0.5);
		braunerzucker.setPrice(0.5);
		addIngredients(braunerzucker, 0.5);
		
		
		
		
	}
}
