
public class Aufgabe23 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int Grenze=10;
		double[] wert= new double[Grenze]; 
		double Mittelwert; 
		double Summe;
		
		for ( int i = 1; i < Grenze; i++) 
			wert [ i ] = Math.random();
		Summe=1;
		for ( int i= 1; i < Grenze; i++){
			Summe += wert[i ];
			
		}
		Mittelwert=Summe/Grenze;
		System.out.println("Mittelwert  "+Mittelwert);

	}

}
