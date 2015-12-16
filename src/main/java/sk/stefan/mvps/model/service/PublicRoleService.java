/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.model.service;

import java.util.List;
import sk.stefan.mvps.model.entity.PublicBody;
import sk.stefan.mvps.model.entity.PublicPerson;
import sk.stefan.mvps.model.entity.PublicRole;

/**
 *
 * @author stefan
 */
public interface PublicRoleService {
  
    public List<PublicRole> getAllPublicRolesOfPublicPerson(PublicPerson pp);
    
    public List<PublicRole> getActualPublicRolesOfPublicPerson(PublicPerson pp);
    
    public String getPublicBodyName(PublicRole pubRole);
    
    public String getTenureName(PublicRole pubRole);
    
    /**
     * Vrati zoznam verejnych roli na zaklade ich ids.
     * @param ids
     * @return 
     */
    public List<PublicRole> getPublicRoles(List<Integer> ids);

    public List<Integer> findPublicRoleIdsByPubBodyId(Integer pbId);
    
    public List<Integer> findPublicRoleIdsByPubPersonId(Integer ppId);

    public List<Integer> findPublicRoleIdsByFilter(String tx);

    /**
     * Vrati aktualnu role verejne osoby v danom verejnom organe (pocita sa s tym
     * ze teoreticky moze mat aj ine funkcie v inych organoch).
     * @param pp
     * @param pb
     * @return 
     */
    public PublicRole getActualRole(PublicPerson pp, PublicBody pb);

    public PublicRole findPublicRoleById(Integer prId);

    public List<PublicRole> findByParam(String public_person_id, String id);

    public PublicRole findOne(Integer prId);

    public String getPresentationName(PublicRole pr);

    public String getPresentationName2(PublicRole pr);

    public List<PublicRole> findByTwoParams(String public_body_id, String pbId, String name, String chiefRole);

    List<PublicRole> findAllPublicRoleByPublicBodyId(Integer publicBodyId);

    PublicBody getPublicBodyForRoleId(Integer public_role_id);
}
