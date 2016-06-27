
public class Aufgabe29 {
	public static void main(String[] args) {

		int[] PrimeArray;
		int id;
		PrimeArray = new int[100];
		boolean[] IsPrime= new boolean [100];

		for (id = 0; id < PrimeArray.length; id++) {
			PrimeArray[id] = id + 1;

			// System.out.println(PrimeArray[id]);
			IsPrime [1]=true;
			for (int j = 2; j < PrimeArray[id]; j++) {
				IsPrime[id] = true;

				if (PrimeArray[id] % j == 0) {
					IsPrime[id] = false;
					break;
				}
				if (j == PrimeArray[id]) {
					IsPrime[id] = true;
				}
			}
		}
			for (id = 0; id < PrimeArray.length; id++) {
				
				if (IsPrime[id] == true) {
					System.out.println(PrimeArray[id] + " is prime");

					
				}
			}
		
// bis hier richtig
			
for (id=0; id< IsPrime.length; id++ )
{			
 if (IsPrime[id]==false){
 
 int i=2;
 while (i<PrimeArray[id])
 {
	 if(PrimeArray[id]%i==0){break;}
	 i++;
 }
 
 if (PrimeArray[id]==1)
	 i=1;
 
 System.out.print(PrimeArray[id]+" "+i);
 	i=id;
 	while (i>1)
 	{
 		if((IsPrime[i-1])&(PrimeArray[id]%i==0)){break;}
 		i--;
 	}
 	if(PrimeArray[id]==1){
 	i=1;}
 System.out.println(" "+i);
}}}
}


