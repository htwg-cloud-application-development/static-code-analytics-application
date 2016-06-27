package Übungsblatt3;

import java.util.Scanner;

public class Aufgabe_17 {

	public static void main(String[] args) {

		double size;
		double weight;
		double bmi;

		Scanner s = new Scanner(System.in);

		System.out.println("This Body-Mass-Index classification is for adults ");
		System.out.println();

		System.out.println("Enter your weight in kg: ");
		weight = s.nextDouble();

		System.out.println("Enter your size in metres: ");
		size = s.nextDouble();

		bmi = weight / Math.pow(size, 2);

		System.out.println();

		if (bmi < 18.5) {

			System.out.println("--> underweight");
		}

		else if (bmi >= 18.5 && bmi <= 24.9) {

			System.out.println("--> normal weight");

		}

		else if (bmi >= 25 && bmi < 30.1) {

			System.out.println("--> overweight");
		}

		else if (bmi > 30 && bmi < 40.1) {

			System.out.println("--> obesity");
		}

		else if (bmi > 40) {

			System.out.println("--> strong obesity");
		}

	}

}