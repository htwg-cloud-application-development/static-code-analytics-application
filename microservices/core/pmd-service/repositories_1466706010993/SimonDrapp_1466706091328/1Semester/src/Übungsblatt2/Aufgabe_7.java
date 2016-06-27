package Übungsblatt2;

public class Aufgabe_7 {

	public static void main(String[] args) {

		// double binear = Math.pow(2,0);
		// double binear1 = Math.pow(2,1);
		// double binear2 = Math.pow(2,2);
		// double binear3 = Math.pow(2,3);
		// System.out.println(binear+""+binear1+""+binear2+""+binear3);
		//
		// double oktal = Math.pow(8,0);
		// double oktal1 = Math.pow(8,1);
		// double oktal2 = Math.pow(8,2);
		// double oktal3 = Math.pow(8,3);
		// System.out.println(oktal+""+oktal1+""+oktal2+""+oktal3);
		//
		// double dezimal = Math.pow(10,0);
		// double dezimal1 = Math.pow(10,1);
		// double dezimal2 = Math.pow(10,2);
		// double dezimal3 = Math.pow(10,3);
		// System.out.println(dezimal+""+dezimal1+""+dezimal2+""+dezimal3);
		//
		// double hexa = Math.pow(16,0);
		// double hexa1 = Math.pow(16,1);
		// double hexa2 = Math.pow(16,2);
		// double hexa3 = Math.pow(16,3);
		// System.out.println(hexa+""+hexa1+""+hexa2+""+hexa3);
		//
		// double sexa = Math.pow(60,0);
		// double sexa1 = Math.pow(60,1);
		// double sexa2 = Math.pow(60,2);
		// double sexa3 = Math.pow(60,3);
		// System.out.println(sexa+""+sexa1+""+sexa2+""+sexa3);

		System.out.println("Die ersten vier Stellenwerte im Binärsystem lauten: ");

		for (int i = 0; i <= 3; i++) {

			double binear = Math.pow(2, i);
			System.out.println("2^" + i + " = " + binear);
		}

		System.out.println();

		System.out.println("Die ersten vier Stellenwerte im Oktalsystem lauten: ");

		for (int i = 0; i <= 3; i++) {

			double oktal = Math.pow(8, i);
			System.out.println("2^" + i + " = " + oktal);
		}

		System.out.println();

		System.out.println("Die ersten vier Stellenwerte im Dezimalsystem lauten: ");

		for (int i = 0; i <= 3; i++) {

			double dezimal = Math.pow(10, i);
			System.out.println("2^" + i + " = " + dezimal);
		}

		System.out.println();

		System.out.println("Die ersten vier Stellenwerte im Hexadezimalsystem lauten: ");

		for (int i = 0; i <= 3; i++) {

			double hexa = Math.pow(16, i);
			System.out.println("2^" + i + " = " + hexa);
		}

		System.out.println();

		System.out.println("Die ersten vier Stellenwerte im Sexagesimalsystem lauten: ");

		for (int i = 0; i <= 3; i++) {

			double sexa = Math.pow(60, i);
			System.out.println("2^" + i + " = " + sexa);
		}

	}
}
