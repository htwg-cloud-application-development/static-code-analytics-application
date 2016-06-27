package Übungsblatt3;

public class Aufgabe_14 {

	public static void main(String[] args) {

		int id = 20;
		int id1 = 20;
		int id2 = 20;

		for (id = 20; id > 0; id -= 2) {

			System.out.println(id);
		}

		System.out.println("*******");

		while (id1 > 0) {

			System.out.println(id1);
			id1 -= 2;
		}

		System.out.println("*******");

		do {
			System.out.println(id2);
			id2 -= 2;
		} while (id2 > 0);

	}

}