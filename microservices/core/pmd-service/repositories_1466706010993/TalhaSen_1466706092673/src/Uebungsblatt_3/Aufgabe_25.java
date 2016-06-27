package Uebungsblatt_3;

		import java.io.IOException;
		import java.io.InputStream;
		import java.net.URL;
		import java.util.Scanner;

		import javax.xml.parsers.*;
		import org.w3c.dom.Document;

		import org.w3c.dom.NodeList;
		import org.w3c.dom.Node;
		import org.w3c.dom.Element;

		public class Aufgabe_25 {


				  public static void main( String[] args )
				  {
				    InputStream is = null;					//initialisierung v. Stream f¸r Webs.
				    Scanner s = new Scanner(System.in);
				    Double rateAusgang, rateZiel;
				    rateAusgang = 1.0;
				    rateZiel = 1.0;
				    
				    
				    System.out.println("---------- Waehrungsrechner ----------");
				    System.out.println("USD, TRY, EUR, ... ");
				    System.out.print("Ausgansw‰hrung: ");
				    String currencyAusgang = s.next();
				    System.out.print("Zielw‰hrung: ");
				    String currencyZiel = s.next();
				    System.out.print("Betrag: ");
				    Double Betrag = s.nextDouble();
				    
				    
				    
				    try  //Versuche ->
				    {
				      URL url = new URL( "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml" );//vorbereiten der url
				      is = url.openStream();		//holt seiteninhalt
				      
				      
				      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();	//webs. als verarbeitbares doc. -> xml
				      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				      Document doc = dBuilder.parse(is);
				      doc.getDocumentElement().normalize(); 
				      
				      NodeList nList = doc.getElementsByTagName("Cube");	//list mit w‰hrungen anlegen

				      for (int temp = 0; temp < nList.getLength(); temp++) {	//geht jeden node durch

				          Node nNode = nList.item(temp); 	//hole aktuelle node

				          if (nNode.getNodeType() == Node.ELEMENT_NODE) { //wenn w‰hrungsnode ->

				              Element eElement = (Element) nNode; // node -> element casten
				              String currency = eElement.getAttribute("currency");//w‰hrung & kurs abgreifen
				              String rate = eElement.getAttribute("rate");
				              if(currency.isEmpty()){
				                  continue;
				              }
				              
				              if (currency.equals(currencyAusgang)) // wenn abgeriffene w‰hrung = eingeg.
				            	  rateAusgang =  Double.parseDouble(rate);//string -> double
				              if (currency.equals(currencyZiel))
				            	  rateZiel =  Double.parseDouble(rate);
				             
				              
				          }
				      }
				      
		              Double BetragNeu = Betrag/rateAusgang*rateZiel;
		              System.out.println("Entspricht: " + BetragNeu);
				      
				    }
				    catch ( Exception e ) {  //Fehler abfangen und ausgeben
				      e.printStackTrace();
				    }
				    finally {  //Am Ende is wieder schlieﬂen
				      if ( is != null )
				        try { is.close(); } catch ( IOException e ) { }
				    }
				  }
		

	}

	
	
