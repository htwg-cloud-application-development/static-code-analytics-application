import java.util.Scanner;
public class Aufgabe13 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		{

//13.1 

System.out.print("Enter your age: ");
int age = scanner.nextInt();

System.out.print("Your ticket price: ");
int ticketPrice = (age >= 16)?20:10;
System.out.println(ticketPrice);


//	if (age >= 16) {
	//	 ticketPrice = 20;
		//} else {
		 //ticketPrice = 10;
		//}

//13.2

System.out.println("Enter the first Number: ");
int firstNumber = scanner.nextInt();
System.out.println("Enter the second Number: ");
int secondNumber = scanner.nextInt();
System.out.println("Enter the third Number: ");
int thirdNumber = scanner.nextInt();

String sequence = ((firstNumber<secondNumber) & (secondNumber<thirdNumber))?"Numbers are in proper sequence":"Numbers are not in proper sequence";

System.out.print(sequence);}

scanner.close();	
		}}
