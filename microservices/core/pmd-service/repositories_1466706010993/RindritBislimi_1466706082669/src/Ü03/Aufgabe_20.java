package Ü03;

import java.util.Scanner;

public class Aufgabe_20 {

	public static void main (String[] args) {
		
		Scanner s = new Scanner (System.in);
		
		short x;
		
		System.out.println("Geben Sie eine Zahl ein");
		x = s.nextShort();	
		
		
		if (x == 0) {
			System.out.print("null");	}
		if (x == 1) {
			System.out.print("eins"); 
			System.exit(0);	}
		if (x == 2) {
			System.out.print("zwei"); 
			System.exit(0);	}
		if (x == 3) {
			System.out.print("drei");
			System.exit(0); }
		if (x == 4) {
			System.out.print("vier"); 
			System.exit(0);	}
		if (x == 5) {
			System.out.print("fünf");	
			System.exit(0); }
		if (x == 6) {
			System.out.print("sechs"); 
			System.exit(0);	}
		if (x == 7) {
			System.out.print("sieben");
			System.exit(0);	}
		if (x == 8) {
			System.out.print("acht");
			System.exit(0); 	}
		if (x == 9) {
			System.out.print("neun");
			System.exit(0);}
		if (x == 11) {
			System.out.print("elf");
			System.exit(0);}
		if (x == 12) {
			System.out.print("zwölf");	
			System.exit(0);}
		if (x == 13) {
			System.out.print("dreizehn");
			System.exit(0);}
		if (x == 14) {
			System.out.print("vierzehn");
			System.exit(0);}
		if (x == 15) {
			System.out.print("fünfzehn"); 
			System.exit(0);}
		if (x == 16) {
			System.out.print("sechszehn");
			System.exit(0);}
		if (x == 17) {
			System.out.print("siebzehn");	
			System.exit(0);}
		if (x == 18) {
			System.out.print("achtzehn");
			System.exit(0);}
		if (x == 19) {
			System.out.print("neunzehn");	
			System.exit(0);}
		
		
		switch (x/100)	{
		case 1:
		System.out.print("hundert");
		if (x % 100 == 1){
			System.out.print("eins");
		System.exit(0);}
		if (x % 100 == 11) {
			System.out.print("elf");
			System.exit(0); }
		if (x % 100 == 12) {
			System.out.print("zwölf");
			System.exit(0);}
		if (x % 100 == 17) {
			System.out.print("siebzehn");
			System.exit(0); }
			break;
		case 2:
			System.out.print("zweihundert");
			if (x % 100 == 1){
				System.out.print("eins");
			System.exit(0);}
			if (x % 100 == 11) {
				System.out.print("elf");
				System.exit(0); }
			if (x % 100 == 12) {
				System.out.print("zwölf");
				System.exit(0);}
			if (x % 100 == 17) {
				System.out.print("siebzehn");
				System.exit(0); }
				break;	
		case 3:
			System.out.print("dreihundert");
			if (x % 100 == 1){
				System.out.print("eins");
			System.exit(0);}
			if (x % 100 == 11) {
				System.out.print("elf");
				System.exit(0); }
			if (x % 100 == 12) {
				System.out.print("zwölf");
				System.exit(0);}
			if (x % 100 == 17) {
				System.out.print("siebzehn");
				System.exit(0); }
				break;		
		case 4:
			System.out.print("vierhundert");
			if (x % 100 == 1){
				System.out.print("eins");
			System.exit(0);}
			if (x % 100 == 11) {
				System.out.print("elf");
				System.exit(0); }
			if (x % 100 == 12) {
				System.out.print("zwölf");
				System.exit(0);}
			if (x % 100 == 17) {
				System.out.print("siebzehn");
				System.exit(0); }
				break;		
		case 5:
			System.out.print("fünfhundert");
			if (x % 100 == 1){
				System.out.print("eins");
			System.exit(0);}
			if (x % 100 == 11) {
				System.out.print("elf");
				System.exit(0); }
			if (x % 100 == 12) {
				System.out.print("zwölf");
				System.exit(0);}
			if (x % 100 == 17) {
				System.out.print("siebzehn");
				System.exit(0); }
				break;		
		case 6:
			System.out.print("sechshundert");
			if (x % 100 == 1){
				System.out.print("eins");
			System.exit(0);}
			if (x % 100 == 11) {
				System.out.print("elf");
				System.exit(0); }
			if (x % 100 == 12) {
				System.out.print("zwölf");
				System.exit(0);}
			if (x % 100 == 17) {
				System.out.print("siebzehn");
				System.exit(0); }
				break;
		case 7:
			System.out.print("siebenhundert");
			if (x % 100 == 1){
				System.out.print("eins");
			System.exit(0);}
			if (x % 100 == 11) {
				System.out.print("elf");
				System.exit(0); }
			if (x % 100 == 12) {
				System.out.print("zwölf");
				System.exit(0);}
			if (x % 100 == 17) {
				System.out.print("siebzehn");
				System.exit(0); }
				break;
		case 8:
			System.out.print("achhundert");
			if (x % 100 == 1){
				System.out.print("eins");
			System.exit(0);}
			if (x % 100 == 11) {
				System.out.print("elf");
				System.exit(0); }
			if (x % 100 == 12) {
				System.out.print("zwölf");
				System.exit(0);}
			if (x % 100 == 17) {
				System.out.print("siebzehn");
				System.exit(0); }
				break;
		case 9:
			System.out.print("neunhundert");
			if (x % 100 == 1){
				System.out.print("eins");
			System.exit(0);}
			if (x % 100 == 11) {
				System.out.print("elf");
				System.exit(0); }
			if (x % 100 == 12) {
				System.out.print("zwölf");
				System.exit(0);}
			if (x % 100 == 17) {
				System.out.print("siebzehn");
				System.exit(0); }
				break;		
		}		
		
		int z = x % 10;
		

		if (z == 1) {
			System.out.print("ein");	}
		if (z == 2) {
			System.out.print("zwei");	}
		if (z == 3) {
			System.out.print("drei");	}
		if (z == 4) {
			System.out.print("vier");	}
		if (z == 5) {
			System.out.print("fünf");	}
		if (z == 6) {
			System.out.print("sechs"); }
		if (z == 7) {
			System.out.print("sieben"); }
		if (z == 8) {
			System.out.print("acht");	}
		if (z == 9) {
			System.out.print("neun");	}
		
		
		int y = x % 100;
		switch (y / 10) {
		case 1:
			System.out.print("zehn");
				break;
		case 2:
			if (x % 10 > 0) {
				System.out.print("und"); 	}
			System.out.print("zwanzig");
				break;	
		case 3:
			if (x % 10 > 0) {
				System.out.print("und"); 	}
			System.out.print("dreißig");
				break;
		case 4:
			if (x % 10 > 0) {
				System.out.print("und"); 	}
			System.out.print("vierzig");
				break;
		case 5:
			if (x % 10 > 0) {
				System.out.print("und"); 	}
			System.out.print("fünfzig");
				break;
		case 6:
			if (x % 10 > 0) {
				System.out.print("und"); 	}
			System.out.print("sechszig");
				break;		
		case 7:
			if (x % 10 > 0) {
				System.out.print("und"); 	}
			System.out.print("siebzig");
				break;
		case 8:
			if (x % 10 > 0) {
				System.out.print("und"); 	}
			System.out.print("achtzig");
				break;
		case 9:
			if (x % 10 > 0) {
				System.out.print("und"); 	}
			System.out.print("neunzig");
				break;	
	}
	}
}
