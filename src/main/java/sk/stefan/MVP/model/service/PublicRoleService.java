/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.service;

import java.util.List;
import sk.stefan.MVP.model.entity.PublicBody;
import sk.stefan.MVP.model.entity.PublicPerson;
import sk.stefan.MVP.model.entity.PublicRole;

/**
 *
 * @author stefan
 */
public interface PublicRoleService {
  
    public List<PublicRole> getAllPublicRolesOfPublicPerson(PublicPerson pp);
    
    public List<PublicRole> getActualPublicRolesOfPublicPerson(PublicPerson pp);
    
    public String getPublicBody(PublicRole pubRole);
    
    public String getTenure(PublicRole pubRole);
    
    public List<PublicRole> getPublicRoles(List<Integer> ids);
    
    public List<Integer> findPublicRoleIdsByPubBodyId(Integer pbId);
    
    public List<Integer> findPublicRoleIdsByPubPersonId(Integer ppId);

    public List<Integer> findPublicRoleIdsByFilter(String tx);

    public PublicRole getActualRoleForPublicBody(PublicPerson pp, PublicBody pb);
    
}
