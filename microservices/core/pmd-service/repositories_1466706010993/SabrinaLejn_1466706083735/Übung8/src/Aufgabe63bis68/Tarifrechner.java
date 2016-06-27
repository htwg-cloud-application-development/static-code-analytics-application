package Aufgabe63bis68;

public abstract class Tarifrechner {

	protected String versand;
	
	public String getVersand() {
		return versand;
	}

	public void setVersand(String versand) {
		this.versand = versand;
	}

	public abstract double Versandkostenberechnung (Address adresse, double weight);
		
	}
	
	// public, weil ansonsten autom. private, d.h. unsichtbar für Hermes etc.

