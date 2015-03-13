//package sk.stefan.filtering;
//
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.Set;
//import sk.stefan.MVP.model.repo.dao.UniRepo;
//
///**
// * Wrapper na zoznam aktualnych entit.
// *
// * @author stefan
// * @param <E>
// */
//public class ActualEntity {
//    
//    //table name, example: t_location
//    private final String table_name;
//    //boss reference, example: location_id
//    private final String boss_reference; 
//    private Set<Integer> entIds;
////    private UniRepo<E> uniRepo;
////    private Class<?> clsE;
//    
//    public ActualEntity(String tn, String br, Set<Integer> ids){
//        this.table_name = tn;
//        this.entIds = ids;
//        this.boss_reference = br;
////        this.clsE = cls;
//    }
//    
//    public ActualEntity(String tn, String br){
//        this.table_name = tn;
//        this.boss_reference = br;
//        this.entIds = new HashSet<>();
//    }
//    
//    public void addId(Integer id){
//        this.entIds.add(id);
//    }
//
//    public void removeId(Integer id){
//        this.entIds.remove(id);
//    }
//
//
//    public String getTable_name() {
//        return table_name;
//    }
//
//    public Set<Integer> getEntIds() {
//        return entIds;
//    }
//
//    /**
//     * @return the boss_reference
//     */
//    public String getBoss_reference() {
//        return boss_reference;
//    }
//}
