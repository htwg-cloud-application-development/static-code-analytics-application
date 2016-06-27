import java.util.Scanner;

public class Aufgabe17 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		{

System.out.print("Please enter your height: ");			
float height=scanner.nextFloat();
System.out.print("Please enter your weight: ");
float weight=scanner.nextFloat();

float bmi =((int)(weight/(Math.pow(2, height))));
System.out.println("Your bmi: " + bmi);

if (bmi< 18.5){
	System.out.println("You are underweight!");}
if ((bmi > 18.5) && (bmi < 24.9)){
	System.out.println("You are normal");}
if (bmi> 24.9){
	System.out.println("You are overweight!");}

	
scanner.close();
		}}}


