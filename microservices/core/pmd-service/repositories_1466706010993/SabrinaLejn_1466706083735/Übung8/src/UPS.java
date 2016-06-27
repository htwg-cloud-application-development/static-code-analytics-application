import Aufgabe63bis68.Address;
import Aufgabe63bis68.Tarifrechner;

public class UPS extends Tarifrechner{
	public UPS(){
		this.versand = "Hermes";
	}
	@Override
	public double Versandkostenberechnung(Address adresse, double weight) {
		
		double versandkosten = 0;
		
		if (adresse.getLand()== "Deutschland"){
			versandkosten = 6.83;
		} else 
			if(adresse.getLand() == "Großbritannien"){
			versandkosten = 20.80;
			
			}
				
		return versandkosten;
		
	}
	
	
	}
		
	




