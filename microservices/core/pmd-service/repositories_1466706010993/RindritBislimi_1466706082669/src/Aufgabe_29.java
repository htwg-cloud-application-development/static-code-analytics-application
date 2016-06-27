
import java.util.Scanner;

public class Aufgabe_29 {

	public static void main(String[] args) {
		
			Scanner s = new Scanner (System.in);
			
			int [] array = new int [100];
			int teiler;
			int[] prim = new int[25];
			int[] nichtPrim = new int[75];
			int x = 0;
			int y = 0;
			
			for (int z = 0; z < array.length; z++) {
				array [z] = z+1;
			}
			
			for (int num = 1; num < array.length; num++)	{
				if (num != 1)	{
					teiler = 0;
					for(int zahl = 1; zahl <= num; zahl ++)	{
						if (num % zahl == 0) {
							teiler++;
							
						}
					}
					if (teiler == 2) {
						System.out.println(num);
						prim[x] = num;
						x++;
					}
					else {
						nichtPrim[y] = num;
						y++;
					}
				}
			}
			
			for (int up = 0; up < nichtPrim.length; up++) {
				if (nichtPrim[up] != 1) {
					if (nichtPrim[up] % prim [0] == 0) {
						System.out.println("Kleinster Primfaktor ist 2");
					}
				}
			}
	}
}
