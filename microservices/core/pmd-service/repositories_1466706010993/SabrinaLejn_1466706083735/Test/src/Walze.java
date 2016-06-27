
public class Walze {
	private int Walzennummer;
	private String Walzenpermutation;
	private final String Permutation1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private final String Permutation2 = "ADCBEHFGILJKMPNOQTRSUXVWZY";
	private final String Permutation3 = "ACEDFHGIKJLNMOQPRTSUWVXZYB";
	private final String Permutation4 = "AZXVTRPNDJHFLBYWUSQOMKIGEC";
	private final String Permutation5 = "AZYXWVUTSRQPONMLKJIHGFEDCB";
	private final String Permutation6 = "AEBCDFJGHIKOLMNPTQRSUYVWXZ";

	public Walze(int nummer) {
		Walzennummer = nummer;
		switch (nummer){
		case 50: 
			Walzenpermutation = Permutation1;
			break;
		case 51:
			Walzenpermutation = Permutation2;
			break;
		case 60:
			Walzenpermutation = Permutation3;
			break;
		case 61:
			Walzenpermutation = Permutation4;
			break;
		case 70:
			Walzenpermutation = Permutation5;
			break;
		case 71:
			Walzenpermutation = Permutation6;
			break;	
		}
	}
	public int countClockwiseRotations(char Start, char Ziel){
		int counter = 0;
		char[] Permutation = Walzenpermutation.toCharArray();
		if(Start==Ziel){
			return counter;
		}
		for(int id = 0; id < Permutation.length; id++){
			if(Permutation[id] == Start){
				if(id==25){
					id=-1;
				}
				for(int id2 = id+1; id2 < Permutation.length; id2++){
				counter++;
					if(Permutation[id2] == Ziel){
						return counter;
					}
					if(id2==25){
						id2=-1;
					}
			}
		
		}
		
	}
		return 666;
}
	
	public int countCounterClockwiseRotations(char Start, char Ziel){
		int counter = 0;
		char[] Permutation = Walzenpermutation.toCharArray();
		if(Start==Ziel){
			return counter;
		}
		for(int id = 0; id < Permutation.length; id++){
			if(Permutation[id] == Start){
				if(id==25){
					id=-1;
				}
				for(int id2 = id+1; id2 < Permutation.length; id2++){
				counter++;
					if(Permutation[id2] == Ziel){
						return  Math.abs(counter-26);
					}
					if(id2==25){
						id2=-1;
					}
			}
		
		}
		
	}
		return 666;
}
	public char rotateClockwise(char Start, int AnzRot) {
		char[] Permutation = Walzenpermutation.toCharArray();
		for(int id = 0; id < Permutation.length; id++){
			if(Permutation[id] == Start){
				if(id==25){
					id=-1;
				}
					while(id+AnzRot>25){
						AnzRot=AnzRot-26;
				}
				return Permutation[id+AnzRot];
			}
		
	}
		return 'Ö';
}
	public char rotateCounterClockwise(char Start, int AnzRot) {
		char[] Permutation = Walzenpermutation.toCharArray();
		for (int id = 0; id < Permutation.length; id++) {
			if (Permutation[id] == Start) {
				if ((id - AnzRot) < 0) {
					AnzRot = AnzRot % 26;
					if ((id - AnzRot) < 0) {
						id = id - AnzRot + 26;
						return Permutation[id];
					} else
						return Permutation[id - AnzRot];

				} else
					return Permutation[id - AnzRot];
			}
		}

		
	

		return 'Ö';
}
}
