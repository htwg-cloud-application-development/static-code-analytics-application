package Aufgabe52;

public class MainClass52 {
	public static void main(String[] args) {
		
		Car52 VW = new Car52 ();
		VW.Colour= "gelb";
		VW.MaxSpeed=260;
		VW.Manufacturer="VW";
		
		Car52 BMW = new Car52 ();
		BMW.Colour= "rot";
		BMW.MaxSpeed=600;
		BMW.Manufacturer="BMW";
		
		Car52 VWtest=new Car52 (); 		//neue Instanz anlegen 
		
		System.out.println(VW.Description());
		System.out.println(BMW.Description());
		System.out.println(VWtest.Description());
	}

	}

