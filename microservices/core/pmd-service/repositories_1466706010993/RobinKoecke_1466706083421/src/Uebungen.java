import java.io.*;
import java.util.Scanner;


public class Uebungen {
    FileReader fileReader;
    Scanner s;
    String file;

    public Uebungen() {
        //this.readFaust();
        this.postleitZahl();
    }

    public static void main( String[] args ) {
        Uebungen u = new Uebungen();
    }

    public String readInput() {
        try {
            s = new Scanner( System.in );
            String input = s.next();
            s.close();
            return input;

        } catch( Exception e ) {

        }
        return null;
    }


    // Buchstabensalat
    public void readFaust() {
        file = "faust.txt";

        char toScan = this.readInput().charAt(0);

        try {
            fileReader = new FileReader( "faust.txt" );

            int a;
            int anzahlBuchstaben = 0;

            while( (a=fileReader.read()) != -1 ) {
                if ( (char) a == toScan ) {
                    anzahlBuchstaben++;
                }
                //System.out.print( (char)a );
            }
            System.out.printf( "Ihr Buchstabe wurde %d mal gefunden!\n",anzahlBuchstaben );
            s.close();
        } catch( Exception e ) {}
    }
    
    // Aufgabe PLZ
    public void postleitZahl() {
        file = "plzDB.csv";
        String[] explodedLine;
        String input = this.readInput().trim();

        try(BufferedReader br = new BufferedReader(new FileReader(file))) {

            for(String line; (line = br.readLine()) != null; ) {
                explodedLine = line.split(" ");

                for ( int i = 0; i<explodedLine.length; i++ ) {
                    if ( explodedLine[i].equals(input) ) {
                        for( int c = 3; c<explodedLine.length; c++ ) {
                            System.out.print (explodedLine[c]+ " " );
                        }
                        System.out.println();
                    }
                }
            }
        } catch( Exception e ) {

        }
    }
}