/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.filtering;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Wrapper nad postupnosti hierachii tried v nasom modeli, vzostupne
 * tj. prvy je zavisly od druheho, etc.  posledny nieje zavisly od nicoho.
 *
 * @author stefan
 */
public class HierarchySequence {
    private List<String> hierTableSequence;

    private List<ActualEntities> hierarchySequence;

    
    public HierarchySequence(List<String> hts){
        this.hierTableSequence = hts;
    }
    
    private void createHS(){
        for (String s : hierTableSequence){
            
        }
    }
    
    /**
     * vrati korenoveho sefa v danej hierarchickej postupnosti
     * @return 
     */
    public String getBigBoss(){
        int last = hierTableSequence.size() - 1;
        return this.hierTableSequence.get(last);
    }

    
    //getters & setters:
    public List<ActualEntities> getHierarchySequence() {
        return Collections.unmodifiableList(hierarchySequence);
    }
    
    public void setHierarchySequence(List<ActualEntities> hierarchySequence) {
        this.hierarchySequence = hierarchySequence;
    }

    public List<String> getHierTableSequence() {
        return hierTableSequence;
    }
    

    public void setHierTableSequence(List<String> hierTableSequence) {
        this.hierTableSequence = hierTableSequence;
    }
    
}
