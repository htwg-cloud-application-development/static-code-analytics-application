
public class Enigma {
	private Walze[] Staebe;
	private String key;
	
	public Enigma(int w1, int w2, int w3, String key){
		this.key=key;
		Walze Walze1 = new Walze (w1);
		Walze Walze2 = new Walze (w2);
		Walze Walze3 = new Walze (w3);
		Staebe = new Walze[] {Walze1, Walze2, Walze3};
	}
	
	public String encrypt(String Text){
		char[] encrypt = Text.toCharArray();
		char[] keyarr = key.toCharArray();
		char Ergebnis;
		int Drehung = 0; 
		Text="";
		for(int id=0; id<encrypt.length; id++){
			if((id%2) == 0){
			Drehung = Staebe[0].countClockwiseRotations(keyarr[0], encrypt[id]);
			keyarr[0] = Staebe[0].rotateClockwise(keyarr[0], Drehung);
			keyarr[1] = Staebe[1].rotateCounterClockwise(keyarr[1], Drehung);
			Ergebnis = Staebe[2].rotateClockwise(keyarr[2], Drehung);
			keyarr[2] = Staebe [2].rotateClockwise(keyarr[2], Drehung);	
			} 
			else{
			Drehung = Staebe[1].countCounterClockwiseRotations(keyarr[1], encrypt[id]);
			keyarr[0] = Staebe[0].rotateClockwise(keyarr[0], Drehung);
			keyarr[1] = Staebe[1].rotateCounterClockwise(keyarr[1], Drehung);
			Ergebnis = Staebe[2].rotateClockwise(keyarr[2], Drehung);
			keyarr[2] = Staebe [2].rotateClockwise(keyarr[2], Drehung);	
			}
			Text=Text+Ergebnis;
		}
		return Text;
	}
	public String decrypt(String text) {
		char[] decrypt = text.toCharArray();
		char[] keyArr = key.toCharArray();
		char Ergebnis;
		text = "";

		int Drehung = 0;

		for (int i = 0; i < decrypt.length; i++) {
			if ((i % 2) == 0) {
				Drehung = Staebe[2].countCounterClockwiseRotations(keyArr[2], decrypt[i]);
				Ergebnis = Staebe[0].rotateCounterClockwise(keyArr[0], Drehung);
				keyArr[0] = Staebe[0].rotateCounterClockwise(keyArr[0], Drehung);
				keyArr[1] = Staebe[1].rotateClockwise(keyArr[1], Drehung);
				keyArr[2] = Staebe[2].rotateCounterClockwise(keyArr[2], Drehung);

			} else {
				Drehung = Staebe[2].countClockwiseRotations(keyArr[2], decrypt[i]);
				Ergebnis = Staebe[1].rotateCounterClockwise(keyArr[1], Drehung);
				keyArr[0] = Staebe[0].rotateCounterClockwise(keyArr[0], Drehung);
				keyArr[1] = Staebe[1].rotateClockwise(keyArr[1], Drehung);
				keyArr[2] = Staebe[2].rotateCounterClockwise(keyArr[2], Drehung);

			}
			text = text + Ergebnis;
		}

		return text;

	}

	}

