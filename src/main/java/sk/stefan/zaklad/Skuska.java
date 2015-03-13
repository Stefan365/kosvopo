package sk.stefan.zaklad;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class Skuska {

    private int aj;
    private static final Logger log = Logger.getLogger(Skuska1.class);
    
    public Skuska(int i){
        aj = i;
    }
    
    public List<Integer> getList(){
        List<Integer> la =  new ArrayList<>();
        la.add(aj);
        return la;

    }
}
