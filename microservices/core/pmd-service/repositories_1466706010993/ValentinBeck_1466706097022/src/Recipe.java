import java.util.*;
import java.util.Scanner;
public abstract class Recipe {
	
	Ingredients ingredient;
	ArrayList<Ingredients> rezept = new ArrayList<Ingredients>();
	double sumP = 0;
	double sumW = 0;
	boolean containsAlcohol = false;

	public Ingredients addIngredients(Ingredients ingredient, double amount){
		ingredient.setWeight(amount);
		rezept.add(ingredient);
		return ingredient;
	}

	public Ingredients addIngredients(Ingredients ingredient, int amount){
		ingredient.setWeight(amount);
		rezept.add(ingredient);
		return ingredient;
	}
	
	public double getTotalPrice(){
		for(int i=0; i<rezept.size();i++ ){
			sumP = sumP + (double)ingredient.getPrice();
		}
		return sumP;
	}
	
	public double getTotalWeight(){
		for(int i=0; i<rezept.size();i++ ){
			sumW = sumW + (double)ingredient.getWeight();
		}
		return sumW;
	}
	
	public boolean containsAlcohol() {
		for(int i = 0; i<rezept.size(); i++){
			if(rezept.get(i) instanceof Alcohol){
				containsAlcohol = true;
			}
		}
		return containsAlcohol;
	}
	
	//EOF
}
