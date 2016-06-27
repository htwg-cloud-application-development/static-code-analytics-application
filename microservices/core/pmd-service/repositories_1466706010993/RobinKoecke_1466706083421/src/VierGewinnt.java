import java.util.Scanner;
public class VierGewinnt{
    int[][] feld = new int[6][7];
    
    private static int PLAYER_ONE = 1;
    private static int PLAYER_TWO = 2;
    
    static String Spieler1;
    static String Spieler2;
    
    public VierGewinnt(){
        this.printField();
        Scanner s = new Scanner( System.in );
        int userinput = 0;
        int userVar = 1;
        while( !this.checkWin(PLAYER_ONE) && !this.checkWin(PLAYER_TWO)  ){
            try{
                userinput = Integer.valueOf(s.nextLine().trim());
                if ( !this.checkUserInput( userinput-1, (userVar%2)+1 ) ) { 
                    System.out.println( "Konnte nicht gesetzt werden!" );
                }else{
                    userVar++;
                }
                
                this.printField();
                
            }catch( Exception e ){
                
            }
        }
        if( this.checkWin(PLAYER_ONE) ) System.out.println ( "Spieler "+Spieler1+" hat gewonnen!" );
        else  System.out.println ( "Spieler "+Spieler2+" hat gewonnen!" );
    }
    
    public static void main ( final String[] args ){
        Scanner s = new Scanner( System.in );
        
        try{
            System.out.println ( "Bitte Spieler1 eingeben: " );
            Spieler1 = s.nextLine();
            System.out.println ( "Bitte Spieler2 eingeben: " );
            Spieler2 = s.nextLine();
            
        }catch( Exception e ){
            
        }       
        VierGewinnt play = new VierGewinnt();
    }

    public void printField(){
        for ( int i =0;i<feld.length;i++ ){
            for ( int c =0;c<feld[i].length;c++){
                if( feld[i][c] == PLAYER_ONE ) System.out.print( " x " ); 
                else if ( feld[i][c] == PLAYER_TWO ) System.out.print( " o " );
                else System.out.print( " - " );
            }
            System.out.println();
        }
        
        for ( int i = 1;i<feld[0].length+1;i++ ) System.out.printf( " %d ",i );
        System.out.println();
    }
    
    public boolean checkUserInput( int temp, int user ){
        for( int i=feld.length-1;i>=0;i-- ){
            if ( feld[i][temp] == 0 ){
               feld[i][temp] = user;
               return true;
            }
        }
        return false;
    }
    
    public boolean checkWin( int user ){
       int pos=0;
       
       for ( int i=feld.length-1;i>=0;i-- ){
            for ( int c=0;c<feld[i].length;c++ ){
               for ( int z=0;z<4 && i-z>=0 && c+z<feld[i].length;z++ ){
                  if( feld[i-z][c+z] == user ){
                     pos++;
                     if ( pos == 4 ) return true; 
                  }else{
                     pos = 0;   
                  }
               }
            }
       }
       pos = 0;
       
       for ( int i=0;i<feld.length;i++ ){
            for ( int c=0;c<feld[i].length;c++ ){
               for ( int z=0;z<4 && i+z<feld.length && c+z<feld[i].length;z++ ){
                  if( feld[i+z][c+z] == user ){
                     pos++;
                     if ( pos == 4 ) return true; 
                  }else{
                     pos = 0;   
                  }
               }
            }
       }
       
       pos = 0;
       for ( int i=0;i<feld.length;i++ ){
           for ( int c=0;c<feld[i].length;c++ ){
              
               if( feld[i][c] == user ){
                  pos++;
                  if ( pos >= 4 ) return true;
              }
              else pos = 0;
           }
       }       
       
       return false;
    }
}