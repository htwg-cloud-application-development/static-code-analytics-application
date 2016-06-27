
public class Aufgabe_4 
{
	public static void main (String[]args){
	byte winEprStudents = 45; //WINEPR Studenten
	short htwgStudents  = 4500; // Studierende an der HTWG
	float euPopulation    = 742500000f; // Bevölkerung der EU 
	long earthPopulation = 7390000000l; // Weltbevölkerung
	byte semseterToStudy = 7; // Anzahl der Semester die ich noch studiere
	char firstLetterSurname = 's'; // Anfangsbuchstabe meines Nachnamen
	float averageExamMark = 2.5f; // Durchschnittsnote der Klausur
	boolean leapYear = true; // Das laufende Jahr ist ein Schaltjahr 
	
	double percentagePartEu = (euPopulation/earthPopulation)*100;
			System.out.println("Anteil der EU-Bewohner an der Weltbevölkerung in %:"+" "+percentagePartEu);
} 
}