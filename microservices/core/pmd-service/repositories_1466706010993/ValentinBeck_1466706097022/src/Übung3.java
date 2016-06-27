import java.util.*;

public class Übung3 {
	 public static void main(String args[]){
	
	 //Aufgabe 10 Typumwandlung

//	
//	System.out.println("Aufgabe 11: Logische Operatoren und if-Anweisungen");
//	 
//	Scanner sort = new Scanner(System.in);
//	System.out.println("Die Zahlen sind: ");
//	
//	ArrayList<Integer> list = new ArrayList<Integer>();
//
//	int numb1 = sort.nextInt();
//	list.add(numb1);
//	int numb2 = sort.nextInt();
//	list.add(numb2);
//	int numb3 = sort.nextInt();
//	list.add(numb3);
//	System.out.println("unsortiert: "+list);
//	
//	Collections.sort(list);
//	System.out.println("sortiert: "+list);
//	
//	if(numb2 < numb1 && numb2 < numb3){
//		System.out.println(numb2);
//		if(numb1 < numb3){
//			System.out.println(numb1);
//			System.out.println(numb3);
//		}
//		else{
//			System.out.println(numb3);
//			System.out.println(numb1);
//		}
//	}
//	
//	if(numb1 < numb2 && numb1 < numb3){
//		System.out.println(numb1);
//		if(numb2 < numb3){
//			System.out.println(numb2);
//			System.out.println(numb3);
//		}
//		else{
//			System.out.println(numb3);
//			System.out.println(numb2);
//		}
//	}
//	
//	if(numb3 < numb1 && numb3 < numb2){
//		System.out.println(numb3);
//		if(numb1 < numb2){
//			System.out.println(numb1);
//			System.out.println(numb2);
//		}
//		else{
//			System.out.println(numb2);
//			System.out.println(numb1);
//		}
//	}
//
//	
//	Scanner aScanner = new Scanner(System.in);
//	int number1 = aScanner.nextInt();
//	int number2 = aScanner.nextInt();
//	
//	if(!(!(number1 == 42 && !(number2 == 42)) && !(number2 == 42 && !(number1 == 42)))){
//		System.out.println("Das ist die Antwort!");
//	}

	//Aufgabe 13.1
	
//	Scanner scanner2 = new Scanner(System.in);
//	System.out.println("Dein Alter ist: ");
//	int age = scanner2.nextInt();
//
//	int ticketPrice = (age < 16)  ?  10 : 20;
//	System.out.println("Ticketprice: "+ticketPrice);
//	 
//	//Aufgabe 13.2
//	 
//	Scanner scanner3 = new Scanner(System.in);
//	System.out.println("Gib drei Zahlen ein: ");
//	int zahl1 = scanner3.nextInt();
//	int zahl2 = scanner3.nextInt();
//	int zahl3 = scanner3.nextInt();
//	
//	String lösung = ((zahl1 < zahl2)&&(zahl2 < zahl3)) ? "richtig" : "falsch";
//	System.out.println(lösung);
	 
	 
	 

	 //Aufgabe 14:
//	
//	 int id = 20;
//	 while(id>0){
//		System.out.println(id);
//		id -= 2;
//	 }
//	 
//	 int i = 20;
//	 do {
//		 System.out.println(i);
//		 i -= 2;
//	 }
//	 while(i > 0);
// 
	 
	 //Aufgabe 15
	 //do-while wird immer ein mal ausgeführt! Dabei ist while die abbruch bedingung und nicht break! 
	 
//	Scanner sca = new Scanner(System.in);
//	int eingabe;
//		
//do{
//	eingabe = sca.nextInt();
//		switch(eingabe){
//		case 1:
//		 	System.out.println("Montag");
//		 	break;
//		case 2:
//		 	System.out.println("Dienstag");
//		 	break;
//		case 3:
//		 	System.out.println("Mittwoch");
//		 	break;
//		case 4:
//		 	System.out.println("Donnerstag");
//		 	break;
//		case 5:
//		 	System.out.println("Freitag");
//		 	break;
//		case 6:
//		 	System.out.println("Samstag");
//		 	break;
//		case 7:
//		 	System.out.println("Sonntag");
//		 	break;
//		default:
//			System.out.println("Falsche Eingabe!");
//			break;
//		}
//}
//
//while(eingabe > 7);

	//Aufgabe 16 
	 // nicht fertig.
//
//
//Scanner scan = new Scanner(System.in);
////int tagf;
//System.out.println("der erste Wochentag ist: ");
//int tagf = scan.nextInt();
//int tag;
//
//do{
//tag = scan.nextInt();
//tagf = tagf - (tag-1);
//
//	switch(tagf){
//	case 1:
//	 	System.out.println("Montag");
//	 	break;
//	case 2:
//	 	System.out.println("Dienstag");
//	 	break;
//	case 3:
//	 	System.out.println("Mittwoch");
//	 	break;
//	case 4:
//	 	System.out.println("Donnerstag");
//	 	break;
//	case 5:
//	 	System.out.println("Freitag");
//	 	break;
//	case 6:
//	 	System.out.println("Samstag");
//	 	break;
//	case 7:
//	 	System.out.println("Sonntag");
//	 	break;
//	default:
//		System.out.println("Falsche Eingabe!");
//		break;
//	}
//}
//
//while(tag > 7);

	 
	 
	 //Aufgabe 17
//
//	 Scanner bmi = new Scanner(System.in);
//	System.out.println("Gib dein Körpergewicht ein: ");
//	double weight = bmi.nextDouble();
//	System.out.println("Gib deine Größe ein: ");
//	double height = bmi.nextDouble();
//	double bmiWert = (weight/ Math.pow(height, 2));
//	System.out.println("Dein BMI beträgt: "+ bmiWert);
//	
//if (bmiWert < 18.5){
//	System.out.println("Du hast Untergewicht.");
//}
//else {
//	if(bmiWert < 24.9){
//		System.out.println("Du hast Normalgewicht");
//	}
//	else{
//		System.out.println("Du hast Übergewicht");
//	}
//}


	//Aufgabe 18

//
//Scanner packet = new Scanner(System.in);
//System.out.println("Paketgewicht: ");
//double gewicht = packet.nextDouble();
//
//
//if (gewicht < 2.0){
//	System.out.println("4,99 Euro");
//}
//else {
//	if(gewicht < 5.0){
//		System.out.println("5,99 Euro");
//	}
//	else{
//		if(gewicht < 10.0){
//		System.out.println("7,99 Euro");
//		}
//		else {
//			if(gewicht < 31.5)
//				System.out.println("13,99 Euro");
//			else
//				System.out.println("Nicht möglich!");
//		}
//	}
//}

	//Aufgabe 19
	 //Eingaben: Umrechnungskurs USD/EUR + Welche Richtung soll gewechselt werden + Welcher Betrag. Anschließend Switch mit Cases.
//
//Scanner umrechnung = new Scanner(System.in);
//System.out.println("Der Umrechnungskurs von USD/EUR ist: ");
//double kurs = umrechnung.nextDouble();
//System.out.println("1 = USD -> Euro. \n 2 = Euro -> USD.");
//int fall = umrechnung.nextInt();
//	switch(fall){
//		case 1: 
//			System.out.println("Betrag: ");
//			double betrag1 = umrechnung.nextDouble();
//			System.out.println("Der Betrag in Euro ist: ");
//			betrag1 = (betrag1* kurs);
//			System.out.println(betrag1);
//			break;
//		case 2:
//			System.out.println("Betrag: ");
//			double betrag2 = umrechnung.nextDouble();
//			System.out.println("Der Betrag in Dollar ist: ");
//			betrag2 = (betrag2/ kurs);
//			System.out.println(betrag2);
//			break;
//	
//}
//	
//		// Aufgabe 20.1
		 Scanner scan = new Scanner(System.in);
		 
		 System.out.println("Dezimalzahl eingeben");
		 String zahl = scan.next();
		 int num = Integer.parseInt(zahl);
		 String[] array = zahl.split("");
		 int eins = Integer.parseInt(array[0]);
		 int zwei = Integer.parseInt(array[1]);
		
		 //0 bis 9
		 String[] nr =	 {"null","ein(s)","zwei","drei","vier","fünf","sechs","sieben","acht","neun"};
		 String[] nr_2 = {"zehn","elf","zwölf","dreizehn","vierzehn","fünfzehn","sechszehn","siebzehn","achtzehn","neunzehn"};
		 String[] nr_1 = {"null","zehn","zwanzig","dreißig","vierzig","fünfzig","sechszig","siebzig","achtzig","neunzig"};
		 
		 //Für Zehner zahlen
		 
		 if((eins>0&&eins<10)&& (zwei ==0)){
		 System.out.println(nr_1[eins]);
		 }
		 
		 if((eins==1)&&(0<zwei)){
		 System.out.println(nr_2[zwei]);
		 }
		 
		 if((eins>1)&& (zwei>0)){
		 System.out.println(nr[zwei]+"und"+nr_1[eins]);
		 }
//
//		// Aufgabe 20.2
//		 Scanner scan  = new Scanner(System.in);
//		 System.out.println("Gleitkommazahl: ");
//		 String zahl = scan.next();
//		 String[] array = zahl.split(",");
//		 String[] arrayVor = array[0].split("");
//		 String[] arrayNach = array[1].split("");
//		 int vk1 = Integer.parseInt(arrayVor[0]);
//		 int vk2 = Integer.parseInt(arrayVor[1]);
//		 String[] arrayNachk = array[1].split("");
//		 int nk1 = Integer.parseInt(arrayNach[0]);
//
//		// 0 bis 9
//		 String[] nr = {"null","ein(s)","zwei","drei","vier","fünf","sechs","sieben","acht","neun"};
//		 String[] nr2 ={"zehn","elf","zwölf","dreizehn","vierzehn","fünfzehn","sechszehn","siebzehn","achtzehn","neunzehn"};
//		 String[] nr1 = {"null","zwanzig","zwanzig","dreißig","vierzig","fünfzig","sechszig","siebzig","achtzig","neunzig"};
//
//		 if(arrayNach.length == 2){
//			 int nk2 = Integer.parseInt(arrayNach[1]);
//		 
//		 
//		 //zehnerZahlen 
//		 if((vk1>0&&vk1<10)&& (vk2 ==0)){
//		 System.out.println(nr1[vk1]);
//		 }
//		 
//		 if((vk1==1)&&(0<=vk2)){
//		 System.out.println(nr2[vk2]);
//		 }
//		 
//		 if((vk1>1)&& (vk2>0)){
//		 System.out.print(nr[vk2]+"und"+nr1[vk1]);
//		 }
//		
//		 if((nk1 >-1) && (nk2 >-1) ){
//		 System.out.println(" "+"Komma"+" "+nr[nk1] +" "+ nr[nk2] );
//		 }
//		 }


	 	//Aufgabe 22 (nicht fertig)
	 
//	 Scanner prime = new Scanner(System.in);
//	 int testNumber = prime.nextInt();
//	 boolean isPrime = true;
//	 int[] pri = {2,3,5,7,11};
//	 
//	 for (int j = 2; j < testNumber && isPrime; j++) {
//			if ((testNumber % j) == 0) {
//				isPrime = false;
//			}
//	 }
//	 
//	 for(int prime=2; prime <= pri.length())
//		 
//	 System.out.println(isPrime);
//			 
//	 if(isPrime == false){
//		 if(testNumber%2==0){
//			 System.out.println(testNumber/2);
//			 testNumber = testNumber/prime;
//		 }
//	 }
	 
	 //Aufgabe 23.1 (Scanner und obergrenze für ganze Aufgabe benutzen)
	 
	 Scanner input = new Scanner(System.in);
	 int obergrenze = input.nextInt();
//	 int sum = 0;
//	 for(int i=1; i <= obergrenze; i++){
//		sum = sum + i;
//	 }
//	 System.out.println("Durchschnitt: "+ sum/obergrenze);
//	 System.out.println("Summe: " + sum);
	 
	 //Aufgabe 23.2
//	 int sum = 0;
//	 int i = 0;
//	 while(i <= obergrenze){
//		 sum = sum + i;
//		 i++;
//	 }
//	 System.out.println("Durchschnitt: "+ sum/obergrenze);
//	 System.out.println("Summe: " + sum);
	 
	 //Aufgabe 23.3
//	 int sum = 0;
//	 int i = 0;
//	 do {
//		 sum = sum + i;
//		 i++;
//	 }
//	 while(sum <= obergrenze);
//	 System.out.println("Durchschnitt: "+ sum/obergrenze);
//	 System.out.println("Summe: " + sum);
	 
	 //Aufgabe 23.4
	 
//	 int sum = 0;
//	 int i = 1;
//	 while(i <= obergrenze){
//		 sum = sum + i;
//		 i+=2;
//	 }
//	 System.out.println("Summe: " + sum);
//	 System.out.println("Durchschnitt: "+ (double) sum/obergrenze);
//	 
	 
	 //Aufgabe 23.5
//	 int sum = 0;
//	 int i = 0;
//	 for(i=0; i <= obergrenze; i++){
//		 if(i%7==0){
//		 sum = sum + i;
//		 }
//		 System.out.println(sum);
//	 }
//	 System.out.println("Summe: "+sum);
//	 System.out.println("Duchschnitt: "+ (double) sum/obergrenze);
	 
	 //Aufgabe 23.6
	 
//	 int sum = 0;
//	 int i = 1;
//	 for(i=1; sum < obergrenze; i++){
//		 
//		 sum = sum + (i*i);
//		 
//		 System.out.println(sum);
//	 }
//	 System.out.println("Summe: "+sum);
//	 System.out.println("Duchschnitt: "+ (double) sum/obergrenze);
	 
	 
	 
	 //Aufgabe 24 (nicht fertig)
//	 
//	 for(int i=1; i < ){
//		 if(){
//			 
//		 }
//	 }
	 
	 
	//EOF
 }
}

