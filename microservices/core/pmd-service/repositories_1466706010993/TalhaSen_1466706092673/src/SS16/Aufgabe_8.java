package SS16;
import java.util.Scanner;

public class Aufgabe_8 {

	public static void main(String[] args) {
		System.out.println("Hallo Semih" );
		
	Scanner fürName = new Scanner(System.in);// Scanner für Namensgebung
	
	String vorname; 
	String nachname;
	
	System.out.println("Eingabe deines Vornamens:");
	vorname = fürName.nextLine(); // Abfrage Vorname - Eingabe Vorname
	System.out.println("Eingabe deines Nachnamens:");
	nachname = fürName.nextLine(); // Abfrage Nachname - Eingabe Nachname 
		
		
	System.out.println(vorname +" "+ nachname); // Ausgabe Vorname und Nachname 
	System.out.println(nachname +" "+ vorname); // Ausgabe Nachname und Vorname
	
	
	}

}
