package Aufgabe63bis68;

public class DPD extends Tarifrechner  {
	public DPD(){
		this.versand = "Hermes";
	}
	
	@Override
	public double Versandkostenberechnung(Address adresse, double weight) {
	
		
		double versandkosten = 0;
		
		if (adresse.getLand()== "Deutschland"){
			versandkosten = 7.00;
		} else 
			if(adresse.getLand() == "Großbritannien"){
			versandkosten = 19.93;
			
			}
				
		return versandkosten;
		
	}
	
	
	}
	

