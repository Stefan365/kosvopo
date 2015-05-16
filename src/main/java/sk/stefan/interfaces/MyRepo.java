package sk.stefan.interfaces;

import java.util.List;

public interface MyRepo<T> {

    // 1.
    /**
     * Najde vsetky entity daneho typu, tj. T.
     * 
     * @return 
     */
    public List<T> findAll();

    // 2.
    /**
     * Najde prave 1 entitu, podla ID.
     * @param id
     * @return 
     */
    public T findOne(Integer id);
    

    // 3.A
    /**
     * Vyhlada zoznam entit, splnajucich danu podmienku.
     * 
     * @param paramName
     * @param paramValue
     * @return 
     */
    public List<T> findByParam(String paramName, String paramValue);

    // 3.A
    /**
     * Vyhlada zoznam entit, splnajucich danu podmienku.
     * 
     * @param p1Name
     * @param p1Value
     * @param p2Name
     * @param p2Value
     * @return 
     */
    public List<T> findByTwoParams(String p1Name, String p1Value, 
            String p2Name, String p2Value);

    
    // 4.
    /**
     * Zahrna update i vytvorenie novej entity.
     *
     * @param ent
     * @param noteChange
     * @return
     */
    public T save(T ent, boolean noteChange);
    

//    // 5.
//    /**
//     * Zmaze entitu z databazy.
//     * @param ent
//     * @return 
//     */
//    public boolean delete(T ent);

    //5.B
    /**
     * Deaktivuje entity, cely strom potomkov, pri deaktivacii danej entity.
     *
     * @param ent
     * @param noteChange
     * @return
     */
    public boolean deactivateOneOnly(T ent, boolean noteChange);

    // 6.
    /**
     * Zkopituje entitu do novej entity s totoznym vnutornym obsahom.
     * 
     * 
     * @param entFrom
     * @param entTo
     * @return 
     */
    public boolean copy(T entFrom, T entTo);

}
