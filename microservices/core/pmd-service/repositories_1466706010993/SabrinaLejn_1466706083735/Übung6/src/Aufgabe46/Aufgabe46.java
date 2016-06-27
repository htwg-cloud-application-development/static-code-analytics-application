package Aufgabe46;

public class Aufgabe46 {

public static void main(String[] args) {
	
	Car VW = new Car ();
	VW.Colour= "gelb";
	VW.MaxSpeed=260;
	VW.Manufacturer="VW";
	
	Car BMW = new Car ();
	BMW.Colour= "rot";
	BMW.MaxSpeed=600;
	BMW.Manufacturer="BMW";
	
	System.out.println(VW.Description());
	System.out.println(BMW.Description());
}

}