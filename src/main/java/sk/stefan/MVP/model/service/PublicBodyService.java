/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.service;

import java.util.List;
import sk.stefan.MVP.model.entity.PublicBody;

/**
 *
 * @author stefan
 */
public interface PublicBodyService {
    
//    public String getPublicBodyAddress(PublicBody pb);
    public String getPublicBodyChief(PublicBody pb);

    public List<PublicBody> findAll();

    public List<Integer> findNewPublicBodyIds(Integer distrId);
    
    public List<PublicBody> findNewPublicBodies(List<Integer> pbIds);
    
    public List<Integer> findNewPublicBodyIdsByFilter(String name);
    
    
}
