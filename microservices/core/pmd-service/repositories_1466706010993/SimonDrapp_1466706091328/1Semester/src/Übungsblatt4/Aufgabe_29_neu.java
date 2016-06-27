package Übungsblatt4;

import java.util.ArrayList;

public class Aufgabe_29_neu {

	public static void main(String[] args) {

		int limit = 101;
		boolean[] primzahlArray = new boolean[limit];

		ArrayList<Integer> list1 = new ArrayList<Integer>();

		for (int i = 1; i < primzahlArray.length; i++) {

			if (i == 1) {

				primzahlArray[i] = false;
			}

			else {
				primzahlArray[i] = true;
				for (int j = 2; j < i; j++) {

					if (i % j == 0) {
						primzahlArray[i] = false;
					}

				}
			}
		}

		for (int i = 2; i < primzahlArray.length; i++) {
			if (primzahlArray[i]) {

				System.out.println(i);
			}

			else {

				System.out.print(i);
				int a = 0;
				int zahl = i;
				while (zahl != 1) {

					if (primzahlArray[a] && zahl % a == 0) {

						zahl = zahl / a;

						list1.add(a);

					} else {

						a++;

					}

				}
				if (i >= 4) {

					System.out.println(" --> " + list1.get(0) + " " + (list1.get(list1.size() - 1)));

				}

			}

			// for (int j = 0; j < list1.size(); j++) {
			//
			// System.out.print(list1.get(j));
			// System.out.println();

			// }
		}

	}
}
