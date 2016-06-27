import java.util.Scanner;

public class Aufgabe22 {

	public static void main(String[] args) {
		Scanner scanner =new Scanner(System.in);
		System.out.println("Geben Sie eine Zahl ein:");
		int zahl = scanner.nextInt();
		boolean Primzahl;
		
	
	for (int i = 2 ; i*i <= zahl; i++){
		
		if (zahl%i ==1) {
			Primzahl=false;
			System.out.println("Zahl ist eine Primzahl");
			break;
		}
		else Primzahl=true;{
			System.out.println("Zahl ist keine Primzahl");
			return;
		}
	
		}
	}

}

