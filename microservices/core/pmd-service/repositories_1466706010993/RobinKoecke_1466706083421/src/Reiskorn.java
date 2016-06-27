import java.math.*;
// Aufgabe Reiskorn
public class Reiskorn{
    BigDecimal[] reisfeld = new BigDecimal[64];

    public Reiskorn(){
        
        for( int i=0;i<64;i++ ){
            reisfeld[i] = new BigDecimal(Double.toString(Math.pow(2,i)));
            System.out.println( reisfeld[i] );
        }
    }
    
    public static void main ( final String[] args ){
       Reiskorn k = new Reiskorn();
    }
}