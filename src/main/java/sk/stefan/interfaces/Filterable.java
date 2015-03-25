/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.interfaces;

import java.util.List;

/**
 *
 * @author stefan
 */
public interface Filterable {
    
    /**
     * Aplikuj dane Ids na dany filter
     * 
     * @param ids zoznam ids entit, ktore maju byt v zozname comboboxu
     */
    public void applyFilter(List<Integer> ids);
    
    /**
     * returns table name that belongs to class of the combobox.
     * @return 
     */
    public String getTableName();
    
}
