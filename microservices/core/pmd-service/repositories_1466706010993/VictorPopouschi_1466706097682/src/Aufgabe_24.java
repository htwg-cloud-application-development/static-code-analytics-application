
public class Aufgabe_24 {
public static void main(String[] args) {
	System.out.println("PI: ");
	double r = 0;
  
    
	for(int z = 1; z<1000;z++){
		if(z%2==1)
		{
			 r= r + ((double)(1))/(z*2-1);
			 System.out.println(r);
		}
		if(z%2==0){
			 r = r+ (-(1/((double)(2*z-1))));
		}
	}
	  double pizahl =4*r;
	System.out.println(pizahl);
	
	double pi = Math.PI;
	double prozentAb = 100 / pi * pizahl ;
	prozentAb = 100 - prozentAb;
	System.out.println("Die Abweichung(prozentual betrachtet) : "+prozentAb);
    System.out.println("wenn die anzahl der Reihenglieder größer ist, wird die Abweichung kleiner");
	
}
}
