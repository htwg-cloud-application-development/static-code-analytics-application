
public class PrimeNumber3 {
int value;
boolean isPrime;

public PrimeNumber3(int value){
	this.value = value;
	
	for(int i=1; i < value-1; i++){
		if(value%i ==0)
			isPrime = false;
		else
			isPrime = true;
	}
}


//EOF
}
