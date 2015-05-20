/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.service;

import com.vaadin.data.Item;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import sk.stefan.MVP.model.entity.A_UserRole;

/**
 * Obsluhuje V8, tj. univerzalny view na vkladanie resp editovanie vsetkych entit v systeme.
 *
 * @author stefan
 * @param <E>
 */
public interface UniTableService<E> {

    /**
     * Ziska nazov DB tabulky danej prisluchajucej danej triede.
     * @return 
     */
    public String getClassTableName();

    /**
     * Deaktivuje danu entitu (i so stromom zavislych entit)
     * @param entId
     * @throws java.sql.SQLException
     */
    public void deactivateWholeTreeById(Integer entId) throws SQLException;
    
    /**
     * Vytvori a vrati entitu z danej Item (item = datovy objekt ktory pouziva Vaadin a spriaha ho s grafickymi 
     * komponentami).
     * 
     * @param item
     * @param mapPar
     * @return 
     */
    public E getEntFromItem(Item item, Map<String, Class<?>> mapPar);    
    
    public E save(E ent, boolean noteChange);

    /**
     * Vrati ids vsetkych uzivatelov typu volunteer a mna ako admina.
     * Vyuziva sa pri editacii uzivatelov vo V8.
     *
     * @param adminId adminovo a_user id.
     * @return
     */
    public List<Integer> findMeAndAllVolunteers(Integer adminId);

    /**
     * Ulozi user_rolu (napr. noveho) uzivatela
     * 
     * @param urole
     */
    public void saveRole(A_UserRole urole);
    
}
