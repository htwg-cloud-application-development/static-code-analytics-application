
public class SwimmingPool extends Recipe {
	
	private Ingredient myIngredients[] = new Ingredient[SwimmingPoolE.values().length];
	private int counter = 0;

	public SwimmingPool() {
		
		Miscellaneous misc = new Miscellaneous();
		misc.setName("Wodka");
		misc.setName(1.0);
		
		Juice juice = new Juice();
		juice.setName("Annanassaft");
		juice.setName(0.5);
		
		Sugar sugar = new Sugar();
		sugar.setName(2.2);
		sugar.setName("Kokossyrup");
		
		addIngredient(misc, 1);
		addIngredient(sugar, 500);
		addIngredient(juice, 1);

	}
	
	public void addIngredient(Ingredient misc, double amount){
		misc.setWeight(amount);
		myIngredients[counter] = (Ingredient) misc;
		counter++;
	}
	
	public Ingredient[] getIngredients(){
		return myIngredients;
	}

	@Override
	double getTotalPrice() {
		double sum = 0.0;
		for(int i = 0; i<myIngredients.length; i++){
			Ingredient myIng = myIngredients[i];
			sum += myIng.getPrice();
		}
		return sum;
	}

	@Override
	double getTotalWeight() {
		double sum = 0.0;
		for(int i = 0; i<myIngredients.length; i++){
			Ingredient myIng = myIngredients[i];
			sum += myIng.getWeight();
					
		}
		return sum;
	}

}

