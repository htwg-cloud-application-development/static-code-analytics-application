package Übungsblatt7;

import java.util.Scanner;

public class Enigma {
	
	private Walze[] walze = new Walze[3]; //3 Walzen
	private static String key;
	static int w1,w2,w3;
	static String text;
	static String text1;
	
	
	public static void main(String[] args) {
		
		Scanner s = new Scanner(System.in);
		
		System.out.println("Walzen zur Auswahl:\t50, 51, 60, 61, 70, 71");
		System.out.print("Walze 1 auswählen: ");
		w1 = Integer.parseInt(s.nextLine());
		System.out.print("Walze 2 auswählen: ");
		w2 = Integer.parseInt(s.nextLine());
		System.out.print("Walze 3 auswählen: ");
		w3 = Integer.parseInt(s.nextLine());
		
		System.out.print("key eingeben: ");
		key = s.nextLine().toUpperCase();
		
		System.out.print("Text zum verschlüsseln: ");
		text = s.nextLine().toUpperCase();
		
		System.out.println("Text zum entschlüsseln: ");
		text1 = s.nextLine().toUpperCase();

		Enigma a = new Enigma(w1,w2,w3,key);
		System.out.println(a.encrypt(text));
		
		Enigma b = new Enigma(w1,w2,w3,key);
		System.out.println(b.decrypt(text1));
		
//		Enigma c = new Enigma(71,51,61,"BIT");
//		System.out.println(c.decrypt("SMFAXRIKKG"));	
		
		
	}
	
	
	public Enigma(int wn1, int wn2, int wn3, String key) {
		
		walze[0] = new Walze(wn1);
		walze[1] = new Walze(wn2);
		walze[2] = new Walze(wn3);
		this.key = key;

	}

	public String encrypt(String plaintext){
		
		StringBuilder cipher = new StringBuilder(); //Constructs a string builder with no characters in it 
													//and an initial capacity of 16 characters.
		int distance;
		char letter; 
		boolean turn = true; //abwechselnde Verwendung Walze1 und Walze2
		
		for (int i = 0; i < plaintext.length(); i++) {
			
			if(turn){
				distance = walze[0].countClockwiseRotations(key.charAt(0), plaintext.charAt(i)); //Walze1: Uhzeigersinn, Distanz zwischen ersten Zeichen key-Wort bis aktuelles Zeichen text
				letter = walze[2].rotateClockwise(key.charAt(2), distance); 				//Walze3: Uhzeigersinn start drittes Zeichen key-Wort,Anzahl der rotierten positionen  
				turn = false;
			}
			
			else{
				distance = walze[1].countClockwiseRotations(key.charAt(1), plaintext.charAt(i));	//Walze2: gegen den Uhrzeigersinn, Distanz zwischen zweiten Zeichen key-Wort bis aktuelles Zeichen text  
				letter = walze[2].rotateCounterClockwise(key.charAt(2), distance);		
				turn = true;
		}
			
			cipher.append(letter); //Zeichen nach und nach in den String Builder
				
	}
		String cipher1 = cipher.toString(); //StringBuilder in String umwandeln
		return cipher1;
}
	public String decrypt(String ciphertext){
		
		StringBuilder decipher = new StringBuilder();
		
		int distance;
		char letter;
		boolean turn = true;
		
		for (int i = 0; i < ciphertext.length(); i++) {
			
			distance = walze[2].countClockwiseRotations(key.charAt(2), ciphertext.charAt(i));
			
			if(turn){
				letter = walze[0].rotateClockwise(key.charAt(0), distance);
				turn = false;
			}
				else{
					letter = walze[1].rotateCounterClockwise(key.charAt(1), distance);
					turn = true;
				}
			
			decipher.append(letter);
				
			}
		String decipher1 = decipher.toString();
		return decipher1;
		
		
		}
	}

