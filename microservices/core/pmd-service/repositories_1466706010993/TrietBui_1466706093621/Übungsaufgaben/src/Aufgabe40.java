import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

public class Aufgabe40 {

	public static void main(String[] args) throws IOException {
		
		File datei = new File("faust.txt");
		String text = FileUtils.readFileToString(datei);
		Scanner s = new Scanner(System.in);
		System.out.println("Geben Sie einen Buchstaben zum Zählen ein: ");
		String buchstabe = s.next();
		int counter =0;
		
		for (int i = 0; i < text.length() ; i++){
			if (Character.toLowerCase(buchstabe.charAt(0)) == text.charAt(i) || Character.toUpperCase(buchstabe.charAt(0)) ==text.charAt(i)){
				counter++;
			}
			
		}
		System.out.println(counter);
		
		
		

	}
}
