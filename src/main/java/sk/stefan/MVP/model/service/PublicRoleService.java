/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.service;

import java.util.List;
import sk.stefan.MVP.model.entity.dao.PublicPerson;
import sk.stefan.MVP.model.entity.dao.PublicRole;

/**
 *
 * @author stefan
 */
public interface PublicRoleService {
  
    public List<PublicRole> getAllPublicRolesOfPublicPerson(PublicPerson pp);
    
    public List<PublicRole> getActualPublicRolesOfPublicPerson(PublicPerson pp);
    
    public String getPublicBody(PublicRole pubRole);
    
    public String getTenure(PublicRole pubRole);
    

}
