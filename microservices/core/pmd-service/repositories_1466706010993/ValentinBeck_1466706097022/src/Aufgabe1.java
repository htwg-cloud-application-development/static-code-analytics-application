import java.util.*;


public class Aufgabe1 {
public static void main(String args[]){

	Scanner sc = new Scanner(System.in);
	System.out.println("Geben sie die Feldgröße an: ");
	int length = sc.nextInt();
	int [] intArray = new int[length];
	int sum = 0;
	
	if(length<1){
		System.out.println("Nicht positive Eingabe nicht möglich.");
	}
	else{
				
		for(int i=0; i<length; i++){
			int rand = (int) ((Math.random() * 200)-100);
			System.out.println("random: "+rand);
			intArray[i]=rand;
			System.out.println(intArray[i]);
		}
	}
	
	for(int n = 0; n < intArray.length; n++){
		sum = sum + intArray[n];
	}
	
	System.out.println("Die Summe ist: "+sum);
	System.out.println("Der Durchschnitt ist: "+ (double) (sum/intArray.length));
	
	int min = intArray[0];
	int index = 0;
	for(int c = 0; c < intArray.length; c++){
		
		if(min > intArray[c]){
			min = intArray[c];
			System.out.println("neues Minimum gefunden: "+intArray[c]+" mit Index "+c);
			index = c;
		}
		else{
			
		}
	}
	System.out.println("Das Minimum ist "+min+" mit dem index "+index+" ." );
	//EOF
}
}
