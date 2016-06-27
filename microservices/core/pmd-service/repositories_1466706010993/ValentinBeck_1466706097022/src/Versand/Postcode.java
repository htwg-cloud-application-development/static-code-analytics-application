package Versand;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;



public class Postcode extends Address{
	String line;
	String line2;
	String answer;

	public String ortsermittlung(String plz) throws IOException{
		FileReader fileR = new FileReader("C:\\Wichtiges\\Studium\\Eclipse\\workspace\\Übung5\\OpenGeoDB-plz-ort-de.csv");
		BufferedReader br = new BufferedReader(fileR);
		
		while( (line = br.readLine()) != null){
			if( line.contains(plz))
				answer = line;
			else {
				FileReader englishFileR = new FileReader("C:\\Wichtiges\\Studium\\Eclipse\\workspace\\Strandbar\\uk-postcode-database-csv.csv");
				BufferedReader englishBR = new BufferedReader(englishFileR);
				
				while((line2 = englishBR.readLine()) != null){
					if( line2.contains(plz))
						answer = line2;
					englishBR.close();
				}
			}
		}
		br.close();
		return answer;	
	}
	
	
	
}
