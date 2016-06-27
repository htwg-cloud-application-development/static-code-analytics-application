package Uebungsblatt_3;
import java.util.Scanner;

public class Aufgabe_11 {

	public static void main(String[] args) {
	
		Scanner s = new Scanner (System.in); // Scanner für Zahleingabe
		
	int a;
	int b;
	int c;
	
		System.out.println("Eingabe der ersten Zahl");
		a = s.nextInt();
		System.out.println("Eingabe der zweiten Zahl");
		b = s.nextInt();
		System.out.println("Eingabe der dritten Zahl");
		c = s.nextInt();
		
		if ((a>b) && (b>c)){
		System.out.println(c+ "ist kleiner als" +b+ "ist kleiner als" +a);
		}
		else if ((a>c)&&(c>b)){
		System.out.println(b+ "ist kleiner als" +c+ "ist kleiner als" +a);
		}
		else if ((b>a)&&(a>c)){
		System.out.println(c+ "ist kleiner als" +a+ "ist kleiner als" +b);
		}
		else if ((b>c)&&(c>a)){
		System.out.println(a+ "ist kleiner als" +c+ "ist kleiner als" +b);
		}
		else if ((c>a)&&(a>b)){
		System.out.println(b+ "ist kleiner als" +a+ "ist kleiner als" +c);
		}
		else if ((c>b)&&(b>a)){
		System.out.println(a+ "ist kleiner als"+b+"ist kleiner als" +c);
		}
		s.close();
	}

}
