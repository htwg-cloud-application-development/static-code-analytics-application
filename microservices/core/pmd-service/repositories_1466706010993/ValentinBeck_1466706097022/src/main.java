import java.io.*;
public class main{

	public static void main(String[] args)throws IOException {
		
		BufferedReader input = new BufferedReader (new InputStreamReader(System.in));
		
		System.out.println("Walzennummer eingeben:");
		int [] input_walze = new int[3]; 
		for(int i=1;i<4;i++){
			System.out.print(i+"Walze: ");
			input_walze[i-1]=Integer.parseInt(input.readLine());
		}
		
		System.out.print("Schlüssel eingeben: ");
		String schluessel = input.readLine();
		schluessel=schluessel.toUpperCase();
				
		System.out.print("0 = entschlüsseln | 1 = verschlüsseln: ");
		int auswahl = Integer.parseInt(input.readLine());
		
		Enigma newenigma = new Enigma(input_walze[0],input_walze[1],input_walze[2],schluessel);
		
		String txt_text="";
		
		switch(auswahl){
			case 0:  
				System.out.print("Zu entschlüsselten Text ein: ");
				txt_text = input.readLine();
				txt_text = txt_text.toUpperCase();
				System.out.print(newenigma.decrypt(txt_text));break;
			case 1:  
				System.out.print("Zu verschlüsselten Text ein: ");
				txt_text = input.readLine();
				txt_text = txt_text.toUpperCase();
				System.out.println(newenigma.encryp(txt_text));break;
		}		
	}
}