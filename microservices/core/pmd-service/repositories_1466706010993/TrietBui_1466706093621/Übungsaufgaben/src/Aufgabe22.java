import java.util.Scanner;

public class Aufgabe22 {

	public static void main(String[] args) {

		Scanner s = new Scanner(System.in);
		int zahl = s.nextInt();
		boolean istPrim=false;

		for (int i = 2; i < zahl; i++) {
			if(zahl%i ==0){
				istPrim=false;
			
				break;
				
			} else{
				istPrim=true;
			}
			
		}
		if (istPrim==true){
			System.out.println("Die Zahl ist eine Primzahl");
			
		}
		else { 
			System.out.println("Die Zahl ist keine Primzahl");
		}
	}
}
