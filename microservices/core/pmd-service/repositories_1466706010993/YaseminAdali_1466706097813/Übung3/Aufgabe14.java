
public class Aufgabe14 {
	public static void main(String[] args) {
		int id = 20;
		while (id>0){
			System.out.println(id);
			id-=2;
		}
		System.out.println("while schleife ist durch");
		
		int id2 = 20;
		do {
			System.out.println(id2);
			id2-=2;			
		}
		while(id2>0);
		System.out.println("do-while schleife ist durch");

	for (int id3=20; id3>0; id3-=2){
		System.out.println(id3);
	}
}
}