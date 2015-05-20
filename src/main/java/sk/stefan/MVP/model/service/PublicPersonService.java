/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.service;

import java.util.List;
import sk.stefan.MVP.model.entity.PublicPerson;

/**
 * Obsluhuje verejnu osobu.
 *
 * @author stefan
 */
public interface PublicPersonService {
    
    /**
     * @return 
     */
    public List<PublicPerson> findAll();

    /**
     * Vrati zoznam ids verejnych osob na zaklade slova zo search line.
     * @param name
     * @return 
     */
    public List<Integer> findPublicPersonsIdsByFilter(String name);
    
    /**
     * Vrati zoznam verejnych osob na zaklade ich id.
     * @param ppIds
     * @return 
     */
    public List<PublicPerson> findPublicPersons(List<Integer> ppIds);
    
}
