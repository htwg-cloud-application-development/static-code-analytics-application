
import java.util.Scanner;
public class Übungsaufgaben2 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		
//Aufgabe 4		
byte numStudWinEpr;		//4.1. Wie viele Studierende WIN-EPR belegen
short numStudHtwg; 		//4.2. Wie viele Studierende an der HTWG eingeschrieben sind
int populationEu;		//4.3. Wie viele Menschen in der Europäischen Union leben
long populationWorld;	//4.4. Wie viele Menschen auf der Erde leben
byte planSemester;		//4.5. Wie viele Semester Sie studieren wollen
char firstLetterName;	//4.6. Mit welchem Buchstaben Ihr Nachname beginnt
float averageGrade;		//4.7. Welche Durchschnittsnote in der Klausur erreicht wurde
boolean currentLeapYear;//4.8. Ob das laufende Jahr ein Schaltjahr ist
	
//Aufgabe 5
numStudWinEpr = 40;		
numStudHtwg = 4812; 		
populationEu = 508190000;		
populationWorld = 7350000000l;	
planSemester = 4;		
firstLetterName = 'S';	
averageGrade = 2.5f;		
currentLeapYear = true;	

//Aufgabe 6
float propPopulationEuWorld = (float)populationEu/populationWorld *100;
System.out.println("Anteil EU-Bürger an Weltbevölkerung: " + propPopulationEuWorld + " %");

//Aufgabe 7
//7.1
System.out.println("Stellenwert1: " + Math.pow(2,0));
System.out.println("Stellenwert2: " + Math.pow(2,1));
System.out.println("Stellenwert3: " + Math.pow(2,3));
System.out.println("Stellenwert4: " + Math.pow(2,4));
//7.2
System.out.println("Stellenwert1: " + Math.pow(8,0));
System.out.println("Stellenwert2: " + Math.pow(8,1));
System.out.println("Stellenwert3: " + Math.pow(8,3));
System.out.println("Stellenwert4: " + Math.pow(8,4));
//7.3
System.out.println("Stellenwert1: " + Math.pow(10,0));
System.out.println("Stellenwert2: " + Math.pow(10,1));
System.out.println("Stellenwert3: " + Math.pow(10,3));
System.out.println("Stellenwert4: " + Math.pow(10,4));
//7.4
System.out.println("Stellenwert1: " + Math.pow(16,0));
System.out.println("Stellenwert2: " + Math.pow(16,1));
System.out.println("Stellenwert3: " + Math.pow(16,3));
System.out.println("Stellenwert4: " + Math.pow(16,4));
//7.5
System.out.println("Stellenwert1: " + Math.pow(60,0));
System.out.println("Stellenwert2: " + Math.pow(60,1));
System.out.println("Stellenwert3: " + Math.pow(60,3));
System.out.println("Stellenwert4: " + Math.pow(60,4));

//Aufgabe 8
//8.1
System.out.println("Name: " + "Sabrina Lejn");

//8.2
System.out.print("First Name: ");
String firstName = scanner.next();
System.out.println(firstName);    	

System.out.print("Second Name: ");
String secondName = scanner.next();    	  	
System.out.println(secondName);    	
 	

//8.3
System.out.println(firstName + " " + secondName);    	
scanner.close(); 
System.out.println(secondName + " " + firstName);    	
scanner.close();     	  	
} 	
    }