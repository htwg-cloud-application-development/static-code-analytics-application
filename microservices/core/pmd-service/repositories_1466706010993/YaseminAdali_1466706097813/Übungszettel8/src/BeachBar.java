
public class BeachBar {

	private String[][] Cocktails;

	public BeachBar(){

		Ingredient[] caipis = new Ingredient[Caipirinha.values().length];
		
		Ingredient rum = new DistilledBeverage();
		caipis[Caipirinha.Rum.value] = rum; 
		Sugar sugar = new Sugar();
		caipis[Caipirinha.Rohrzucker.value] = sugar;
		Ingredient limete = new Fruit();
		caipis[Caipirinha.Limetten.value] = limete;
		
	 	Ingredient [] yellowrunner = new Ingredient[YellowRunnerE.values().length];
		
		Juice asaft = new Juice();
		yellowrunner[YellowRunnerE.Annanassaft.value] = asaft;
		Ingredient zsaft = new Juice();
		yellowrunner[YellowRunnerE.Zitronensaft.value]=zsaft;
		Ingredient gsaft = new Juice();
		yellowrunner[YellowRunnerE.Grapefruitsaft.value]= gsaft;
		Ingredient mandelsyrup = new Sugar();
		yellowrunner[YellowRunnerE.Mandelsyrup.value]= mandelsyrup;
		
		
		Ingredient[] Swimmingpool = new Ingredient[SwimmingPoolE.values().length];
		
		Ingredient ansaft= new Juice();
		Swimmingpool[SwimmingPoolE.Annanassaft.value]= ansaft;
		Ingredient wodka = new DistilledBeverage();
		Swimmingpool[SwimmingPoolE.Wodka.value]= wodka;
		Ingredient koko = new Sugar();
		Swimmingpool[SwimmingPoolE.Kokossyrup.value]= koko;
		Ingredient blue = new DistilledBeverage();
		Swimmingpool [SwimmingPoolE.BlueCuracao.value]= blue;
		
		
		
		
		
	}

	public String[][] getCocktails() {
		return Cocktails;
	}

	public void setCocktails(String[][] cocktails) {
		Cocktails = cocktails;
	}
	
}
