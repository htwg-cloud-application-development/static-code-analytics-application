import java.util.Scanner;

public class Aufgabe18 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		{

float packageWeight;
			
do{
System.out.println("Please enter the package weight: ");			
packageWeight = scanner.nextFloat();

if (packageWeight<2 && packageWeight>0){ System.out.print("Cost: 4,99 €");}
if (packageWeight<5 && packageWeight>2){ System.out.print("Cost: 5,99 €");}
if (packageWeight<10 && packageWeight>5){ System.out.print("Cost: 7,99 €");}
if (packageWeight<31.5 && packageWeight>10){ System.out.print("Cost: 13,99 €");}
else if (packageWeight<0 | packageWeight>31.5) { System.out.println("Excessive or negative weight!"); 
}
}while ((packageWeight>31.5) | (packageWeight<0));			
				
scanner.close();			
		}}}


