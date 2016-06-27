package Übungsblatt8;

public class Caipirinha extends Recipe {

	public Caipirinha() {
	DistilledBeverage rum = new DistilledBeverage();
	Fruit limetten = new Fruit();
	Sugar zucker = new Sugar();
	Miscellaneous eis = new Miscellaneous();
	
	rum.setName("Rum");
	rum.setPrice(10);
	rum.setWeight(1);
	
	limetten.setName("Limetten");
	limetten.setPrice(2);
	limetten.setWeight(1);
	
	zucker.setName("Brauner Zucker");
	zucker.setPrice(3);
	zucker.setWeight(1);
	
	eis.setName("Eiswürfel");
	eis.setPrice(2);
	eis.setWeight(1);
	
	addIngredient(rum, 0.2);
	addIngredient(limetten,0.2 );
	addIngredient(zucker, 0.1);
	addIngredient(eis, 0.2);
	
	System.out.println("----Caipirinha----");
	System.out.println("Der Preis ist: \t\t" +getTotalPrice());
	System.out.println("Das Gewicht ist:\t" +getTotalWeight());
	System.out.println("Enthält Alkohol:\t" + containsAlcohol());
	}
	
}
