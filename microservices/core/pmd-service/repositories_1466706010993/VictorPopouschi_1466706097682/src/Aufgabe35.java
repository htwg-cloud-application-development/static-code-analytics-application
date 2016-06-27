

import java.util.Scanner;

public class Aufgabe35 {
public static void main(String[] args) {
	Scanner input = new Scanner(System.in);
 // Tage ohne Felder
	System.out.println("Tag eingeben: ");
	int res = input.nextInt();
	if(res>0&& res <366){
	if(res<=31){
		System.out.println("januar");
	}
	if(res>31 && res<=61){
		System.out.println("februar");
	}
	if(res>61 && res<=91){
		System.out.println("märz");
	}
	if(res>91 && res<=121){
		System.out.println("april");
	}
	if(res>121 && res<=152){
		System.out.println("mai");
	}
	if(res>152 && res<=182){
		System.out.println("iuni");
	}
	if(res>182 && res<=213){
		System.out.println("juli");
	}
	if(res>213 && res<=244){
		System.out.println("august");
	}
	if(res>244 && res<=274){
		System.out.println("september");
	}
	if(res>274 && res<=305){
		System.out.println("oktober");
	}
	if(res>305 && res<=335){
		System.out.println("november");
	}
	if(res>335 && res<=365){
		System.out.println("dezember");
	}
	}
	else
	{
		System.out.println("Es gibt kein Tag 0 oder tag mehr als 365");
	}
	//Tage mit Felder
	int[]tage = new int[365];
	for(int z =0;z<tage.length;z++){
		tage[z]=z;
	}
	int[] mon = new int[12];
	for(int h =0;h<mon.length;h++){
		mon[h]=h;
	}
	
	String[] monat = {"Januar","Februar","März","April","Mai",
			"Juni","Juli","August","September","Oktober","November","Dezember"};
	System.out.println("Tag eingeben[mit felder]");
	
	int in = input.nextInt();
	if(tage[in]>0&& tage[in] <366){
		if(tage[in]<=31){
			System.out.println(monat[0]);
		}
		if(tage[in]>31 && tage[in]<=61){
			System.out.println(monat[1]);
		}
		if(tage[in]>61 && tage[in]<=91){
			System.out.println(monat[2]);
		}
		if(tage[in]>91 && tage[in]<=121){
			System.out.println(monat[3]);
		}
		if(tage[in]>121 && tage[in]<=152){
			System.out.println(monat[4]);
		}
		if(tage[in]>152 && tage[in]<=182){
			System.out.println(monat[5]);
		}
		if(tage[in]>182 && tage[in]<=213){
			System.out.println(monat[6]);
		}
		if(tage[in]>213 && tage[in]<=244){
			System.out.println(monat[7]);
		}
		if(tage[in]>244 && tage[in]<=274){
			System.out.println(monat[8]);
		}
		if(tage[in]>274 && tage[in]<=305){
			System.out.println(monat[9]);
		}
		if(tage[in]>305 && tage[in]<=335){
			System.out.println(monat[10]);
		}
		if(tage[in]>335 && tage[in]<=365){
			System.out.println(monat[11]);
		}
		}
		else
		{
			System.out.println("Es gibt kein Tag 0 oder tag mehr als 365");
		}
	//Mit Array kann man sich die Arbeit speichern und muss nicht so viel Code schreiben
}
}
