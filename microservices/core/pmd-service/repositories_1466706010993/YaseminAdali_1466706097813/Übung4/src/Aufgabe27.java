
public class Aufgabe27 {

	public static void main(String[] args) {
		java.util.Scanner scanner = new java.util.Scanner(System.in);
		System.out.println("Array Zahl");
		int eingabe = scanner.nextInt();

		int randomnumber;
		if (eingabe < 0) {
			System.out.println("falsche eingabe");

		} else {
			int[] anIntArray = new int[eingabe];

			for (int i = 0; i < anIntArray.length; i++) {
				randomnumber = (int) (Math.random() * 200) - 100;
				anIntArray[i] = randomnumber;
				
				System.out.println("Index:" + i + "-Zahl: " + randomnumber); // Aufgabe26
			}
			double Durchschnitt = 0;// zahlen definition deshalb 0

			int Summe = 0; // Zahlen definition deshalb 0
			for (int id = 0; id < anIntArray.length; id++) {
				Summe = Summe + anIntArray[id]; // operator (x=x+y)

			}
			System.out.println("Summe:       "+Summe);
			
			Durchschnitt = Summe / anIntArray.length;
			System.out.println("Durchschnitt:    "+Durchschnitt);
			
			
			scanner.close();//close schliesst die eingabe
		}
	}

}
