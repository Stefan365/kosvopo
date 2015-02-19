package sk.stefan.zaklad;

import java.util.ArrayList;
import java.util.List;

public class Skuska {

    private int aj;
    
    public Skuska(int i){
        aj = i;
    }
    
    public List<Integer> getList(){
        List<Integer> l =  new ArrayList<>();
        l.add(aj);
        return l;

    }
}
