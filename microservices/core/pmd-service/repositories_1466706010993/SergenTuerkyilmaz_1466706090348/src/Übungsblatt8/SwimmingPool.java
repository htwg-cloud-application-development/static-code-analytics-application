package Übungsblatt8;

public class SwimmingPool extends Recipe{
	
	public SwimmingPool() {
		
		DistilledBeverage rum = new DistilledBeverage();
		DistilledBeverage wodka = new DistilledBeverage();
		Miscellaneous blueCuracao = new Miscellaneous();
		Miscellaneous kokosnusscreme = new Miscellaneous();
	
		
		rum.setName("weißer Rum");
		rum.setPrice(10);
		rum.setWeight(1);
		wodka.setName("Wodka");
		wodka.setPrice(10);
		wodka.setWeight(1);
		
		
		blueCuracao.setName("blauer Sirup");
		blueCuracao.setPrice(2);
		blueCuracao.setWeight(2);
		
		
		kokosnusscreme.setName("Kokosnusscreme");
		kokosnusscreme.setPrice(2);
		kokosnusscreme.setWeight(2);
		
		
		
		addIngredient(rum, 0.2);
		addIngredient(wodka, 0.2);
		addIngredient(blueCuracao, 0.1);
		addIngredient(kokosnusscreme, 0.2);
		
		
		System.out.println("\n----SwimmingPool----");
		System.out.println("Der Preis ist: \t\t" +getTotalPrice());
		System.out.println("Das Gewicht ist:\t" +getTotalWeight());
		System.out.println("Enthält Alkohol:\t" + containsAlcohol());
		}
	
}
