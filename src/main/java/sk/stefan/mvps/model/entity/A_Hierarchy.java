/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.model.entity;


/**
 * Trieda zachytavajuca hierarchicke vztahy mezdi statnymi entitami. 
 * Jej Vyhodou je, ze pokial chceme niektory hierarchicky vztah ignorovat,
 * prote ho pomocou tejto triedy nezapiseme.
 * 
 * @author stefan
 */
public class A_Hierarchy {
    
    private Integer id;
    private String table_name;
    private String boss_table;
    private String boss_reference;
    private Boolean visible;


    public static final String TN = "a_hierarchy";
    public A_Hierarchy(){
        
    }
    public A_Hierarchy(String tn, String btn, String bref){
        
        this.table_name = tn;
        this.boss_table = btn;
        this.boss_reference = bref;
    
    }
    
    public static String getTN() {
        return TN;
    }

    
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

    public Boolean getVisible() {return visible;}

    public void setVisible(Boolean visible) {this.visible = visible;}

    /**
     * @return the boss_reference
     */
    public String getBoss_reference() {
        return boss_reference;
    }

    /**
     * @param boss_reference the boss_reference to set
     */
    public void setBoss_reference(String boss_reference) {
        this.boss_reference = boss_reference;
    }

}
