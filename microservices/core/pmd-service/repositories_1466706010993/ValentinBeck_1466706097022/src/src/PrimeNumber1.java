
public class PrimeNumber1 {
	
	public static boolean isPrime(int zahl){
		
		boolean isPrime = true;
		for(int i=1; i < zahl-1; i++){
			if(zahl%i ==0){
				isPrime = false;
			}
			else isPrime = true;
		}
		return isPrime;
	}
}
