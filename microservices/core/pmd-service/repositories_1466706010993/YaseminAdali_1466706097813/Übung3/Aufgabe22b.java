import java.util.Scanner;

public class Aufgabe22b {

	public static void main(String[] args) {
		Scanner scanner =new Scanner(System.in);
		System.out.println("Geben Sie eine Zahl ein:");
		int Zahl = scanner.nextInt();
		System.out.println(Zahl);
		
		for (int i = 2 ; i <= Zahl; i++){
			
			if (Zahl% i ==0){
				Zahl=Zahl/i;
				System.out.println(i);
			}
			while (Zahl) { <Anweisung/Block> }
}
		
	}
}