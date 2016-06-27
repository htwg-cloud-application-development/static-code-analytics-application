import java.io.BufferedReader;
import java.io.*;
import java.util.Scanner;

public class Postleitzahl{
	
	int pcode;
	public void postcodeToCity(String plz) throws IOException {

		Scanner sc = new Scanner(System.in);
		System.out.println("geben sie die gesuchte Postleitzahl ein: ");
		
		plz = sc.next();
		String line;
		
		FileReader fileR = new FileReader("C:\\Wichtiges\\Studium\\Eclipse\\workspace\\Übung5\\OpenGeoDB-plz-ort-de.csv");
		BufferedReader br = new BufferedReader(fileR);
	
		while( (line = br.readLine()) != null){
			if( line.contains(plz)){
				System.out.println(line);
			}
		}
		
		br.close();
}
	
	public void setPostcode(int pcode) throws IOException {
		this.pcode = pcode;
		String line;
		String plz2 = "" + pcode;
		FileReader fileR = new FileReader("C:\\Wichtiges\\Studium\\Eclipse\\workspace\\Übung5\\OpenGeoDB-plz-ort-de.csv");
		BufferedReader br = new BufferedReader(fileR);
	
		while( (line = br.readLine()) != null){
			if( line.contains(plz2)){
				System.out.println(line);
			}
		}
		
		br.close();
		
	}
}

