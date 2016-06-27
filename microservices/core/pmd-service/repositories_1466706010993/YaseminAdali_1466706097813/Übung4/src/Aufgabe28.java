
public class Aufgabe28 {

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
				System.out.println("Index:" + i + "-Zahl: " + randomnumber);//Aufgabe26
			}
			int minIndex = 0;
			int min = anIntArray[0];
			
			for (int j = 0; j < anIntArray.length; j++) {
				if (anIntArray[j] < min) {//An Array stelle j(kleiner als Array länge und aufsteigend)
										//ist es kleiner als min (wird auf 0 gesetzt)
					min = anIntArray[j];//min wird auf arry j gesetzt, da "for"-schleife 
										// wird so lange laufen bis es keine kleinere Zahl gibt
					minIndex = j;
				}
			}
			System.out.println(min);
			System.out.println(minIndex);
			
			int maxIndex =0;
			int max =anIntArray[0];
			for (int k =0; k<anIntArray.length; k++){
				if (anIntArray[k]>max){ // gleicher vorgang wie bei min nur mit anderen Zahlen und max
					max= anIntArray[k];
					maxIndex=k;
				}
			}
			System.out.println("max "+max);
			System.out.println("MaxIndex "+maxIndex);
		}scanner.close();
	}

}
