package Uebungsblatt_7;

public class Walze {
		 
	    private final String Walzennummer50 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    private final String Walzennummer51 = "ADCBEHFGILJKMPNOQTRSUXVWZY";
	    private final String Walzennummer60 = "ACEDFHGIKJLNMOQPRTSUWVXZYB";
	    private final String Walzennummer61 = "AZXVTRPNDJHFLBYWUSQOMKIGEC";
	    private final String Walzennummer70 = "AZYXWVUTSRQPONMLKJIHGFEDCB";
	    private final String Walzennummer71 = "AEBCDFJGHIKOLMNPTQRSUYVWXZ";
	    private int Walzennummer;
	     
	     
	    public Walze(int a){
	        this.Walzennummer=a;
	    }
	     
	    //Walzenunterscheidung
	    public String WalzenUnterscheidung(){
	        switch(this.Walzennummer){
	        case 50:
	            return Walzennummer50;
	        case 51:
	            return Walzennummer51;
	        case 60:
	            return Walzennummer60;
	        case 61:
	            return Walzennummer61;
	        case 70:
	            return Walzennummer70;
	        case 71:
	            return Walzennummer71;
	            default:
	                System.out.println("Falsche Walzennummer");
	                return"";
	        }
	         
	    }
	     
	    //1.Zahnrad rotiert im Uhrzeigersinn
	    public int countClockwiseRotations(char start, char ziel){
	        String permutation= this.WalzenUnterscheidung();
	        int firstPosition = 0;
	        int lastPosition = 0;
	         
	        for(int i=0;i<permutation.length();i++){
	            if(permutation.charAt(i)==start){
	                firstPosition=i;
	            }
	            if(permutation.charAt(i)==ziel){
	                lastPosition=i;
	            }
	        }
	         
	        if(firstPosition<lastPosition){
	            return lastPosition-firstPosition;
	        }
	        else{
	            return (lastPosition-firstPosition)+26;
	        }
	    }
	     
	    //2.Zahnrad rotiert gegen den Uhrzeigersinn
	    public int countCounterClockwiseRotations(char start, char ziel){
	        String permutation= this.WalzenUnterscheidung();
	        int firstPosition = 0;
	        int lastPosition = 0;
	         
	        for(int i=0;i<permutation.length();i++){
	            if(permutation.charAt(i)==start){
	                firstPosition=i;
	            }
	            if(permutation.charAt(i)==ziel){
	                lastPosition=i;
	            }
	        }
	         
	        if(firstPosition>lastPosition){
	            return firstPosition-lastPosition;
	        }
	        else{
	            return (firstPosition-lastPosition)+26;
	        }
	    }
	     
	    //rotateClockwise für erstes Zahnrad --> bestimmt die Posititon des Buchstabens nach der Rotation -->Zahnrad 1 dreht sich im Uhrzeigersinn
	    public char rotateClockwise(char start, int rotation){
	        String permutation= this.WalzenUnterscheidung();
	        int firstPosition = 0;
	         
	        for(int i = 0;i<permutation.length();i++){
	            if(permutation.charAt(i)==start){
	            firstPosition=i;
	            }
	        }
	        if(firstPosition+rotation>25){
	            return permutation.charAt((firstPosition+rotation)-26);
	        }else{
	            return permutation.charAt(firstPosition+rotation);
	        }
	    }
	     
	    //rotateCounterClockwise für zweites Zahnrad --> bestimmt die Posititon des Buchstabens nach der Rotation -->Zahnrad 2 dreht sich gegen Uhrzeigersinn
	    public char rotateCounterClockwise(char start, int rotation){
	        String permutation= this.WalzenUnterscheidung();
	        int firstPosition = 0;
	         
	        for(int i = 0;i<permutation.length();i++){
	            if(permutation.charAt(i)==start){
	            firstPosition=i;
	            }
	        }
	        if(firstPosition-rotation<0){
	            return permutation.charAt((firstPosition-rotation)+26);
	        }else{
	            return permutation.charAt((firstPosition-rotation));
	        }
	    }
	 
	}

