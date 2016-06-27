package Übungsblatt8;

import Übungsblatt8.Drinks;

public class BeachBar {

	private Recipe[] drinks = new Recipe[Drinks.values().length];

	public BeachBar() {
				
		drinks[0]= new Caipirinha();
		drinks[1]= new SwimmingPool();
		drinks[2]= new YellowRunner();
		System.out.println("added");
		
	}

}

