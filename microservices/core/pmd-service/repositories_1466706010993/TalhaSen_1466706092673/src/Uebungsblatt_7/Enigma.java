package Uebungsblatt_7;

public class Enigma {
	private Walze[] walze = new Walze[3];
	private String key;

	public Enigma(int walzennummer1, int walzennummer2, int walzennummer3, String schlüssel) {
		this.walze[0] = new Walze(walzennummer1);
		this.walze[1] = new Walze(walzennummer2);
		this.walze[2] = new Walze(walzennummer3);
		key = schlüssel;
	}

	// Methode Verschlüsselung
	public String encrypt(String s) {

		char[] f = { this.key.charAt(0), this.key.charAt(1), this.key.charAt(2) };

		String verschlüsseln = "";
		for (int i = 0; i < s.length(); i++) {
			if (i % 2 == 0) {
				// Walze 1 rotirert um n im Uhrzeigersinn und Walze 3 rotiert um
				// n --> auf Walze 3 Verschlüsselung ablesbar

				int rotation = this.walze[0].countClockwiseRotations(f[0], s.charAt(i));

				f[0] = this.walze[0].rotateClockwise(f[0], rotation);
				f[1] = this.walze[1].rotateCounterClockwise(f[1], rotation);
				f[2] = this.walze[2].rotateClockwise(f[2], rotation);

				verschlüsseln = verschlüsseln + f[2];

			} else {
				// Walze 2 rotirert um n gegen den Uhrzeigersinn und Walze 3
				// rotiert um n --> auf Walze 3 Verschlüsselung ablesbar

				int rotation = this.walze[1].countCounterClockwiseRotations(f[1], s.charAt(i));

				f[0] = this.walze[0].rotateClockwise(f[0], rotation);
				f[1] = this.walze[1].rotateCounterClockwise(f[1], rotation);
				f[2] = this.walze[2].rotateClockwise(f[2], rotation);

				verschlüsseln = verschlüsseln + f[2];
			}
		}
		return verschlüsseln;

	}

	// Methode Entschlüsslung
	public String decrypt(String s) {

		char[] f = { this.key.charAt(0), this.key.charAt(1), this.key.charAt(2) };

		String entschlüsseln = "";
		for (int i = 0; i < s.length(); i++) {
			if (i % 2 == 0) {
				// Walze 1 rotirert um n im Uhrzeigersinn und Walze 3 rotiert um
				// n --> auf Walze 3 Verschlüsselung ablesbar

				int rotation = this.walze[2].countCounterClockwiseRotations(s.charAt(i), f[2]);

				f[2] = this.walze[2].rotateClockwise(f[2], rotation);
				f[0] = this.walze[0].rotateClockwise(f[0], rotation);
				f[1] = this.walze[1].rotateCounterClockwise(f[1], rotation);
				// f[2] = this.walze[2].rotateCounterClockwise(f[2],rotation);
				// f[2] = this.walze[2].rotateClockwise(f[2], rotation);
				// ZFWCYUTQEL

				entschlüsseln = entschlüsseln + f[0];

			} else {
				// Walze 2 rotirert um n gegen den Uhrzeigersinn und Walze 3
				// rotiert um n --> auf Walze 3 Verschlüsselung ablesbar

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
