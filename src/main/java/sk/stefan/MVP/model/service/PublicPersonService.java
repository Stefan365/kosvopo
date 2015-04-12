/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.service;

import java.util.List;
import sk.stefan.MVP.model.entity.dao.PublicPerson;

/**
 *
 * @author stefan
 */
public interface PublicPersonService {
    
    public List<PublicPerson> findAll();

    public List<Integer> findNewPublicPersonsIds(String name);
    
    public List<PublicPerson> findNewPublicPersons(List<Integer> ppIds);
    
}
