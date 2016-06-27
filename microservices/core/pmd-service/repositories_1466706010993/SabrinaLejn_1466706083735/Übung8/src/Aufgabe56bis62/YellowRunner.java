package Aufgabe56bis62;

public class YellowRunner extends Recipe {
	
	/*Constructor*/
	public YellowRunner(){
		
		Miscalellaneous grapefruitsaft = new Miscalellaneous();
		Miscalellaneous ananassaft = new Miscalellaneous();
		Miscalellaneous zitronensaft = new Miscalellaneous();
		Miscalellaneous mandelsirup = new Miscalellaneous();
		
		
		grapefruitsaft.setName("Grapefruitsaft");
		grapefruitsaft.setWeight(2);
		
		
		addIngredient(grapefruitsaft, 8);
		addIngredient(ananassaft, 10);
		addIngredient(zitronensaft, 1);
		addIngredient(mandelsirup, 1);
		
		
	}
	
}
