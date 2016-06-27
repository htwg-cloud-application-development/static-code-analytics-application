package Übungsblatt7;

public class Aufgabe54_Enigma {

	private Walze[] walze = new Walze[3];
	private String key;

	public Aufgabe54_Enigma(int walze1, int walze2, int walze3, String schlüssel) {
		this.walze[0] = new Walze(walze1);
		this.walze[1] = new Walze(walze2);
		this.walze[2] = new Walze(walze3);
		this.key = schlüssel;
	}

																		// hier beginnt die Methode der Verschlüsselung
	public String encrypt(String s) {

		char[] f = { this.key.charAt(0), this.key.charAt(1), this.key.charAt(2) };

		String verschlüsseln = "";
		for (int i = 0; i < s.length(); i++) {
			if (i % 2 == 0) {
																		// die Walze 1 rotiert um n im Uhrzeigersinn 
																		// und Walze 3 rotiert um n --> auf Walze 3 Verschlüsselung ablesbar

				int rotation = this.walze[0].countClockwiseRotations(f[0], s.charAt(i));

				f[0] = this.walze[0].rotateClockwise(f[0], rotation);
				f[1] = this.walze[1].rotateCounterClockwise(f[1], rotation);
				f[2] = this.walze[2].rotateClockwise(f[2], rotation);

				verschlüsseln = verschlüsseln + f[2];

			} else {													//gleiches Spiel wie davor, diesmal Walze 2
																		// Walze 2 rotiert um n gegen den Uhrzeigersinn 
																		// und Walze 3 rotiert um n --> auf Walze 3 Verschlüsselung ablesbar

				int rotation = this.walze[1].countCounterClockwiseRotations(f[1], s.charAt(i));

				f[0] = this.walze[0].rotateClockwise(f[0], rotation);
				f[1] = this.walze[1].rotateCounterClockwise(f[1], rotation);
				f[2] = this.walze[2].rotateClockwise(f[2], rotation);

				verschlüsseln = verschlüsseln + f[2];
			}
		}
		return verschlüsseln;

	}

																		// hier fängt die Methode der Entschlüsslung an
	public String decrypt(String s) {

		char[] f = { this.key.charAt(0), this.key.charAt(1), this.key.charAt(2) };

		String entschlüsseln = "";
		for (int i = 0; i < s.length(); i++) {
			if (i % 2 == 0) {
																		// Walze 1 rotiert um n im Uhrzeigersinn 
																		// und Walze 3 rotiert um n --> auf Walze 3 Verschlüsselung ablesbar

				int rotation = this.walze[2].countCounterClockwiseRotations(s.charAt(i), f[2]);

				f[2] = this.walze[2].rotateClockwise(f[2], rotation);
				f[0] = this.walze[0].rotateClockwise(f[0], rotation);
				f[1] = this.walze[1].rotateCounterClockwise(f[1], rotation);
				

				entschlüsseln = entschlüsseln + f[0];

			} else {
																		// Walze 2 rotiert um n gegen den Uhrzeigersinn 
																		// und Walze 3 rotiert um n --> auf Walze 3 Verschlüsselung ablesbar

				int rotation = this.walze[2].countCounterClockwiseRotations(s.charAt(i), f[2]);

				f[0] = this.walze[0].rotateClockwise(f[0], rotation);
				f[1] = this.walze[1].rotateCounterClockwise(f[1], rotation);
				f[2] = this.walze[2].rotateClockwise(f[2], rotation);

				entschlüsseln = entschlüsseln + f[1];
			}
		}
		return entschlüsseln;

	}
}