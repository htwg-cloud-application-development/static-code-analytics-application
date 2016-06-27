
public class Aufgabe29 {

	public static void main(String[] args) {
		java.util.Scanner scanner = new java.util.Scanner(System.in);

		int eingabe = 100;

		for (int i = eingabe; i > 1; i--) {
			if (checkPrim(i) == true) {//primzahl check
				System.out.println(i);

			} else {
				boolean first = true; // müssen im else sein da sie immer wieder auf 0 gesetzt werden
				int minPrim = 0;
				int maxPrim = 0;
				for (int j = i; j > 1; j--) {//1 Ausgeschlossen; Vermindert

					if (checkPrim(j) == true) {//primzahl check
						if (i % j == 0) {
							minPrim = j;// aussuchen der Primzahlen
							if (first == true) {

								maxPrim = j;
								first = false;//Wenn Primzahl gefunden dann false Damit es aufhört!
							}
						}
					}
				}
				System.out.println(+i + " bestehend aus " + minPrim + " und " + maxPrim);

				scanner.close();
			}
		}

	}

	public static boolean checkPrim(int number) { // number ist hier unser Übergabeparameter
	
		boolean prim = true;

		for (int i = number - 1; i > 1; i--) {
			if (number % i == 0) {//wenn kein teil rest dan keine primzahl
				prim = false;
				break;

			}
		}
		return prim;
	}


}
