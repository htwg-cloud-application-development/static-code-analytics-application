import Aufgabe63bis68.Address;
import Aufgabe63bis68.Tarifrechner;

public class Hermes extends Tarifrechner{

	public Hermes(){
		this.versand = "Hermes";
	}

	@Override
	public double Versandkostenberechnung(Address adresse, double weight) {
		
		double versandkosten=0;
		
		if (adresse.getLand()== "Deutschland"){
			versandkosten = 5.90;
		} else 
			if(adresse.getLand() == "Großbritannien"){
			versandkosten = 17.54;
			
			}
				
		
		return versandkosten;
		
	}
	
	
	}


