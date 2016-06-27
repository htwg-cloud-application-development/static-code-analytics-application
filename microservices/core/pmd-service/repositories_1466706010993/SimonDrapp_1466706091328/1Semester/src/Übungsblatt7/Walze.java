package Übungsblatt7;

public class Walze {

	private final String walze50 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private final String walze51 = "ADCBEHFGILJKMPNOQTRSUXVWZY";
	private final String walze60 = "ACEDFHGIKJLNMOQPRTSUWVXZYB";
	private final String walze61 = "AZXVTRPNDJHFLBYWUSQOMKIGEC";
	private final String walze70 = "AZYXWVUTSRQPONMLKJIHGFEDCB";
	private final String walze71 = "AEBCDFJGHIKOLMNPTQRSUYVWXZ";

	private int walzennr;

	public Walze(int wnummer) { //Konstruktor zum übergeben der Walzennumer
		walzennr = wnummer;
	}

	public String getValueWalze(int value) { //Walzenauswahl
		switch (value) {
		case 50:
			return walze50;
		case 51:
			return walze51;
		case 60:
			return walze60;
		case 61:
			return walze61;
		case 70:
			return walze70;
		case 71:
			return walze71;
		default:
			return null;
		}
	}

	public int countClockwiseRotations(char start, char ziel) {

		int abstand = 0;
		int s = 0;
		int z = 0;
		int z1 = 0;

		String w = getValueWalze(walzennr); //ruft Methode zur Walzenauswahl auf

		for (int i = 0; i < w.length(); i++) {

			if (w.charAt(i) == start) {
				s = i;
			}
			if (w.charAt(i) == ziel) {
				z = i;
			}
			if (s <= z) {
				abstand = z - s;
			} else if (z < s) {  		//wenn das Ziel kleiner ist als der Start ""for-Schleife von vorne""
				z1 = w.length() + z;
				abstand = z1 - s;
			}
		}
		return abstand;
	}

	public int countCounterClockwiseRotations(char start, char ziel) {

		int abstand = 0;
		int s = 0;
		int z = 0;

		String w = getValueWalze(walzennr);

		for (int i = 0; i < w.length(); i++) {

			if (w.charAt(i) == start) {
				s = i;
			}

			if (w.charAt(i) == ziel) {
				z = i;
			}
			if (s >= z) {
				abstand = s - z;
			} else if (s < z) {
				abstand = s + w.length() - z; //startindex + 26 - zielindex
			}
		}
		return abstand;
	}

	public char rotateClockwise(char start, int rotation) {

		char ziel = 0;
		int a = 0;

		String w = getValueWalze(walzennr);

		for (int i = 0; i < w.length(); i++) {

			if (w.charAt(i) == start) {

				if (i + rotation < w.length()) { 
					ziel = w.charAt(i + rotation);

				} else {

					a = w.length() - i; 			// 26 - i
					ziel = w.charAt(rotation - a);	

				}
			}
		}

		return ziel;
	}

	public char rotateCounterClockwise(char start, int rotation) {

		char ziel = 0;
		int a = 0;

		String w = getValueWalze(walzennr);

		for (int i = 0; i < w.length(); i++) {

				if (w.charAt(i) == start) {

					if (i - rotation >= 0) {

						ziel = w.charAt(i - rotation);
					} else {

						a = rotation - i;
						ziel = w.charAt(w.length() - a);
					}
				}
			}
		return ziel;
	}
}
