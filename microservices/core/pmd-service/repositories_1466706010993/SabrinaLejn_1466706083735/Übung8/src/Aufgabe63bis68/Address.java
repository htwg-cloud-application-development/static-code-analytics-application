package Aufgabe63bis68;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Address {

	String vornameNachname;
	String strasseHausnummer;
	String plz;
	String Ort;
	String land;

	/* Konstruktor */
	Address() {

		this.vornameNachname = " kein Name ";
		this.strasseHausnummer = " keine Strasse ";
		this.plz = " keine PLZ ";
		this.Ort = " kein Ort ";
		this.land = " kein Land ";

	}

	public String getVornameNachname() {
		return vornameNachname;
	}

	public void setVornameNachname(String vornameNachname) {
		this.vornameNachname = vornameNachname;
	}

	public String getStrasseHausnummer() {
		return strasseHausnummer;
	}

	public void setStrasseHausnummer(String strasseHausnummer) {
		this.strasseHausnummer = strasseHausnummer;
	}

	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	public String getOrt() {
		return Ort;
	}

	public void setOrt(String Ort) {
		this.Ort = Ort;
	}

	public String getLand() {
		return land;
	}

	public void setLand(String land) {
		this.land = land;
	}

	public String getAdressLabel() throws FileNotFoundException {

		Meth Kombi = new Meth();

		if ((Kombi.CombiUK(plz, Ort) == true)) {

			return vornameNachname + "\n" + strasseHausnummer + "\n" + plz + " " + Ort + "\n" + land;

		} else

		if ((Kombi.CombiDE(plz, Ort) == true)) {

			return vornameNachname + "\n" + strasseHausnummer + "\n" + plz + " " + Ort + "\n" + land;

		} else

		if ((Kombi.ortUK(Ort) != "")) {
			plz = Kombi.ortUK(Ort);
			return vornameNachname + "\n" + strasseHausnummer + "\n" + plz + " " + Ort + "\n" + land;

		} else

		if ((Kombi.ortDE(Ort) != "")) {
			plz = Kombi.ortDE(Ort);
			return vornameNachname + "\n" + strasseHausnummer + "\n" + plz + " " + Ort + "\n" + land;

		} else

		if ((Kombi.plzDE(plz) != "")) {
			Ort = Kombi.plzDE(plz);
			return vornameNachname + "\n" + strasseHausnummer + "\n" + plz + " " + Ort + "\n" + land;

		} else

		if ((Kombi.plzUK(plz) != "")) {
			Ort = Kombi.plzUK(plz);
			return vornameNachname + "\n" + strasseHausnummer + "\n" + plz + " " + Ort + "\n" + land;
		}
		return vornameNachname + "\n" + strasseHausnummer + "\n" + plz + " " + Ort + "\n" + land;

	}
}
