
public class Aufgabe26 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		java.util.Scanner scanner = new java.util.Scanner(System.in);
		System.out.println("Array Zahl");
		int eingabe = scanner.nextInt();

		int randomnumber;
		if (eingabe < 0) {
			System.out.println("falsche eingabe"); //Wenn Eingabe kleiner als 0 ist dann f

		} else {
			int[] anIntArray = new int[eingabe];

			for (int i = 0; i < anIntArray.length; i++) {//anIntArray.length gibt aus, wie groß das Array ist
														// Abbruchbedingung wird festgelegt  
				
				randomnumber = (int) (Math.random() * 200) - 100; // Zufalls zahlen von -100 bis 200 
				anIntArray[i] = randomnumber; // i wird mit randomnuber gleich gesetzt 
				System.out.println("Index:" + i + "-Zahl: " + randomnumber); // 
			}
		}
		scanner.close(); //close schliesst die eingabe
	}


	}


