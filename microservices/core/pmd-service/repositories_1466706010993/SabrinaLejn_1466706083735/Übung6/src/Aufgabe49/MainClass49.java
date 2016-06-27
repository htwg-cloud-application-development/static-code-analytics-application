package Aufgabe49;

import java.util.Scanner;

public class MainClass49 {
	public static void main(String args[]) {
		Scanner s = new Scanner(System.in);  // neuer Scanner
		boolean laufe = true;
		boolean valueOK = false;
		PrimeNumber primeNumberObject = new PrimeNumber();
				
		while(laufe){
			
			if(valueOK){
				laufe = false;
			}else{
				System.out.print("Please enter an integer: ");
				String eingabeUser = s.next();
				
				
			try{										//wenn im try block ein Fehler...		
				primeNumberObject.value = Integer.parseInt(eingabeUser);
				
				if(primeNumberObject.value > 0 && primeNumberObject.value <= 1000){
					valueOK = true;
				}else{
					System.out.println("Keine Zahl zwischen 1 und 1000 eingegeben!");
					valueOK = false;
				}
				
														// ...wird in den catch-Block abgesprungen
			}catch(Exception e){
						System.out.println("Depp: Keine gerade Zahl eingegeben!");
						valueOK = false;														//nach dem catch-Block geht es weiter, wenn in diesen gesprungen wurde
			}
			}
		
													
		}
		s.close();
		System.out.print(primeNumberObject.isPrime());
}
														
}


