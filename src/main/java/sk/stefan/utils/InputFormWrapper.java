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
    private final Object objE;
    private final String buttonName;

//    public InputFormWrapper(Class<E> clsE, String butName){
//        this.setButtonName(butName);
//        this.setClsE(clsE);
//    }
    @SuppressWarnings("unchecked")
    public InputFormWrapper(E objE, String butName) {
//        this.setButtonName(butName);
//        this.setObjE(objE);
//        this.setClsE((Class<E>) objE.getClass());
        this.buttonName = butName;
        this.clsE = (Class<E>) objE.getClass();
        this.objE = objE;
    }

//    public void setName(String nm){
//        this.name = nm;
//    }
//    public void setButtonName(String nm){
//        this.buttonName = nm;
//    }
//    
//    public void setClsE(Class<E> cls){
//        this.clsE = cls;
//    }
//    
//    public void setObjE(Object o){
//        this.objE = o;
//    }
//    public String getName(){
//        return this.name;
//    }
    public String getButtonName() {
        return this.buttonName;
    }

    public Class<E> getClsE() {
        return this.clsE;
    }

    public Object getObjE() {
        return this.objE;
    }

}
