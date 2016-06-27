	public class Aufgabe11 {

		public static void main(String[] args) {
			
		java.util.Scanner scanner	=new java.util.Scanner(System.in);
	   System.out.println("Geben sie drei verschiedene Ganzzahlen ein");
	   int a= scanner.nextInt();
	   int b= scanner.nextInt();
	   int c=scanner.nextInt();
	   
	   if (a<c) {   
		 if (a<b){// 
		   if (b<c){ //a ist kleiner als b und c
			   System.out.print(a); //a kl. b, c gr.
			   System.out.println(b);
			   System.out.println(c);} 
			   else //a kleinst. dann c
			  {System.out.println(a);
			   System.out.println(c);
			   System.out.println(b);
			   }}
		   else {System.out.println(b); //a<c b<a
		   System.out.println(a);
		   System.out.println(c);}
		    
		   }
		   else {  //c<a
			   if (c<b) { // c<a c<b c kleinste
				   if (a<b){ 
				   System.out.println(c); 
				   System.out.println(a);
				   System.out.println(b);
				   }
				   else //c<a und b<a und c<b
				   System.out.println(c);
				   System.out.println(b);
				   System.out.println(a);}
			  
			   else {System.out.println(b);
			   System.out.println(c);
			   System.out.println(a);}
			   scanner.close();  }
	   }	   
	     	   
	} 
