package Uebungsblatt_5;
import java.util.Scanner;
import java.io.*;

public class Aufgabe_40 {

	public static void main(String[] args) throws FileNotFoundException {
		// Scanner für Text einlesen
		Scanner scanner = new Scanner(System.in);
		// Verweis auf den Text
		java.io.File aFile = new java.io.File("C://Users//Semih//Desktop//Faust.txt");
		// Text wird eingelsen
		Scanner sc = new Scanner(aFile, "ISO-8859-1");
		// In diese Line wird Text eingespeichert.In aLine ist ganze Text drin.
		String aLine = "";
		System.out.println("Bitte geben sie einen Buchstaben ein!");
		// Scanner für Buchstabe. Buchstabe einlesen.
		String a = scanner.nextLine();
		// erste Buchstabe
		char b = a.charAt(0);
		int aNumber = 0;
		
		while (sc.hasNextLine()) { //Scanner geht ganze Text durch bis keine neue Line.
			aLine = sc.nextLine(); 
			for (int i = 0; i<aLine.length();i++){
				if (b==aLine.charAt(i)){ //aNumber (counter). Für jedes true erhöht sich Counter.
					aNumber++;	
				}
			}
		}			
		System.out.println(aNumber);
	}
}
//
//while (s.hasNextLine()){
//	String word = s.nextLine();
//	
//	char[] text = new char [word.length()];
//	
//	for (int i = 0;i<text.length;i++){
//		text[i] = word.charAt(i);
//	}


