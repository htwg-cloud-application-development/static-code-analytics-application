package Ü03;

import java.util.Scanner;

public class Aufgabe_19 {

	public static void main (String[] args) {
		
		Scanner s = new Scanner (System.in);
		
		double exchange;
			System.out.println("How much is the exchange rate");
			exchange = s.nextDouble();

		double yourMoney = s.nextDouble();
			System.out.println("How much money do you want to exchange?");


		double changedMoney;

			System.out.println("If you want to have EUR type: 1");
			System.out.println("If you want to have USD type: 2 ");
			
			String theway = s.next();

			if (theway.equals("1")){
				changedMoney = (yourMoney*exchange);
				System.out.println("You will get " + changedMoney + " EUR for  " + yourMoney + " Dollars");
			}
				else {
					if (theway.equals("2")) {
						changedMoney = (yourMoney/exchange);
						System.out.println("You will get " + changedMoney + " Dollars for  " + yourMoney + " Euro");
		
					} else {
		
			System.out.println("Please enter yes or no.");

	}
}
	}
}
