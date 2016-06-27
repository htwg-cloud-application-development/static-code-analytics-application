
public class Aufgabe24 {
	public static void main(String[] args) {

//Pi = 4 x (1 - 1/3 + 1/5 - 1/7 + 1/9 – 1/11 + 1/13 – 1/15 + ...).		
		
double pi=1.0d, one=1.0d, denominator=3;

for (int i=1; denominator<200;i++){



if ((i%2)==1){ //Zahl ungerade?
pi=pi-(double)(one/denominator);
}else{ pi=pi+(double)(one/denominator);

}
System.out.print(i+" ");
denominator=denominator+2;

//System.out.print(pi+" ");

System.out.print("Pi= " +4*pi+" ");

//24.2
System.out.println("Math.PI= "+Math.PI);
double Difference= Math.abs(100-((Math.PI)/(4*pi)*100));
System.out.println("Difference= " + Math.round(Difference)+" %");
}
		
	
		}
			

	}