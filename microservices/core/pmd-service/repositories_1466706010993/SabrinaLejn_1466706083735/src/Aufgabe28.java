import java.util.Arrays;
import java.util.Scanner;

public class Aufgabe28 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		int enNumber,id=0;
		double Sum = 0;
		int MAX=-100,MIN=100,MAXid=0,MINid=0;
		
		
		int[] anIntArray = null;
		System.out.print("Please enter a number: ");
		enNumber = scanner.nextInt();

		anIntArray = new int[enNumber];
		Arrays.sort(anIntArray);
		
		for (id = 0; id < anIntArray.length; id++) {
			anIntArray[id] = (int) (Math.random() * 201) - 100;
			if (MAX<anIntArray[id]){									//NEU
				MAX=anIntArray[id]; MAXid=id;}							//NEU	
			if (MIN>anIntArray[id]){									//NEU
				MIN=anIntArray[id]; MINid=id;}							//NEU
						
			Sum = Sum + anIntArray[id];	
			System.out.println("[" + id + "]" + "=" + anIntArray[id]);
		}
	
	
		
		System.out.println("Sum: " + Sum);							
		System.out.println("Avarage: " + (double) Sum / enNumber);	
		System.out.println("MAX: "+MAX+" id="+MAXid);						//NEU
		System.out.println("MIN: "+MIN+" id="+MINid);						//NEU
		
		

		scanner.close();
}}