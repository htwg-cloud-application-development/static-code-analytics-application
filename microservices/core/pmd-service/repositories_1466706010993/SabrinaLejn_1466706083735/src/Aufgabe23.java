import java.util.Scanner;

public class Aufgabe23 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		
//23.1		
int Number=0,entNumber,Sum=0;
		
System.out.print("Please enter a Number: ");
entNumber=scanner.nextInt();

System.out.println("Integers: ");

for (; Number<=entNumber; Number++) {
	
	System.out.println(Number);

    Sum=Sum+Number;}

System.out.println("[for]Sum of the integers: " + Sum);
System.out.println("[for]Average: " +(float) Sum/entNumber);




//23.2

while (Number<=entNumber){
	
System.out.println(Number);

Sum=Sum+Number;
Number++;
}	
System.out.println("[while]Sum of the integers: " + Sum);
System.out.println("[while]Average: " +(float) Sum/entNumber);	
		
//23.3
Number=0; Sum=0;
do {
	Sum=Sum+Number;
	Number=Number+1;
	
}while (Number<=entNumber);

System.out.println("[do-while]Sum of the integers: " + Sum);
System.out.println("[do-while]Average: " +(float) Sum/entNumber);


//23.4
	Number=0;Sum=0;

	do{	
	System.out.print("[%2]Please enter a Number: ");
	entNumber=scanner.nextInt();	
	}while ((entNumber%2)==0);
	
	for (; Number<=entNumber; Number++) {
		
		System.out.println(Number);

	    Sum=Sum+Number;}

	System.out.println("[%2]Sum of the integers: " + Sum);
	System.out.println("[%2]Average: " +(float) Sum/entNumber);

//23.5
Number=0;Sum=0;

do{	
System.out.print("[%7]Please enter a Number: ");
entNumber=scanner.nextInt();	
}while ((entNumber%7)>0);

for (; Number<=entNumber; Number++) {
	
	System.out.println(Number);

    Sum=Sum+Number;}

System.out.println("[%7]Sum of the integers: " + Sum);
System.out.println("[%7]Average: " +(float) Sum/entNumber);

//23.6
Number=0;int SumSquares=0;
	
System.out.print("[Squares]Please enter a Number: ");
entNumber=scanner.nextInt();	

for (; Number<=entNumber; Number++){
	
	System.out.println(Number);
	SumSquares=SumSquares+(Number*Number);}

System.out.println("[for]Sum of squares: " + SumSquares);

		
scanner.close();		
	}}

