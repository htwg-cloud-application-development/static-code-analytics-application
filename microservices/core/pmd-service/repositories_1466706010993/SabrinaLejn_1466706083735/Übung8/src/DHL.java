import Aufgabe63bis68.Address;
import Aufgabe63bis68.Tarifrechner;

public class DHL extends Tarifrechner{

	public DHL(){
		this.versand = "Hermes";
	}
	@Override
	public double Versandkostenberechnung(Address adresse, double weight) {
		
		double versandkosten = 0;
		
		if (adresse.getLand()== "Deutschland"){
			versandkosten = 5.99;
		} else 
			if(adresse.getLand() == "Großbritannien"){
			versandkosten = 15.99;
			
			}
				

		return versandkosten;
	
	
	}
}
