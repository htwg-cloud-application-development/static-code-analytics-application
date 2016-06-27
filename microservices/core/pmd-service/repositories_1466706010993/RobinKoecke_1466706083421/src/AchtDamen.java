import java.awt.Dimension;

public class AchtDamen{
    boolean[][] damen = new boolean[8][8];
    int anzahlDamen = 0;
    int cy = 0;
    int ci = 0;
    public AchtDamen(){
        for ( int i = 0;i<8;i++,ci++ ){
            for ( int c=0;c<8;c++,cy++ ){
                if ( checkMove(ci,cy) ){
                    damen[ci][cy] = true;
                    anzahlDamen++;
                }
            }
        }
        
        for ( int i = 0;i<8;i++ ){
           for ( int c = 0;c<8;c++ ){
              if ( damen[i][c] ) System.out.print( " x " );
              else System.out.print (" o ");
           }
           System.out.println();
        }
    }
    
    public static void main ( final String[] args ){
        AchtDamen acht = new AchtDamen();
    }
    
    public boolean checkMove( int x, int y ){
        // Diagonal nach oben links
        for ( int i=x,c=y;i>=0&&c>=0;i--,c-- ){
          if ( damen[i][c] == true ) return false;
       }
       
       // Diagonal nach unten rechts
       for ( int i=x,c=y;i<damen.length&&c<damen.length;i++,c++ ){
          if ( damen[i][c] == true ) return false;
       }
       
       // X wert nach rechts
       for ( int i=x,c=y;i<damen.length&&c<damen.length;c++ ){
          if ( damen[i][c] == true ) return false;
       }
       
       // Y wert nach unten
       for ( int i=x,c=y;i<damen.length&&c<damen.length;i++ ){
          if ( damen[i][c] == true ) return false;
       }
       
       for ( int i=x,c=y;i>=0&&c<damen.length;i--,c++ ){
           if ( damen[i][c] == true ) return false;
       }
       
       for ( int i=x,c=y;i<damen.length&&c>=0;i++,c-- ){
          if ( damen[i][c] == true ) return false;
       }
       
       for ( int i=x,c=y;c>=0;c-- ){
          if ( damen[i][c] == true ) return false;
       }      
       
       for ( int i=x,c=y;i>=0;i-- ){
          if ( damen[i][c] == true ) return false;
       }
       return true;
    }
}