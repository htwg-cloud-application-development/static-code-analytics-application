import java.util.Scanner;

public class Aufgabe19 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		double Text;
		double Wert;
		double usdEU=1;
		double euUSD=2;
		double USD= 1.1284;
		double EUR =0.8862;
		
		Scanner Scanner= new Scanner(System.in);
		
		System.out.println("Geben Sie für (USD-EUR)1 ein und für (EUR-USD)2:");
		
		Text=Scanner.nextDouble();
		
		System.out.println("Geben Sie den Betrag ein:");
		
		Wert=Scanner.nextDouble();
		
		if(Text==1){
		usdEU=Wert/USD;
		
		System.out.println("Ihr Betrag lautet:    "+usdEU);}
		
		if (Text==2) {euUSD=EUR*Wert;
		
		System.out.println("Ihr Betrag lautet  :"+euUSD);}
		
		
		
	}
	

}
