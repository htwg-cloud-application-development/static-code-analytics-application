package Ü05;
import java.io.*;
import java.util.Scanner;

public class Aufgabe_40	{
	
	public static void main(String[] args) throws FileNotFoundException {
		

	Scanner s = new Scanner(System.in);
	int x = 0;
	
	System.out.println("Geben Sie einen Buchstaben ein");
	char buchstabe = s.next().charAt(0);
	
	File datei = new File("C://Aufgabe40//Faust.txt");
	Scanner b = new Scanner(datei,"ISO-8859-1");
    
	String zeile;	{
    while (b.hasNextLine()) {
    	zeile = b.nextLine();
    	for (int i = 0; i < zeile.length(); i++) {
    	if (buchstabe == zeile.charAt(i)) {
    		x++;
    	}
    }
    }
	}
    System.out.println("Der Buchstabe " + buchstabe + " ist " + x + " mal vorhanden.");
	}
}
