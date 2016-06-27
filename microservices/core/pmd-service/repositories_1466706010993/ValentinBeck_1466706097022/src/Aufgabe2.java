
public class Aufgabe2 {
	public static void main(String args[]){
		
	int[] primzahlen = new int[24];	
	int limit = 100;
	int count = 0;
    int zahl;      
    int zaehler;   
    boolean primzahl;

    for (zahl = 2; zahl <= limit; zahl++) {
        primzahl = true;
        for (zaehler = 2; zaehler < Math.sqrt(zahl) + 1; zaehler++) {
            if (zahl % zaehler == 0) {
                primzahl = false;
                break;
            }
        }
        if (primzahl) {
            System.out.println(zahl+" ist eine Primzahl");
            primzahlen[count] = zahl;
            count++;
        }
    }
    
    for(int n = 0; n < primzahlen.length; n++){
    	System.out.println(primzahlen[n]);
    }
}
}

