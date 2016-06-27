package Uebungsblatt_2;

public class Aufgabe_7new {

	public static void main(String[] args) {
	
		double n = 3;
		
		System.out.println("Binaer"); // Binärdarstellung 
		for(int i = 0;i <= n; i++ ){
		double x = Math.pow(2, i);
		System.out.println("2^" +i +"=" +x);}
		
		System.out.println("Oktal");// Oktaldarstellung 
		for (int i = 0;i <= n; i++){
		double x = Math.pow(8, i);
		System.out.println("8^" +i +"=" +x);}
		
		System.out.println("Dezimal"); //Dezimaldarstellung 
		for (int i = 0;i <= n; i++){
		double x = Math.pow(10, i);
		System.out.println("10^" +i +"=" +x);}
		
		System.out.println("Hexa"); //Hexadezimaldarstellung
		for (int i = 0;i <= n; i++){
		double x = Math.pow(16, i);
		System.out.println("16^" +i +"=" +x);}
		
		System.out.println("Sexigesimal");//Sexigesimaldarstellung
		for (int i = 0;i <=n; i++){
		double x = Math.pow(60, i);
		System.out.println("60^" +i +"=" +x);}
			
		}
			
		}
	


