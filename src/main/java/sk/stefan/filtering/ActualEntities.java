package sk.stefan.filtering;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Wrapper na zoznam aktualnych entit.
 *
 * @author stefan
 */
public class ActualEntities {
    private final String table_name;
    private Set<Integer> entIds;
    
    public ActualEntities(String tn, Set<Integer> setIds){
        this.table_name = tn;
        this.entIds = setIds;
    }
    
    public ActualEntities(String tn){
        this.table_name = tn;
        this.entIds = new HashSet<>();
    }
    
    public void addId(Integer id){
        this.entIds.add(id);
    }

    public void removeId(Integer id){
        this.entIds.remove(id);
    }


    public String getTable_name() {
        return table_name;
    }

    public Set<Integer> getEntIds() {
//        return Collections.unmodifiableSet(entIds);
        return entIds;
    }
}
