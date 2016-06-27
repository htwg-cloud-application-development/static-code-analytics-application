import java.util.*;

/**
 * Created by Filzlsaus on 05.04.2016.
 */
public class Test {
    public static void main( String[] args ){
        Scanner s = new Scanner(System.in);
        // Aufgabe 4
        int winUsers;
        int HTWGstudents;
        int EUcitizen;
        long humans;
        byte timeOnSchool;
        double classDiff;
        boolean leapYear;

        //Aufgabe 5
        winUsers = 40;
        HTWGstudents = 3000;
        EUcitizen = 500000000;
        humans = Long.valueOf("7000000000");
        timeOnSchool = 11;
        classDiff = 1;
        leapYear = true;

        // Aufgabe 6
        System.out.println (" Aufgabe 6 " );
        System.out.printf("%.5f%%\n",((double)100/(humans/EUcitizen)));

        // Aufgabe 7
        System.out.println (" Aufgabe 7 " );
        for ( int i= 0;i<4;i++ ) {
            System.out.println(1<<i);
            System.out.println(Math.pow(8,i));
            System.out.println(Math.pow(10,i));
            System.out.println(Math.pow(16,i));
            System.out.println(Math.pow(60,i));
        }

        // Aufgabe 8
        System.out.println (" Aufgabe 8 " );
        String[] name;
        String lastName;
        System.out.println( "Kevin Heim" );
        String input="";
        boolean notok=true;

        while(notok){
            input = "";
            try {
                input = s.nextLine();

                if ( input.matches(("^quit") )){
                    System.out.println( "Programm beendet" );
                    s.close();
                    System.exit(0);
                } else if ( !(input.matches("^(?:([a-zA-Z\\s]))+ (?:([a-zA-Z\\s]))+$"))){
                    System.out.println( "Falsche Eingabe! " );
                } else{
                    notok = false;
                }

            }catch( NoSuchElementException e ){
                s.close();
                System.exit(0);
            }
        }

        name = input.split(" ");
        s.close();

        String names = "";
        for ( int i = 0; i <= name.length-2;i++ )names += name[i]+" ";

        System.out.println( names+ " "+ name[name.length-1]);
        System.out.println( name[name.length-1] + " " + names );

    }
}
