import java.util.Scanner;

public class Aufgabe_26 {

	public static void main(String[] args) {
		
		Scanner s = new Scanner (System.in);
		
		byte x;
		
		do{
			System.out.println("Geben Sie eine Feldgröße an");
			x=s.nextByte();	
		}
		while(x<1);
		
		int[] array = new int [x];
		int y;
		
		for(int z = 0; z < array.length; z++)	{
			y = (int)(Math.random() * 201) -100;
			array [z] = y;
			System.out.print(y + " ");
		}
		
	}
}
