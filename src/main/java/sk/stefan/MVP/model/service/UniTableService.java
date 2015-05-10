/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.service;

import com.vaadin.data.Item;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author stefan
 * @param <E>
 */
public interface UniTableService<E> {

    
    public String getClassTableName();

    public void deactivateById(Integer entId) throws SQLException;
    
    public E getObjectFromItem(Item item, Map<String, Class<?>> mapPar);    
    
    public E save(E ent);
    
}
