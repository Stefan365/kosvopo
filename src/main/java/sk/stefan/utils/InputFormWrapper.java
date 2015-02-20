package sk.stefan.utils;

/**
 * Wrapper, ktory zahrnuje vsetko potrebne na vytvorenie inputlaoutu danej
 * entity
 *
 * @author stefan
 * @param <E>
 */
public final class InputFormWrapper<E extends Object> {

//    private String name;
    /**
     * Trieda danej entity
     */
    private final Class<E> clsE;
    private final String buttonName;
    private final String tableName;
    
    public InputFormWrapper(Class<E> clsE, String butName, String tn){
        this.buttonName = butName;
        this.clsE = clsE;
        this.tableName = tn;
    }
    
    public String getButtonName() {
        return this.buttonName;
    }

    public Class<E> getClsE() {
        return this.clsE;
    }
    
    public String getTableName() {
        return this.tableName;
    }
}
