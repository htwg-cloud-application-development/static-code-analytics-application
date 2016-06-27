import java.util.Scanner;

public class Aufgabe30 {

	public static void main(String[] args) {
		boolean[] primArray = new boolean[1000];
		for (int i = 0; i < primArray.length; i++) {

			primArray[i] = false;
		}

		int sizeA = 1000;
		
		for (int i = sizeA; i > 1; i--) {
			if (checkPrim(i) == true) {//primzahl check

				primArray[i] = true;

			} 
		}
		Scanner scanner = new java.util.Scanner(System.in);
		System.out.println("Zahl 1-1000");
		int eingabe = scanner.nextInt();
		if (primArray[eingabe] == true) {// wenn primArrayn stimmt dann ausgabe
			System.out.println("Woho ist prim");

		} else {
			for (int i = 2; i <= eingabe; i++) {
				while (eingabe % i == 0) {//eingabe muss 0 zu i sein

					eingabe = eingabe / i;//eingabe wird durch i geteilt und dan auf diese definiert
										// damit es in der nächten schleife abgezogen ins und 
										//die selben zahlen nicht nochmal berechnet werden
					
					System.out.println(i);//Angabe aller primzahlen
				}
			}
		}
scanner.close();
	}
	

	public static boolean checkPrim(int number) { //Aufgabe 29
		
		boolean prim = true;

		for (int i = number - 1; i > 1; i--) {
			if (number % i == 0) {
				prim = false;
				break;

			}
		}
		return prim;
	}

}
