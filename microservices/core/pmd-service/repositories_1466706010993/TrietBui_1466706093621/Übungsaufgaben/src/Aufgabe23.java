import java.util.Scanner;

public class Aufgabe23 {

    public static void main(String[] args) {
    	
    	//--------------------for_Schleife
   
    	Scanner s = new Scanner(System.in);
    	System.out.println("Geben Sie eine Obergrenze ein");
    	int obergrenze = s.nextInt();
    	
    	int a = 0;
    	for (int i = 1 ; i <=obergrenze ; i++) {
    	
    		a = a+i;
    		System.out.print(i+" ");
    		}
    		System.out.println("Summe: "+a);
    		double durchschnitt = (double) a/obergrenze;
    		System.out.println("Durchschnitt: "+durchschnitt);
    		  
    		}
    	
  
    	
    
    	//----------------while-Schleife
//    	Scanner s = new Scanner(System.in);
//    	System.out.println("Geben Sie eine Obergrenze ein");
//    	int obergrenze = s.nextInt();
//    	int a = 0;
//    	
//    	int i = 1;
//    	while (i <= obergrenze){
//    		a= a+i;
//    		i++;
//    	}
//    	
//    	System.out.println(a);
    	
    	//---------------------------------do_Schleife
    	
//    	Scanner s = new Scanner(System.in);
//	   	System.out.println("Geben Sie eine Obergrenze ein");
//	   	int obergrenze = s.nextInt();
//	   	int a = 0;
//	   	int i = 1;
    	
    	
//	   	
//	   	do { 
//	   		a= a+i;
//	   		i++;
//	   		
//	   	}	while (i <= obergrenze);
//	   	System.out.println(a);
	   	
    	
    	
    	
    	
    }

    
    	