package Aufgabe56bis62;

public class BeachBar {
	
	private Recipe [] coctailArray;
	
	BeachBar(){
		
		int length = Coctail.values().length;
		
		coctailArray = new Recipe [length];
		
//		for (Coctail c : Coctail.values().length){
//			System.out.println(c);
		
		
		Caipirinha caipirinha = new Caipirinha();
		YellowRunner yellowrunner = new YellowRunner();
		SwimmingPool swimmingpool = new SwimmingPool();
		
		coctailArray [0]= caipirinha;
		coctailArray [1]= yellowrunner;
		coctailArray [2]= swimmingpool;
		
		
	}
	
	public double getPrice(Coctail c){
		
		if (c == Coctail.Caipirinha){
			return coctailArray[0].getTotalPrice(); 
		} 
		if (c == Coctail.YellowRunner){
			return coctailArray[1].getTotalPrice(); 
		}
		if (c == Coctail.SwimmungPool){
			return coctailArray[2].getTotalPrice(); 
		} else 
			return -1;								//bei Eingabe vom ungültigen Coctail
		}
	
	public double getWeight(Coctail c){
		
		if (c == Coctail.Caipirinha){
			return coctailArray[0].getTotalWeight(); 
		} 
		if (c == Coctail.YellowRunner){
			return coctailArray[1].getTotalWeight(); 
		}
		if (c == Coctail.SwimmungPool){
			return coctailArray[2].getTotalWeight(); 
		} else 
			return -1;								//bei Eingabe vom ungültigen Coctail
	}
	
	
	public boolean containsAlcohol(Coctail c){
		
		if (c == Coctail.Caipirinha){
			return coctailArray[0].containtsAlcohol(); 
		} 
		if (c == Coctail.YellowRunner){
			return coctailArray[1].containtsAlcohol(); 
		}
		if (c == Coctail.SwimmungPool){
			return coctailArray[2].containtsAlcohol(); 
		} else 
			return false;								
	}
		
	}


