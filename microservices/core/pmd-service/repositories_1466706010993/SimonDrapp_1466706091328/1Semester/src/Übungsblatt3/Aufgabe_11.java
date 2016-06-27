package Übungsblatt3;

import java.util.Scanner;

public class Aufgabe_11 {

	public static void main(String[] args) {

		int number1;
		int number2;
		int number3;
		// int counter = 0;

		Scanner scanner = new Scanner(System.in);

		// int[] arrsort = new int[3];

		System.out.print("Enter the first number: ");
		// arrsort[0] = scanner.nextInt();
		number1 = scanner.nextInt();

		System.out.print("Enter the second number: ");
		// arrsort[1] = scanner.nextInt();
		number2 = scanner.nextInt();

		System.out.print("Enter the third number: ");
		// arrsort[2] = scanner.nextInt();
		number3 = scanner.nextInt();

		System.out.println();

		// for(int i = 0; i < arrsort.length;i++ ){
		//
		// for(int j = 0; j < arrsort.length-1-i; j++){
		//
		// if(arrsort[j] > arrsort[j+1]){
		//
		// int tausch = 0;
		//
		// tausch = arrsort[j];
		// arrsort[j] = arrsort[j+1];
		// arrsort[j+1] = tausch;
		// }
		// counter++;
		// }
		// }
		//
		// for(int i = 0; i < arrsort.length; i++){
		//
		// System.out.println(arrsort[i]);
		// }

		if (number1 < number2 && number2 < number3) {

			System.out.println(number1);
			System.out.println(number2);
			System.out.println(number3);
		} else if (number1 < number2 && number3 < number2 && number1 < number3) {

			System.out.println(number1);
			System.out.println(number3);
			System.out.println(number2);

		}
		if (number2 < number1 && number1 < number3) {

			System.out.println(number2);
			System.out.println(number1);
			System.out.println(number3);
		} else if (number2 < number1 && number3 < number1 && number2 < number3) {

			System.out.println(number2);
			System.out.println(number3);
			System.out.println(number1);
		}

		if (number3 < number1 && number1 < number2) {

			System.out.println(number3);
			System.out.println(number1);
			System.out.println(number2);
		} else if (number3 < number1 && number2 < number1 && number3 < number2) {

			System.out.println(number3);
			System.out.println(number2);
			System.out.println(number1);

		}
	}
}