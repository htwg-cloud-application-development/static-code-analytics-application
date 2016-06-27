package Uebungsblatt_3;
import java.util.Scanner;

public class Aufgabe_20_2 {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		System.out.println("Geben Sie eine Gleitkommazahl ein: ");
		
		double y = s.nextDouble();
		double x = (y*100)%100;
		
		if (y >= 0 && y < 20) {

			String[] Einer = { "null", "eins", "zwei", "drei", "vier", "fünf", "sechs", "sieben", "acht", "neun","zehn", "elf", "zwÃ¶lf", "dreizehn", "vierzehn", "fÃ¼nfzehn",
					"sechszehn", "siebzehn", "achtzehn", "neunzehn" };
			String a = Einer[(int)y];
			
			System.out.print(a);
	
		} else if (y % 10 == 0) {

			String[] Zehner = { "", "zehn", "zwanzig", "dreißig", "vierzig", "fünfzig", "sechzig", "siebzig", "achtzig", "neunzig" };
			String c = Zehner[(int)y / 10];
			
			System.out.println(c);
			
		}	else if ((int)y > 19 && y % 10 != 0) {
			
					String[] Einer = { "null", "eins", "zwei", "drei", "vier", "fünf", "sechs", "sieben", "acht", "neun", "zehn", "elf", "zwÃ¶lf", "dreizehn", "vierzehn", "fÃ¼nfzehn",
							"sechszehn", "siebzehn", "achtzehn", "neunzehn"};
					String a = Einer[(int)y % 10];
			
					String[] Zehner = { "", "zehn", "zwanzig", "dreißig", "vierzig", "fünfzig", "sechzig", "siebzig", "achtzig", "neunzig" };
					String b = Zehner[(int)y / 10];
				
					System.out.print(a + "und" + b);
			
		}  if  (x>0) {
			System.out.print("komma");
			for(int i =1; i <= 3; i++){
			
			String[] Komma = {"null","eins","zwei", "drei", "vier", "fünf", "sechs", "sieben", "acht", "neun"};
			String d = Komma [(int) ( y*Math.pow(10,i) )%10];
			
			System.out.print(d);
			
			
		}
	}
}
	
}