import java.util.Scanner;

public class Aufgabe17 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		double Gewicht;
		double Größe;
		double Berechnung;
		double Größe1;
		
		Scanner Scanner = new Scanner (System.in);
		
		System.out.println("Geben Sie Ihr Gewicht ein:");
		
		Gewicht=Scanner.nextDouble();
		
		System.out.println("Geben Sie Ihre Größe ein:");
		
		Größe=Scanner.nextDouble();
		
		Größe1=Größe*Größe;
		
		Berechnung=Gewicht/Größe1;
		
		System.out.println("Ihr BMI beträgt:   "+Berechnung);
		
		if (Berechnung<18.5){
		System.out.println("Sie sind Untergewichtig");
		}
		if(Berechnung>=18.5 && Berechnung<25){
		System.out.println("Sie sind Normalgewichtig");
		}
		if (Berechnung>=25 && Berechnung<30){
		System.out.println("Übergewichtig");
		}
		if(Berechnung>=30 && Berechnung>40){
			System.out.println("Starkes Übergewicht");	
		}
		if (Berechnung>=40){
			System.out.println("Extreme Adiposita");
		}
	}
}
