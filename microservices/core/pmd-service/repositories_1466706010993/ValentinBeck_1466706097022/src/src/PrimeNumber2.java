
public class PrimeNumber2 {
	
	boolean isPrime = true;
	int value;
	public boolean isPrime(){
		for(int i=1; i < value-1; i++){
			if(value%i ==0){
				isPrime = false;
			}
			
			else isPrime = true;
		}
		return isPrime;
	}
}
