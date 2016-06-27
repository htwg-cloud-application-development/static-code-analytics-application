package Aufgabe56bis62;

public abstract class Recipe {

	Object[][] ingredientsAmount = new Object[7][2];  	//Liste mit 7 Splten je Zutat und Menge
		//Object ist DIE Oberklasse, alle Objekte sind Kinder von Object
		//es gibt keine Superklasse/Oberklasse von Object
	
	
	public void addIngredient(Ingredient ingredient, double amount){
	
		for (int i=0; i < ingredientsAmount.length; i++){
			if (ingredientsAmount[i][0] == null){
				ingredientsAmount[i][0]= ingredient;
				ingredientsAmount[i][1]= amount;
				break;
			}
		}	
	}
	
	public void addIngredient(Ingredient i, int amount){
		double d = (double) amount;				
		addIngredient(i, d);
	}
	
	public double getTotalPrice(){
		
		double price=0;				//default: Preis ist null 
		
		for (int i=0; i < ingredientsAmount.length; i++){
			if (ingredientsAmount[i] != null){
				Ingredient zutat;
				zutat= (Ingredient) ingredientsAmount[i][0];
						// gecastet von Object nach Ingredient // "ich bin mir sicher, da sind nur Ingrediants drin
				double menge;
				menge= (double) ingredientsAmount[i][1];
				
				price += zutat.getPrice() * menge;
				
			}				
		}		
		return price;
	}
	
	public double getTotalWeight(){
		double weight=0;
		
		for (int i=0; i < ingredientsAmount.length; i++){
			if (ingredientsAmount[i] != null){
				Ingredient zutat;
				zutat = (Ingredient) ingredientsAmount[i][0];
						// gecastet von Object nach Ingredient // "ich bin mir sicher, da sind nur Ingrediants drin
				double menge;
				menge = (double) ingredientsAmount[i][1];
				
				weight += zutat.getWeight() * menge;
					}
			}
		
		return weight;		
	}
	
	public boolean containtsAlcohol(){
		for (int i=0; i < ingredientsAmount.length; i++){
			if(ingredientsAmount[i] != null){
				Ingredient zutat;
				zutat = (Ingredient) ingredientsAmount[i][0];		//casten: an der Stelle 0 sind die Zutaten
																	// an der stelle 0 sind die Zutaten, an 1 die Menge
				if(zutat instanceof Alcohol){						// zutat ein Teil von Alkohol?
					return true;
				}
			
				
//				Class[] klassen = zutat.getClass().getInterfaces();
//				for (Class c : klassen){
//					if(c.equals(Alcohol.class)){		//hat irgendeine Zutat eine Superklasse, die Alkohol ist
//						return true;
//					}
//				}	
			}									
		}// Interface zählt auch als  Superklasse
		return false;
	}
		
	//ich hab ein Object Zutat, daraus hole ich mir die Klasse; aus der Klasse dann die Superklasse
	// und die vergleiche ich mit der Klasse von Alcohol
	
	
}
