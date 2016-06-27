import java.util.Scanner;

public class Aufgabe30 {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int id;
		boolean[] IsPrime = new boolean[1000];
		int[] PrimeArray = new int[1000];

		for (id = 0; id < PrimeArray.length; id++) {
			PrimeArray[id] = id + 1;

			// System.out.println(PrimeArray[id]);
			IsPrime[1] = true;
			for (int j = 2; j < PrimeArray[id]; j++) {
				IsPrime[id] = true;

				if (PrimeArray[id] % j == 0) {
					IsPrime[id] = false;
					break;
				}
				if (j == PrimeArray[id]) {
					IsPrime[id] = true;
				}
			}
		}
		for (id = 0; id < PrimeArray.length; id++) {

			if (IsPrime[id] == true) {
				// System.out.println(PrimeArray[id] + " is prime");

			}
		}
		int number;
do{		
		System.out.println("Enter a number between 1 and 1000: ");
		number = s.nextInt();}
	while (number <1 | number>1000);	

		if (IsPrime[number - 1] == true) {
			System.out.println(number + " is prime");
		} else {
			System.out.println("Zerlegung");
			for (int a = 2; number != 1; a++) {
				if (number % a == 0) {
					System.out.print(a + " ");
					number = number / a;					//Nummer durch Primzahl teilen und mit dieser weiterrechnen
					a = 1;									//a zurücksetzen auf 2 (1++)
				}

			}
		}

s.close();
	}}

