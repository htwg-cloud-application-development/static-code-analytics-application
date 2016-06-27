import java.util.Scanner;

public class Aufgabe19 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		{
String currency;
float amount = 0;

System.out.print("Please enter your amonunt: ");
amount=scanner.nextFloat();

if (amount<=0){System.out.print("Please enter a positive amount!");
	
}
else{
System.out.println("Please choose your currency (USD or EUR): ");
currency=scanner.next();


switch (currency){
case "USD": System.out.print((amount*0.8855)+(" EUR"));break;
case "EUR":	System.out.print((amount*1.1292)+(" USD"));break;
default: System.out.print("Error, try again!");
}

		
scanner.close();
}
}}}