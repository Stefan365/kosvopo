/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.service;

import java.util.List;
import sk.stefan.MVP.model.entity.PublicBody;

/**
 * Obsluhuje Verejny organ.
 * 
 * @author stefan
 */
public interface PublicBodyService {

    /**
     * Ziska predsedu verejneho organu.
     * 
     * @param pb
     * @return 
     */
    public String getPublicBodyChief(PublicBody pb);

    /**
     * 
     * @return 
     */
    public List<PublicBody> findAll();

    /**
     * Najde vsetky verejne organy v danom okrese. 
     * 
     * @param distrId
     * @return 
     */
    public List<Integer> findPublicBodyIdsByDistrictId(Integer distrId);
    
    /**
     * Na zaklade id vrati odpovedajuci zoznam verejnych organov.
     * @param pbIds
     * @return 
     */
    public List<PublicBody> findPublicBodies(List<Integer> pbIds);
    
    /**
     * Hlavna vyhladavacia metoda. Ziska id verejnych organov na zaklade slova z search line.
     * @param name
     * @return 
     */
    public List<Integer> findPublicBodyIdsByFilter(String name);
    
    
}
