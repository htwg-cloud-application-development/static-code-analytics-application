
import java.util.Scanner;


public class Aufgabe32 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		String[][] chess = new String[8][8];

		// white
		String KingWhite = "KingW";
		String QueenWhite = "QueenW";
		String Tower1White = "TowerW";
		String Tower2White = "TowerW";
		String Knight1White = "KnightW";
		String Knight2White = "KnightW";
		String Runner1White = "RunnerW";
		String Runner2White = "RunnerW";
		String Farmer1White = "FarmerW";
		String Farmer2White = "FarmerW";
		String Farmer3White = "FarmerW";
		String Farmer4White = "FarmerW";
		String Farmer5White = "FarmerW";
		String Farmer6White = "FarmerW";
		String Farmer7White = "FarmerW";
		String Farmer8White = "FarmerW";

		// black
		String KingBlack = "KingB";
		String QueenBlack = "QueenB";
		String Tower1Black = "TowerB";
		String Tower2Black = "TowerB";
		String Knight1Black = "KnightB";
		String Knight2Black = "KnightB";
		String Runner1Black = "RunnerB";
		String Runner2Black = "RunnerB";
		String Farmer1Black = "FarmerB";
		String Farmer2Black = "FarmerB";
		String Farmer3Black = "FarmerB";
		String Farmer4Black = "FarmerB";
		String Farmer5Black = "FarmerB";
		String Farmer6Black = "FarmerB";
		String Farmer7Black = "FarmerB";
		String Farmer8Black = "FarmerB";

		// DEUTSCH

		// weiss
		String KönigWhite = "KönigW";
		String DameWhite = "DameW";
		String Turm1White = "TurmW";
		String Turm2White = "TurmW";
		String Springer1White = "SpringerW";
		String Springer2White = "SpringerW";
		String Läufer1White = "LäuferW";
		String Läufer2White = "LäuferW";
		String Bauer1White = "BauerW";
		String Bauer2White = "BauerW";
		String Bauer3White = "BauerW";
		String Bauer4White = "BauerW";
		String Bauer5White = "BauerW";
		String Bauer6White = "BauerW";
		String Bauer7White = "BauerW";
		String Bauer8White = "BauerW";

		// schwarz
		String KönigBlack = "KönigS";
		String DameBlack = "DameS";
		String Turm1Black = "TurmS";
		String Turm2Black = "TurmS";
		String Springer1Black = "SpringerS";
		String Springer2Black = "SpringerS";
		String Läufer1Black = "LäuferS";
		String Läufer2Black = "LäuferS";
		String Bauer1Black = "BauerS";
		String Bauer2Black = "BauerS";
		String Bauer3Black = "BauerS";
		String Bauer4Black = "BauerS";
		String Bauer5Black = "BauerS";
		String Bauer6Black = "BauerS";
		String Bauer7Black = "BauerS";
		String Bauer8Black = "BauerS";

		for (int x = 0; x > chess.length; x++) {
			for (int y = 0; y > chess[x].length; y++) {
				chess[x][y] = "<>";
			}
		}

		int enLang;
		do {
			System.out.print("Please choose your language (English=1 / Deutsch=2): ");
			enLang = scanner.nextInt();
		} while (enLang < 1 | enLang > 2);

		String[] chessVert = { "A", "B", "C", "D", "E", "F", "G", "H" };
		String[] chessHorz = { "1", "2", "3", "4", "5", "6", "7", "8" };

		if (enLang == 1) {

			// black
			chess[0][0] = KingBlack;
			chess[0][1] = QueenBlack;
			chess[0][2] = Tower1Black;
			chess[0][3] = Tower2Black;
			chess[0][4] = Knight1Black;
			chess[0][5] = Knight2Black;
			chess[0][6] = Runner2Black;
			chess[0][7] = Runner1Black;
			chess[1][0] = Farmer1Black;
			chess[1][1] = Farmer2Black;
			chess[1][2] = Farmer3Black;
			chess[1][3] = Farmer4Black;
			chess[1][4] = Farmer5Black;
			chess[1][5] = Farmer6Black;
			chess[1][6] = Farmer7Black;
			chess[1][7] = Farmer8Black;
			// white
			chess[6][0] = KingWhite;
			chess[6][1] = QueenWhite;
			chess[6][2] = Tower1White;
			chess[6][3] = Tower2White;
			chess[6][4] = Knight1White;
			chess[6][5] = Knight2White;
			chess[6][6] = Runner2White;
			chess[6][7] = Runner1White;
			chess[7][0] = Farmer1White;
			chess[7][1] = Farmer2White;
			chess[7][2] = Farmer3White;
			chess[7][3] = Farmer4White;
			chess[7][4] = Farmer5White;
			chess[7][5] = Farmer6White;
			chess[7][6] = Farmer7White;
			chess[7][7] = Farmer8White;

			for (int x = 0; x < chess.length; x++) {
				for (int y = 0; y < chess[x].length; y++) {
					if (chess[x][y] != " <> ") {
						System.out.println(chess[x][y] + " on " + chessVert[y] + chessHorz[x]);

					}
				}
			}
		} else {

			chess[0][0] = KönigBlack;
			chess[0][1] = DameBlack;
			chess[0][2] = Turm1Black;
			chess[0][3] = Turm2Black;
			chess[0][4] = Springer1Black;
			chess[0][5] = Springer2Black;
			chess[0][6] = Läufer2Black;
			chess[0][7] = Läufer1Black;
			chess[1][0] = Läufer1Black;
			chess[1][1] = Bauer2Black;
			chess[1][2] = Bauer3Black;
			chess[1][3] = Bauer4Black;
			chess[1][4] = Bauer5Black;
			chess[1][5] = Bauer6Black;
			chess[1][6] = Bauer7Black;
			chess[1][7] = Bauer8Black;
			// white
			chess[6][0] = KönigWhite;
			chess[6][1] = DameWhite;
			chess[6][2] = Turm1White;
			chess[6][3] = Turm2White;
			chess[6][4] = Springer1White;
			chess[6][5] = Springer2White;
			chess[6][6] = Läufer2White;
			chess[6][7] = Läufer1White;
			chess[7][0] = Bauer1White;
			chess[7][1] = Bauer2White;
			chess[7][2] = Bauer3White;
			chess[7][3] = Bauer4White;
			chess[7][4] = Bauer5White;
			chess[7][5] = Bauer6White;
			chess[7][6] = Bauer7White;
			chess[7][7] = Bauer8White;

			
			for (int x = 0; x < chess.length; x++) {
				for (int y = 0; y < chess[x].length; y++) {
					if (chess[x][y] != " <> ") {
						System.out.println(chess[x][y] + " auf " + chessVert[y] + chessHorz[x]);
					
					}

				}

			}
		}
		scanner.close();
	}
}
