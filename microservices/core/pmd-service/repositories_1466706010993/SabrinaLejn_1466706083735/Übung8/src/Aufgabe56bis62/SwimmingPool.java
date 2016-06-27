package Aufgabe56bis62;

public class SwimmingPool extends Recipe {

	SwimmingPool(){
	
	DistilledBeverage wodka = new DistilledBeverage();
	DistilledBeverage weiﬂerRum = new DistilledBeverage();
	DistilledBeverage blueCuracao = new DistilledBeverage();
	Miscalellaneous kokosnusscreme = new Miscalellaneous();
	Miscalellaneous ananassaft = new Miscalellaneous();
	Miscalellaneous sahne = new Miscalellaneous();
	Miscalellaneous crushedice = new Miscalellaneous();
	
	wodka.setName("Wodka");
	wodka.setWeight(2);
	
	
	addIngredient(wodka, 2);
	addIngredient(weiﬂerRum, 4);
	addIngredient(crushedice, 1);
	addIngredient(blueCuracao, 1);
	addIngredient(kokosnusscreme, 2);
	addIngredient(ananassaft, 6);
	addIngredient(sahne, 1);
	
}
}