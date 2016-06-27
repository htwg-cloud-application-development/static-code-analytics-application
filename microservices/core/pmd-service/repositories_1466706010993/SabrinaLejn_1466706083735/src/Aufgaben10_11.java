import java.util.Scanner;

public class Aufgaben10_11 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		{
// Aufgabe 10
// 10.1 			
System.out.print("First Integer: ");
int firstInt = scanner.nextInt ();
System.out.print("Second Integer: ");
int secondInt = scanner.nextInt ();

System.out.println("Result: " + (float)firstInt/secondInt);
/*Testen Sie Ihr Programm mit den Eingabezahlpaaren 
 * (10,2)		- Result: 5.0
 * (14,8)		- Result: 1.75
 * (1,2)		- Result: 0.5
 * (-2,42)		- Result: -0.04761905 
 * (1,0).		- Result: Infinity								
 * */		

// 10.2
System.out.print("First Float: ");
float firstFloat = scanner.nextFloat ();
System.out.print("Second Float: ");
float secondFloat = scanner.nextFloat ();

//System.out.println("Result: " + (int)firstFloat*secondFloat);
//System.out.println("Result: " + (float)firstFloat*secondFloat);
//System.out.println(("rounded up: ")+(Math.ceil(firstFloat*secondFloat)));   
//System.out.println(("rounded: ") + (Math.floor(firstFloat*secondFloat))); 

int resInteger = (Math.round(firstFloat*secondFloat)); 
double resDec = (Math.abs(firstFloat*secondFloat))-(Math.floor(Math.abs((firstFloat*secondFloat))));

System.out.println(("Integer: ") + resInteger + " " +"Decimal: " + resDec);
System.out.println( "Left decimal: " + (1 - resDec));

/*Testen Sie Ihr Programm mit den Eingabezahlpaaren 
(2.5;4.1)		- float: 10.25 rounded up: 11.0 rounded: 10.0 Integer: 10 Decimal: 0,25/0,75
(-3.0;14.3)		- float: -42.9 rounded up: -42.0 rounded: -43.0 Integer: -43 Decimal: 0.9000015258789062 Left decimal: 0.09999847412109375
(42.0;0.16)		- float: 6.72 rounded up: 7.0  rounded: 6.0 Integer: 7 Decimal: 0.7199997901916504 Left decimal: 0.2800002098083496
*/


//Aufgabe 11
//11.1

System.out.print("Enter the first Number: ");
int firstNumber = scanner.nextInt();
System.out.print("Enter the second Number: ");
int secondNumber = scanner.nextInt();
System.out.print("Enter the third Number: ");
int thirdNumber = scanner.nextInt();

if (firstNumber < secondNumber && firstNumber < thirdNumber){
			
	System.out.print(firstNumber);
	if (secondNumber < thirdNumber){
	System.out.print(secondNumber); 
	System.out.print(thirdNumber);}
	else {
		System.out.print(thirdNumber);
		System.out.print(secondNumber);}
	}
	
if (secondNumber < firstNumber && secondNumber < thirdNumber){
	
	System.out.print(secondNumber);
	if (firstNumber < thirdNumber){
	System.out.print(firstNumber); 
	System.out.print(thirdNumber);}
	else{
		System.out.print(thirdNumber);
		System.out.print(firstNumber);}
		}

if (thirdNumber < firstNumber && thirdNumber < secondNumber){	

	System.out.print(thirdNumber);
	if (firstNumber < secondNumber){
	System.out.print(firstNumber); 
	System.out.print(secondNumber);}
	else{
		System.out.print(secondNumber);
		System.out.print(firstNumber);}
	}
	

scanner.close();}}}






