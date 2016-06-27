import java.util.Scanner;
public class Aufgabe15 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		{
int Number;

do {
	System.out.print("Please enter a Number between 1 and 7: ");
	Number = scanner.nextInt();
}while((Number<1) | (Number>7));

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

scanner.close();
}}}
	



