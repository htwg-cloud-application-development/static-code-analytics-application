package Übungsblatt4;

import java.util.ArrayList;

public class Aufgabe_29 {

	public static void main(String[] args) {

		boolean isprim = true;

		ArrayList<Integer> list1 = new ArrayList<Integer>();
		ArrayList<Integer> list2 = new ArrayList<Integer>();

		int[] prim = new int[100];

		for (int i = 0; i < prim.length; i++) {

			prim[i] = i + 1;
			isprim = true;

			for (int j = 2; j < prim.length; j++) {

				if (prim[i] % j == 0 && prim[i] != j) {
					isprim = false;
				}

				boolean kleinprim = true;

				for (int k = 2; k < j; k++) {

					if (j % k == 0) {

						kleinprim = false;
					}
				}
				if (kleinprim) {

					list1.add(j);
				}
			}
			if (isprim && prim[i] != 1) {
				System.out.println(prim[i]);
			}

		}

		System.out.println("*****");

		for (int i = 0; i < prim.length; i++) {

			prim[i] = i + 1;
			isprim = true;

			for (int j = 2; j < prim.length; j++) {

				if (prim[i] % j == 0 && prim[i] != j) {
					System.out.print(prim[i]);

					int a = 0;
					while (prim[i] != 1) {

						if (prim[i] % list1.get(a) == 0) {

							prim[i] = prim[i] / list1.get(a);

							list2.add(list1.get(a));
						} else {

							a++;
						}
					}

					// System.out.println("Die Primzahlzerlegung lautet: ");
					for (int k = 0; k < list2.size(); k++) {

						System.out.print(+list2.get(k));

						if (k < list2.size() - 1) {

							System.out.print("*");
						}
					}
					break;

				}
			}
		}

		// int i = 0;
		// while(prim[i] != 1){
		//
		// if(prim[i] % list1.get(i)==0){
		//
		// prim[i] = prim[i] / list1.get(i);
		//
		// list2.add(list1.get(i));
		// }
		// else{
		//
		// i++;
		// }
		// }
		//
		// System.out.println("Die Primzahlzerlegung lautet: ");
		// for (int j = 0; j < list2.size(); j++) {
		//
		// System.out.print(list2.get(j));
		//
		// if (j < list2.size() - 1) {
		//
		// System.out.print("*");
		// }
		// }

	}
}
