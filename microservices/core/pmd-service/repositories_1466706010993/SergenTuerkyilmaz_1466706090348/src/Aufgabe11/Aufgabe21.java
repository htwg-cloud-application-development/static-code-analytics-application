package Aufgabe11;
import java.util.Scanner;
public class Aufgabe21 {

	public static void main(String[] args) {	
	
Scanner s = new Scanner(System.in);

System.out.println("Geben Sie eine Dezimalzahl ein: ");

int y = s.nextInt();


String[] RoeamD= {"","M","MM","MMM"};
String d= RoeamD[(int)y/1000];
System.out.print(d);

String[] RoeamC= {"","C","CC","CCC","CD","D","DC","DCC","DCCC","CM"};
String c = RoeamC [(int)(y/100)%10];
System.out.print(c);

String[] RoeamB= {"","X","XX","XXX","XXXL","L","LX","LXX","LXXX","XC"};
String b = RoeamB [(int)(y/10)%10];
System.out.print(b);

String[] Roeam = {"","I","II","III","IV","V","VI","VII","VIII","IX"};
String a = Roeam [(int)y%10];
System.out.print(a);


	}

}


