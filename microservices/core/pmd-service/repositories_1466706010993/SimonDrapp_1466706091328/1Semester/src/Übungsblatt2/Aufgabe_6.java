package Übungsblatt2;

public class Aufgabe_6 {
	
	public static void main(String[] args) {

		int humanEurope = 742500000; // Anzahl Menschen in Europa
		long humanEarth = 7350000000l; // Anzahl Menschen auf der Erde

		double a = (double) humanEurope;
		double b = (double) humanEarth;

		double euEarth = b / a;
		System.out.println(
				"The portion of the citizens of the European Union in the world population amounts: " + euEarth);

		double euEarth1 = a / b * 100;
		System.out.println(
				"The portion of the citizens of the European Union in the world population in percents amounts: "
						+ euEarth1 + "%");
	}
}
