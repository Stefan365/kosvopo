package sk.stefan.utils;


import java.util.Random;

/**
 *
 * @author User
 */
public class Take5 {
    static int[] zoznamKariet = new int[5];
    
    
    public static final void nakrmzoznam(){
        Random rand = new Random();
        
        for(int i = 0; i<zoznamKariet.length;i++){
            int rc;
            do {
                  rc  = rand.nextInt((22)); 
            } while(jeVzozname(rc, i) == true);

            zoznamKariet[i] = rc;        
        }
    }
    
    // nextInt is normally exclusive of the top value,
    // so add 1 to make it inclusive
    
    public static final String zobraz(){
        String s = "";
        for (int i = 0; i < zoznamKariet.length; i++){
            if(i + 1 != zoznamKariet.length){
                s = s + zoznamKariet[i] + ", ";
            } else{
                s = s + zoznamKariet[i] + "";
            }
        }
        return s;
    }
    
    private static boolean jeVzozname(int x, int j){
        for (int i = 0; i <= j; i++){
            if(zoznamKariet[i] == x){
                return true;
            }
        }
        return false;
    }
}
