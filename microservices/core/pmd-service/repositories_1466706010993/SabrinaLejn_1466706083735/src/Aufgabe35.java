import java.util.Scanner;
public class Aufgabe35 {

	public static void main (String[]args){
	
	Scanner s=new Scanner(System.in);
	{
	int n;

do	
	{
	System.out.print("[if-clause]Enter a Number between 1 and 365: ");
	n=s.nextInt();
	}while (n<1 | n>365);
	
	if (n> 1 && n<=31){
		System.out.print(n+" "+"Januar");
	}else if (n>31 && n<=59){
		System.out.print((n-31)+ " "+"Februar");
	}else if (n>59 && n<=90){
		System.out.print((n-59)+" "+"März");
	}else if (n>90 && n<=120){
		System.out.print((n-90)+" "+"April");
	}else if (n>120 && n<=151){
		System.out.print((n-120)+" "+"Mai");
	}else  if (n>151 && n<=181){
		System.out.print((n-151)+" "+"Juni");
	}else if (n>181 && n<=212){
		System.out.print((n-181)+" "+"Juli");
	}else if (n>212 && n<= 243){
		System.out.print((n-212)+" "+"August");
	}else if (n>243 && n<= 273){
		System.out.print((n-243)+" "+"September");
	}else if (n>273 && n<=304){
		System.out.print((n-273)+" "+"Oktober");
	}else if (n>304 && n<=334){
		System.out.print((n-304)+" "+"November");
	}else if (n>334 && n<=365){
		System.out.print((n-334)+" "+"Dezember");
	}
System.out.println();	
int calArrayInt[]=new int [365];
String calArrayString[]= new String [365];

for (int id=0; id<31; id++){
	calArrayInt[id]=id+1;
	calArrayString[id]="Januar";
//System.out.println(calArrayString[id]+" "+calArrayInt[id]);
}
for (int id=31; id<59; id++){
	calArrayInt[id]=id+1-31;
	calArrayString[id]="Februar";
//System.out.println(calArrayString[id]+" "+calArrayInt[id]);	
}
for (int id=59; id<90; id++){
	calArrayInt[id]=id+1-59;
	calArrayString[id]="März";
//System.out.println(calArrayString[id]+" "+calArrayInt[id]);	
}
for (int id=90; id<120; id++){
	calArrayInt[id]=id+1-90;
	calArrayString[id]="April";
//System.out.println(calArrayString[id]+" "+calArrayInt[id]);	
}	
for (int id=120; id<151; id++){
	calArrayInt[id]=id+1-120;
	calArrayString[id]="Mai";
//System.out.println(calArrayString[id]+" "+calArrayInt[id]);	
}
for (int id=151; id<181; id++){
	calArrayInt[id]=id+1-151;
	calArrayString[id]="Juni";
//System.out.println(calArrayString[id]+" "+calArrayInt[id]);	
}
for (int id=181; id<212; id++){
	calArrayInt[id]=id+1-181;
	calArrayString[id]="Juli";
//System.out.println(calArrayString[id]+" "+calArrayInt[id]);	
}
for (int id=212; id<243; id++){
	calArrayInt[id]=id+1-212;
	calArrayString[id]="August";
//System.out.println(calArrayString[id]+" "+calArrayInt[id]);	
}
for (int id=243; id<273; id++){
	calArrayInt[id]=id+1-243;
	calArrayString[id]="Semtember";
//System.out.println(calArrayString[id]+" "+calArrayInt[id]);	
}
for (int id=273; id<304; id++){
	calArrayInt[id]=id+1-273;
	calArrayString[id]="Oktober";
//System.out.println(calArrayString[id]+" "+calArrayInt[id]);	
}
for (int id=304; id<334; id++){
	calArrayInt[id]=id+1-304;
	calArrayString[id]="November";
//System.out.println(calArrayString[id]+" "+calArrayInt[id]);	
}
for (int id=334; id<365; id++){
	calArrayInt[id]=id+1-334;
	calArrayString[id]="Dezember";
//System.out.println(calArrayString[id]+" "+calArrayInt[id]);	
}
int m;
System.out.println("[Array]Enter a new Number between 1 and 365: ");
m=s.nextInt();

System.out.print(calArrayInt[m-1]+" "+ calArrayString[m-1]);

}
	s.close();
}

}