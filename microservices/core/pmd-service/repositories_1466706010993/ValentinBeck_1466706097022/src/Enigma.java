
public class Enigma {
	private Walze[] array = new Walze[3];
	private String str_schlüssel;

	public Enigma(int w1, int w2, int w3, String schluessel){
		this.str_schlüssel = schluessel;
		this.array[0] = new Walze(w1);
		this.array[1] = new Walze(w2);
		this.array[2] = new Walze(w3);
	}
	
	public String encryp(String text){
		String str_ecryp="";
		char[] char_position = new char[3];
		
		for(int a = 0;a<this.str_schlüssel.length();a++){
			char_position[a]=this.str_schlüssel.charAt(a);
		}
		char buchstabe=' ';
		for (int i = 0; i < text.length(); i++) {
			if (i % 2 == 0) {
				int count = this.array[0].countClockwiseRotations(char_position[0], text.charAt(i));
				buchstabe = this.array[0].rotateClockwise(char_position[0],count); 
				buchstabe = this.array[1].rotateClockwise2(char_position[1], count);
				buchstabe = this.array[2].rotateClockwise(char_position[2],count);
				str_ecryp += buchstabe;
			} else {
				int count = this.array[1].countClockwiseRotations2(char_position[1], text.charAt(i)); 
				buchstabe = this.array[0].rotateClockwise(char_position[0],count);
				buchstabe = this.array[1].rotateClockwise2(char_position[1], count);
				buchstabe = this.array[2].rotateClockwise(char_position[2],count);
				str_ecryp += buchstabe;
			}
		}
		return str_ecryp;
	}
	
	 public String decrypt(String ecryp_text) {
		 char[] char_position = new char[3];
		 for(int a = 0;a<this.str_schlüssel.length();a++){
				char_position[a]=this.str_schlüssel.charAt(a);
			}
			int count=0;
		 	char buchstabe=' ';
			String plain_text="";
			for (int i = ecryp_text.length()-1; i >=0; i--) {
				if(i==0){
					count = this.array[2].countClockwiseRotations(char_position[2], ecryp_text.charAt(i));
					buchstabe = this.array[0].rotateClockwise(char_position[0], count);
				}
				
				if(i%2==0){
					count = this.array[2].countClockwiseRotations(char_position[2], ecryp_text.charAt(i));
					buchstabe = this.array[0].rotateClockwise(char_position[0], count);
				}else{
					count = this.array[2].countClockwiseRotations(char_position[2], ecryp_text.charAt(i));
					buchstabe = this.array[1].rotateClockwise2(char_position[1], count);
				}
				plain_text = buchstabe+plain_text;
			}	
		    return plain_text;
	}

}
