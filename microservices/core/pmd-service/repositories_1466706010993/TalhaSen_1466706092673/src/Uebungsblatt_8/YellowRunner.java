package Uebungsblatt_8;

public class YellowRunner extends Recipe{
	public YellowRunner(){
		Juice saft = new Juice();
		Miscellaneous sirup = new Miscellaneous();
		
		saft.setName("Zitronensaft, Ananassaft und Grapefruitsaft");
		saft.setWeight(19);
		saft.setPrice(4);
		addIngredient(saft,19);
		
		sirup.setName("Mandelsirup");
		sirup.setWeight(1);
		sirup.setPrice(0.5);
		addIngredient(sirup,1);
		
		
	}

}
