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
 *
 * @author stefan
 * @param <E>
 */
public interface UniTableService<E> {

    
    public String getClassTableName();

    public void deactivateById(Integer entId) throws SQLException;
    
    public E getObjectFromItem(Item item, Map<String, Class<?>> mapPar);    
    
    public E save(E ent, boolean noteChange);

    /**
     *
     * @param uid
     * @return
     */
    public List<Integer> findMeAndAllVolunteers(Integer uid);

    public void saveRole(A_UserRole urole);
    
}
