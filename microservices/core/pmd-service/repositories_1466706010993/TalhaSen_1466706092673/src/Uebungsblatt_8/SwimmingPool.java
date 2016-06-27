package Uebungsblatt_8;

public class SwimmingPool extends Recipe{
	public SwimmingPool(){
		DistilledBeverage alk = new DistilledBeverage();
		Juice saft = new Juice();
		Miscellaneous cream = new Miscellaneous();
		
		alk.setName("Weißer Rum, Wodka und Blue Curacao");
		alk.setWeight(7);
		alk.setPrice(3);
		addIngredient(alk,7);
		
		saft.setName("Ananassaft");
		saft.setWeight(6);
		saft.setPrice(1);
		addIngredient(saft,6);
		
		cream.setName("Kokosnusscreme, Sahne und Eiswürfel");
		cream.setWeight(7);
		cream.setPrice(3);
		addIngredient(cream,7);
		
		
	}
}
