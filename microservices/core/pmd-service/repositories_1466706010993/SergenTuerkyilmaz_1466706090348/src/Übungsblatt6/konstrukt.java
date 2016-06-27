package Übungsblatt6;

import java.util.Scanner;

public class konstrukt {

	public boolean isPrime;
	public int value;

	public konstrukt(int valueX) {
		this.value = valueX;
		int z; // z ist der Teiler

		if (value != 1) {
			z = 0;
			for (int i = 1; i <= value; i++) {
				if (value % i == 0) {
					z++;
				}

			}
			if (z == 2) {
				isPrime = true;

			}

		}
	}
}