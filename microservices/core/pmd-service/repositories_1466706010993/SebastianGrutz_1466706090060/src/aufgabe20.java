import java.util.Scanner;
public class aufgabe20 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner scanner = new java.util.Scanner(System.in);
		System.out.println("Bitte geben sie eine Zahl ein");
		int anumber = scanner.nextInt();
		
		if (anumber>=0 && anumber<20) {
			String[] Einer = {"null","eins","zwei","drei","vier","fŸnf","sechs","sieben","acht","neun","zehn","elf","zwšlf","dreizehn","vierzehn","fŸnfzehn","sechzehn","siebzehn","achtzehn","neunzehn"};
			
		String a = Einer [ anumber];
			System.out.print(a);
			} else {
				
				String[] Einer = {"null","eins","zwei","drei","vier","fŸnf","sechs","sieben","acht","neun","zehn","elf","zwšlf","dreizehn","vierzehn","fŸnfzehn","sechzehn","siebzehn","achtzehn","neunzehn"};
				
				String a = Einer [ anumber -((int) (anumber/10)*10)];
					System.out.print(a);
			
			 String []zehner = {"","","und zwanzig","und drei§ig","und vierzig","und fŸnfzig","und sechzig","und siebzig","und achtzig","und neunzig"};
			 String b= zehner [(int) (anumber/10)];
			// if(anumber)
			 System.out.print( " "+b);
			}
		

			 
					 
			 
		}

}


