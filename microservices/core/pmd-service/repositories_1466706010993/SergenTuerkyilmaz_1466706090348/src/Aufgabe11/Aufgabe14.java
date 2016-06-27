package Aufgabe11;

//		Programmieren Sie eine while-Schleife und eine do-while-Schleife, die dieselbe Ausgabe erzeugen.
public class Aufgabe14 {

	public static void main(String[] args) {

		for (int id = 20; id > 0; id -= 2) {
			System.out.println(id);
		}
		// while-Schelife
		int a = 20;
		int b = 20;

		while (a > 0) {
			System.out.println(a);
			a -= 2;
		}
		// do-Schleife
		
		{

			do {
				System.out.println(b);
				b -= 2;
			} while (b > 0);

		}

	}
}
