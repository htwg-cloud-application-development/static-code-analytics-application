import java.util.Scanner;
public class Aufgabe16 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		{
int Number, corNumber = 0;

System.out.print("Please enter the first day of the week: ");
String firstDay= scanner.next();

switch (firstDay){
case "monday": corNumber=+0;break;
case "tuesday": corNumber=+1;break;
case "wednesday": corNumber=+2;break;
case "thursday": corNumber=+3;break;
case "friday": corNumber=+4;break;
case "satursday": corNumber=+5;break;	
case "sunday": corNumber=+6;break;
}

{
do {
	System.out.print("Please enter a Number between 1 and 7: ");
	Number = scanner.nextInt();
}while((Number<1) | (Number>7));

if ((corNumber+Number)>7){
	Number=(corNumber+Number)-7;
}
else{
Number=(corNumber+Number);
}
switch (Number) {
case 1:
	System.out.println("monday");
	break;
case 2:
	System.out.println("tuesday");
	break;
case 3:
	System.out.println("wednesday");
	break;
case 4:
	System.out.println("thursday");
	break;
case 5:
	System.out.print("friday");
	break;
case 6:
	System.out.print("satursday");
	break;
case 7:
	System.out.print("sunday");
	break;}

scanner.close();}}}

		
	}



	


	
