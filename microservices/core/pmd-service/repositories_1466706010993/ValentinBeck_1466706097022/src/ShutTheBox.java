
	import java.util.*;
	//import java.io.File;

	class ShutTheBox {

	    static final int MAX_N = 22; 
	    static final int MAX_VAL = 22;
	    static int N = MAX_N;    
	    static ArrayList<ArrayList<Integer>> choices;

	   //gibt true aus, wenn eine zahl p markiert ist
	    public static boolean unmarked(int code, int p) {
	        return (code & (1 << (p-1))) > 0;   // bit is set
	    }
	    
        // wird geprüft, ob die Zahlen schon markiert wurden 
	    public static boolean legal(int code, int turn) {
	        return ((code & turn) == turn);  
	    }

	    
	    public static int markPiece(int code, int p) {   
	        return code | (1 << (p-1));   
	    }

	   
	    public static int reduce(int code, int turn) {   
	        return (code ^ turn);   
	    }

	 
	    public static int numMarked(int code) {
	        int total = 0;
	        for (int p=1; p <= N; p++)
	            if (!unmarked(code,p))
	                total++;
	        return total;
	    }

	    public static int pegSum(int code) {
	        int total = 0;
	        for (int p=1; p <= N; p++)
	            if (unmarked(code,p))
	                total += p;
	       
	        return total;
	    }

	   
	    public static void initChoices() {
	        choices = new ArrayList<ArrayList<Integer>>();
	        for (int j=0; j < 1+MAX_VAL; j++)
	            choices.add(new ArrayList<Integer>());

	        int half = MAX_VAL / 2;
	        int halfcode = 1 << half;
	   
	        for (int code = 0; code < halfcode; code++) {
	            int total = pegSum(code);
	            if (total <= MAX_VAL) {
	                choices.get(total).add(code);
	                for (int p = half+1; p <= MAX_VAL; p++) {
	                    if (total + p <= MAX_VAL)
	                        choices.get(total+p).add(markPiece(code,p));
	                    else
	                        break;
	                }
	            }
	        }
	    }

	    public static void main(String[] args) throws java.io.FileNotFoundException {
	        Scanner in = new Scanner(System.in);
	        initChoices();

	        int game = 1;
	        while (true) {
	            ArrayList<Integer> turns = new ArrayList<Integer>();
	            N = in.nextInt();
	            int T = in.nextInt();
	            if (N == 0) break;

	            for (int k=0; k < T; k++)
	                turns.add(in.nextInt());

	            Set<Integer> reachable = new HashSet<Integer>();
	            int initial = (1 << N) - 1;   // alle unmarkierte zahlen werden gespeichert
	            int mostMarked = 0;
	            reachable.add(initial);

	            for (int t=0; t < T; t++) {
	                int turn = turns.get(t);
	                Set<Integer> newReach = new HashSet<Integer>();

	                for (int curCode : reachable) {
	                    for (int k = 0; k < choices.get(turn).size(); k++) {
	                        int subset = choices.get(turn).get(k);
	                        if (legal(curCode,subset)) {
	                            int newCode = reduce(curCode,subset);
	                            newReach.add(newCode);
	                            int score = numMarked(newCode);
	                            if (score > mostMarked)
	                                mostMarked = score;
	                        }
	                    }
	                }
	                reachable = newReach;
	                //gibt aus, wenn es keine Zahen mehr geöffnet sind (0)
	                if (reachable.size() == 0) {
	                	System.out.println("YOU SHUT THE BOX !!!");
	                break; 
	                }
	            }

	            System.out.println("Game " + game++ + ": " + mostMarked);
	        }
	    }
	}


