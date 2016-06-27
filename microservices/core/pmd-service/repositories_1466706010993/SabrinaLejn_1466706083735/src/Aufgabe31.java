import java.util.Arrays;

public class Aufgabe31 {
	public static void main(String[] args) {
		
		String[][] chess=new String [8][8];
		
		//white
		String KingWhite= "KGW ";
		String QueenWhite= "QUW ";
		String Tower1White= "TWW ";
		String Tower2White= "TWW ";
		String Knight1White= "KNW ";
		String Knight2White= "KNW ";
		String Runner1White= "RNW ";
		String Runner2White= "RNW ";
		String Farmer1White= "FMW ";
		String Farmer2White= "FMW ";
		String Farmer3White= "FMW ";
		String Farmer4White= "FMW ";
		String Farmer5White= "FMW ";
		String Farmer6White= "FMW ";
		String Farmer7White= "FMW ";
		String Farmer8White= "FMW ";
		
		//black
		String KingBlack= "KGB ";					//King
		String QueenBlack= "QUB ";					//Queen	
		String Tower1Black= "TWB ";					//Tower
		String Tower2Black= "TWB ";
		String Knight1Black= "KNB ";
		String Knight2Black= "KNB ";				//Knight
		String Runner1Black= "RNB ";				//Runner
		String Runner2Black= "RNB ";
		String Farmer1Black= "FMB ";				//Farmer
		String Farmer2Black= "FMB ";
		String Farmer3Black= "FMB ";
		String Farmer4Black= "FMB ";
		String Farmer5Black= "FMB ";
		String Farmer6Black= "FMB ";
		String Farmer7Black= "FMB ";
		String Farmer8Black= "FMB ";
		
		
		for (int x=0; x>chess.length; x++){
			for (int y=0; y>chess[x].length; y++){
				chess[x][y]="<>";
			}
		}
		//black
		chess [0][0]=KingBlack;
		chess [0][1]=QueenBlack;
		chess [0][2]=Tower1Black;
		chess [0][3]=Tower2Black;
		chess [0][4]=Knight1Black;
		chess [0][5]=Knight2Black;
		chess [0][6]=Runner2Black;
		chess [0][7]=Runner1Black;
		chess [1][0]=Farmer1Black;
		chess [1][1]=Farmer2Black;
		chess [1][2]=Farmer3Black;
		chess [1][3]=Farmer4Black;
		chess [1][4]=Farmer5Black;
		chess [1][5]=Farmer6Black;
		chess [1][6]=Farmer7Black;
		chess [1][7]=Farmer8Black;
		//white
		chess [6][0]=KingWhite;
		chess [6][1]=QueenWhite;
		chess [6][2]=Tower1White;
		chess [6][3]=Tower2White;
		chess [6][4]=Knight1White;
		chess [6][5]=Knight2White;
		chess [6][6]=Runner2White;
		chess [6][7]=Runner1White;
		chess [7][0]=Farmer1White;
		chess [7][1]=Farmer2White;
		chess [7][2]=Farmer3White;
		chess [7][3]=Farmer4White;
		chess [7][4]=Farmer5White;
		chess [7][5]=Farmer6White;
		chess [7][6]=Farmer7White;
		chess [7][7]=Farmer8White;
		
		//System.out.print(chess[1][6]);
		
		
		for (int x=chess.length;x>0; x--){
			System.out.println(Arrays.toString(chess[x-1]));
		}
		
		}
	}

