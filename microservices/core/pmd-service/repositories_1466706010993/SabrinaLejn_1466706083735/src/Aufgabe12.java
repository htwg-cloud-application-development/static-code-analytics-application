import java.util.Scanner;
public class Aufgabe12 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		{
	
	System.out.print("First Integer: ");
	int firstInt = scanner.nextInt ();
	System.out.print("Second Integer: ");
	int secondInt = scanner.nextInt ();
	
	if ((!((firstInt==42)&(secondInt==42))&(!(!(firstInt==42)&(!(secondInt==42)))))){
		System.out.print("Das ist die Antwort");}

	scanner.close();		
		}}}

