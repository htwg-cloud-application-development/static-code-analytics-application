import java.util.ArrayList;

public abstract class Recipe {

	ArrayList<Ingredient> list = new ArrayList<Ingredient>();
	ArrayList<Number> listAmount = new ArrayList<Number>();

	public void addIngredient(Ingredient in, double amount) {
		int tempPos;
		double tempAmount;
		if (!list.contains(in)) {
			list.add(in);
			listAmount.add(amount);
		} else {
			tempPos = list.indexOf(in);
			System.out.println("temp: " + tempPos);
			tempAmount = listAmount.get(tempPos).doubleValue() + amount;
			listAmount.set(tempPos, tempAmount);
			System.out.println("DEBUG: Zutat gab es schon, Menge erhöht");
		}
	}
}
