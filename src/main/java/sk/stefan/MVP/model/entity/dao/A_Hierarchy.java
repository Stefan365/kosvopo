/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.entity.dao;

import java.util.List;
import java.util.Set;
import sk.stefan.filtering.HierarchySequence;

/**
 *
 * @author stefan
 */
public class A_Hierarchy {
    
    private Integer id;
    private String table_name;
    private String boss_table;

    HierarchySequence hs;
    List<String> ls;
    Set<Integer> ids;
    
//    private void checkti(){
//        ids.a
//    }

    public static final String TN = "t_hierarchy";

    
    
    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBoss_table() {
        return boss_table;
    }

    public void setBoss_table(String boss_table) {
        this.boss_table = boss_table;
    }
    
}
