
import java.util.Scanner; //scanner muss klasse aktivieren
public class Variablen {

	
public static void main(String[] args) {

	
Scanner fürName = new Scanner(System.in); //scanner für namensgebung
	
	
byte winAnzahl = 35;
short studierendeHTWG = 5000;
float einwohnerEU = 503000000;  // verwendung von float weil für Aufgabe 6 relevant.
long weltbevölkerung = 7284283000L;
byte anzahlSemster = 8;
char buchstabeNachname = 'g';
float noteKlausur = (float) 2.9;
boolean schaltjahr = true;
String vorname;
String nachname;


System.out.println("Hallo Sebastian");




System.out.println("eingabe von vorname");
vorname = fürName.nextLine();
System.out.println("eingabe von nachname");
nachname = fürName.nextLine();
System.out.println(vorname +" "+ nachname);

System.out.println(nachname +" "+vorname);



System.out.println((einwohnerEU*100)/weltbevölkerung + "%");




;

}
}
