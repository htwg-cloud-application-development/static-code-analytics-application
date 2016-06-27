public class Walze {
	
	final private String Walz50 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	final private String Walz51 = "ADCBEHFGILJKMPNOQTRSUXVWZY";
	final private String Walz60 = "ACEDFHGIKJLNMOQPRTSUWVXZYB";
	final private String Walz61 = "AZXVTRPNDJHFLBYWUSQOMKIGEC";
	final private String Walz70 = "AZYXWVUTSRQPONMLKJIHGFEDCB";
	final private String Walz71 = "AEBCDFJGHIKOLMNPTQRSUYVWXZ";
	final private String Walz49 = "ABCDE";

	private int walzennummer;

	public Walze(int input){
		this.walzennummer=input;
	}
	
	
	public int countClockwiseRotations(char char_start, char char_ziel){
		String walze = getWalze(this.walzennummer);
		int start=0;
		int ziel=0;
		int count=0;
//Start und Ende
			for(int i=0;i<walze.length();i++){
				if(char_start == walze.charAt(i)){
					start=i;
				}
				if(char_ziel == walze.charAt(i)){
					ziel=i;
				}
			}
			
			if(start>ziel){
				count = walze.length()-(start-ziel);
			}else{
				count = ziel-start;
			}
		return count;
	}

	public int countClockwiseRotations2(char char_start, char char_ziel){
		String walze = getWalze(this.walzennummer);
		int start=0;
		int ziel=0;
		int count=0;
			//Start und Zielwerte finden und setzen
			for(int i=walze.length()-1;i>=0;i--){
				if(char_start == walze.charAt(i)){
					start=i;
				}
				if(char_ziel == walze.charAt(i)){
					ziel=i;
				}
			}
			
			//Count Auswerten
			if(start>ziel){
				count = start-ziel;
			}else{
				count = walze.length()-(ziel-start);
			}
		//Count zurückgeben
		return count;		
	}

	public char rotateClockwise(char char_start, int int_rotation){
		String walze = getWalze(this.walzennummer);
		int start=0;
		char buchstabe=' ';
		for(int i=0;i<walze.length();i++){
			if(char_start==walze.charAt(i)){
				start=i;
				break;
			}
		}
		int count = start+int_rotation;
		if(count>25){
			count=count-walze.length();
		}			
		return walze.charAt(count);
	}

	public char rotateClockwise2(char char_start, int int_rotation){
		String walze = getWalze(this.walzennummer);
	int start=0;
	char buchstabe=' ';
	for(int i=0;i<walze.length();i++){
		if(char_start==walze.charAt(i)){
			start=i;
			break;
		}
	}
	int count = start-int_rotation;
	if(count<0){
		count=count+walze.length();
	}			
	return walze.charAt(count);
		
	}
	
	public String getWalze(int nummer){
		switch(nummer){
			case 50: return Walz50;
			case 51: return Walz51;
			case 60: return Walz60;
			case 61: return Walz61;
			case 70: return Walz70;
			case 71: return Walz71;
			case 49: return Walz49;
			default: System.out.println("Keine Walze mit der Nummer "+nummer+"gefunden.");System.exit(1);
		}
		return "Fehler";
	}
	
}