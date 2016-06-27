import java.io.File;
public class Aufgabe41 {

	public static void main(String[] args) throws IOException {
		
		File datei = new File("OpenGeoDB-plz-ort-de.csv");
		String text = FileUtils.readFileToString(datei);
		//int plz = s.nextInt();
		Scanner s = new Scanner(System.in);
		String suchPlz = s.next();
		
		String[] arrText = text.split("\n");
		
		for (int i = 0; i < arrText.length; i++) {
			if(arrText[i].contains(suchPlz)==true){
				String ausgabe = arrText[i].substring(5, arrText[i].length());
				System.out.println(ausgabe);
			}
		}
		
	}
}