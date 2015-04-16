package sk.stefan.utils;


import java.util.Random;

/**
 *
 * @author User
 */
public class ZDB_Take5 {
    
    private static int[] zoznamKariet = new int[5];
    
    
    public static final void nakrmzoznam(){
        Random rand = new Random();
        
        for(int i = 0; i<getZoznamKariet().length;i++){
            int rc;
            do {
                  rc  = rand.nextInt((22)); 
            } while(jeVzozname(rc, i) == true);

            getZoznamKariet()[i] = rc;        
        }
    }
    
    // nextInt is normally exclusive of the top value,
    // so add 1 to make it inclusive
    
    public static final String zobraz(){
        String s = "";
        for (int i = 0; i < getZoznamKariet().length; i++){
            if(i + 1 != getZoznamKariet().length){
                s = s + getZoznamKariet()[i] + ", ";
            } else{
                s = s + getZoznamKariet()[i] + "";
            }
        }
        return s;
    }
    
    private static boolean jeVzozname(int x, int j){
        for (int i = 0; i <= j; i++){
            if(getZoznamKariet()[i] == x){
                return true;
            }
        }
        return false;
    }

    /**
     * @return the zoznamKariet
     */
    public static int[] getZoznamKariet() {
        return zoznamKariet;
    }

    /**
     * @param aZoznamKariet the zoznamKariet to set
     */
    public static void setZoznamKariet(int[] aZoznamKariet) {
        zoznamKariet = aZoznamKariet;
    }
}
