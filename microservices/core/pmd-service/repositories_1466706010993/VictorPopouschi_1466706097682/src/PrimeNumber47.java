

public class PrimeNumber47 {
	public boolean prim = true;
	public int grenze = 1000;
	int nr = 0;
	int value = 0;

	public static void main(String args[]) {
		PrimeNumber47 function = new PrimeNumber47();
		function.isPrime(11);
	}

	public boolean isPrime(int zahl) {
		this.value = zahl;
		if (zahl <= grenze) {
			for (int div = 2; div <= zahl - 1; div++) {
				if (zahl % div == 0) {
					prim = false;
				}
			}
		} else
			System.out.println("Die Grenze wurdeüberschritten!");

		System.out.println(prim);
		return prim;
	}
}