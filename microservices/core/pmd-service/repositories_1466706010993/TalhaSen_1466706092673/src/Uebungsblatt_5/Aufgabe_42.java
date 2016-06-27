package Uebungsblatt_5;

public class Aufgabe_42 {

	public static void main(String[] args) {
		System.out.println("Bachelorgesamtnote");
		System.out.println("");
		String[] module = { "MAWI1", "BWL", "REWE", "WIN", "PROG", "HASY", "MAWI2", "ALGO", "BETR", "WAHRS1", "THEOI",
				"DATENB", "GESCH", "SWT1", "WAHRS2", "RECH", "SWT2", "BETRSY", "PRAXIS", "SPC", "ITPM", "TP", "SWA",
				"SWQ", "WEBT", "VETS", "E-BUIS", "WAHLP" };

		int[] e = { 6, 7, 8, 8, 8, 5, 6, 6, 6, 5, 5, 8, 9, 6, 5, 3, 5, 8, 30, 7, 5, 6, 6, 3, 6, 6, 6, 9 };

		double mark;
		double a;
		double noteg;
		double gesnote = 0;
		double noteZ;
		double gesnoteZ = 0;

		for (int i = 0; i < module.length; i++) {
			double random = Math.random() * (4 - 1) + 1;
			a = ((int) (random * 10));
			mark = a / 10;
			System.out.println("Note: " + mark + "---> Fach: " + module[i]);

			noteg = mark * e[i] / 198;
			gesnote = gesnote + noteg;

			if (i <= 10) {
				noteZ = mark * e[i] / 67;
				gesnoteZ = gesnoteZ + noteZ;
			}
		}

		double bzwnote = ((int) (gesnoteZ * 10));
		double bznote = bzwnote / 10;

		System.out.println("");
		double bnote = ((int) (gesnote * 10));
		double bgesnote = bnote / 10;

		System.out.println("Die Zwischenprüfungsnote ist: " + bznote);
		System.out.println("Die Bachelorgesamtnote ist: " + bgesnote);
	}



	}


