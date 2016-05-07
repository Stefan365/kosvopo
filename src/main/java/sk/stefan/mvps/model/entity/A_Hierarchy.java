/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.model.entity;

import lombok.Data;


/**
 * Trieda zachytavajuca hierarchicke vztahy mezdi statnymi entitami. 
 * Jej Vyhodou je, ze pokial chceme niektory hierarchicky vztah ignorovat,
 * prote ho pomocou tejto triedy nezapiseme.
 * 
 * @author stefan
 */
@Data
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

}
