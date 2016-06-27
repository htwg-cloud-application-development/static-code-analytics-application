import java.io.*;
import java.util.Scanner;
// Aufgabe Schachbrett 
public class Schachbrett {
    FileReader fileReader;
    Scanner s;
    String file;
    int[][] chess;
    
    String data = "Weißer ";
    String data2 = "Schwarzer ";
    String data3 = " auf ";        
    String data4 = "Turm ";
    String data5 = "Springer ";
    String data6 = "Laeufer ";        
    String data7 = "Koenigin ";
    String data8 = "Koenig ";
    String data9 = "Bauer ";        

    
    
    public Schachbrett() {
        chess = new int[8][8];
        this.setStartPosition();
        this.printChessField();
        if ( this.readInput().equals("eng") ){
            data = "White ";
            data2 = "Black ";
            data3 = " on ";
            data4 = "Tower ";
            data5 = "Horse ";
            data6 = "Walker ";
            data7 = "Queen ";
            data8 = "King ";
            data9 = "Knight ";            
        }
        this.printStream();
    }

    public static void main( String[] args ) {
        Schachbrett u = new Schachbrett();
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
    
    public void setStartPosition(){
       for ( int i = 0;i<8;i++ ){
            chess[1][i] = 1;
            chess[6][i] = 11;
       }
       
       for ( int i =0;i<5;i++ ){
            chess[0][i] = i+2;
            chess[7][7-i] = 10+i+2;       
       }
       
       for ( int i = 0;i<3;i++ ){
            chess[0][7-i] = i+2;
            chess[7][i] = 10+i+2;                      
       }
       return;
    }
    
    public void printChessField(){
        System.out.print( "  " );
        for ( int c = 65;c<=72;c++ ){
              System.out.printf( "%2c ",(char) c);
        }
        System.out.println();
        
        for ( int i = 0;i<8;i++ ){
           System.out.print( (i+1)+ " " );
           for( int c = 0;c<8;c++ ){
              System.out.printf( "%2d ", chess[i][c] );
           }
           System.out.println();
        }
    }
    
    public void printStream(){
        for ( int i = 0;i<8;i++ ){
           for ( int c = 0;c<8;c++ ){
              this.whichFigure( chess[i][c], i, c );
           }
        }
    }
    
    public void whichFigure( int figure, int x, int y ){
        if ( figure == 0 ) return;
        if( figure/10 == 1 ){
            System.out.print( data2 );
            figure = figure-10;
            
        }else{
            System.out.print( data );
        }
        
        switch( figure ){
            case 1: System.out.print( data9 ); break;
            case 2: System.out.print( data4 ); break;
            case 3: System.out.print( data5 ); break;
            case 4: System.out.print( data6 ) ; break;
            case 5: System.out.print( data7 ); break; 
            case 6: System.out.print( data8 ); break;    
        }
        System.out.print( data3 );
        System.out.printf( "%2c%d",(char)y+65,x+1 );
        
        System.out.println( );
    }
}