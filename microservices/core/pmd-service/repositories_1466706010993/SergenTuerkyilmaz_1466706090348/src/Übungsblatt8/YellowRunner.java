package Übungsblatt8;

public class YellowRunner extends Recipe {
	
	public YellowRunner() {
		Juice annanassaft =  new Juice();
		Juice grapefruitsaft =  new Juice();
		Juice zitronensaft = new Juice();
		Miscellaneous mandelsitup = new Miscellaneous();
		
		annanassaft.setName("Annanassaft");
		annanassaft.setPrice(2);
		annanassaft.setWeight(1);
		
		grapefruitsaft.setName("Grapefruitsaft");
		grapefruitsaft.setPrice(2.5);
		grapefruitsaft.setWeight(1);
		
		zitronensaft.setName("Zitronensaft");
		zitronensaft.setPrice(6);
		zitronensaft.setWeight(1);
		
		mandelsitup.setName("mandelsirup");
		mandelsitup.setPrice(4);
		mandelsitup.setWeight(1);
		
		addIngredient(annanassaft, 0.1);
		addIngredient(grapefruitsaft,0.08 );
		addIngredient(zitronensaft, 0.01);
		addIngredient(mandelsitup, 0.01);
		
		System.out.println("\n----yRunner----");
		System.out.println("Der Preis ist: \t\t" +getTotalPrice());
		System.out.println("Das Gewicht ist:\t" +getTotalWeight());
		System.out.println("Enthält Alkohol:\t" + containsAlcohol());
	}
	
//	10	cl	Ananassaft
//	8	cl	Grapefruitsaft
//	1	cl	Mandelsirup
//	1	cl	Zitronensaft (frisch)
}
