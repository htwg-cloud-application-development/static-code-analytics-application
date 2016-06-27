
//Aufgabe 7
package Aufgabe7;

public class Aufgabe7 {

	public static void main(String[] args) {
		
byte n = 3;  
		
		System.out.println("Die ersten 4 Stellenwerte im Binaeren-System: "); //Binaeres-System (Basis 2)
		
		for(int i = 0; i <= n; i++){
			double x 	= Math.pow(2,i);
			System.out.println("2^" +i + "=" + x);	}	
			
		System.out.println("Die ersten 4 Stellenwerte im Oktal-System: "); //Oktal-System (Basis 8)
			
			for(int i = 0; i <= n; i++){
				double x 	= Math.pow(8,i);
			System.out.println("2^" +i + "=" + x);}
	
		
		System.out.println("Die ersten 4 Stellenwerte im Dezimalsystem-System:"); //Dezimal-System (Basis 10)
				
				for(int i = 0; i <= n; i++){
					double x 	= Math.pow(10,i);
			System.out.println("2^" +i + "=" + x);}
				
		
		System.out.println("Die ersten 4 Stellenwerte im Hexadezilmal-System: "); //Hexadezimal-System (Basis 16)
					
					for(int i = 0; i <= n; i++){
						double x 	= Math.pow(16,i);
			System.out.println("2^" +i + "=" + x);	}
					
		
		System.out.println("Die ersten 4 Stellenwerte im Sexagesimal-System: ");  //Sexagesimal-System (Basis 60)
						
						for(int i = 0; i <= n; i++){
							double x 	= Math.pow(60,i);
			System.out.println("2^" +i + "=" + x);	}
	}
			
		
	}




