

public class Aufgabe53 {
	public boolean isPrime;
	int value = 0;

	public Aufgabe53(int wert) {
		this.value = wert;
		isPrime();
	}

	public static void main(String args[]) {
		Aufgabe53 ifPrime = new Aufgabe53(23);
	}

	public boolean isPrime() {
		isPrime = true;
		for (int div = 2; div <= value - 1; div++) {
			if (value % div == 0) {
				isPrime = false;
			}
		}
		System.out.println(isPrime);
		return isPrime;
	}

}