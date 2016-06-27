package Uebungsblatt_8;

public class Caipirinha extends Recipe {
	public Caipirinha(){
		DistilledBeverage cacacha = new DistilledBeverage();
		Fruit limes = new Fruit();
		Sugar braunerZucker = new Sugar();
		Miscellaneous ice = new Miscellaneous();
		
		cacacha.setName("Cacacha");
		cacacha.setPrice(3);
		cacacha.setWeight(6);
		addIngredient(cacacha,6);
		
		limes.setName("Limetten");
		limes.setPrice(1);
		limes.setWeight(0.5);
		addIngredient(limes,1);
		
		braunerZucker.setName("brauner Zucker");
		braunerZucker.setPrice(0.5);
		braunerZucker.setWeight(2);
		addIngredient(braunerZucker,0.5);
		
		ice.setName("Eiswürfel");
		ice.setPrice(0.2);
		ice.setWeight(0.25);
		addIngredient(ice,0.25);
	
	}
	
}
