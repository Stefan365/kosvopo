/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components;

import com.vaadin.ui.VerticalLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import sk.stefan.MVP.model.entity.dao.PublicRole;
import sk.stefan.MVP.model.service.PublicRoleService;
import sk.stefan.interfaces.Filterable;

/**
 *
 * @author stefan
 */
public class PublicRolesLayout extends VerticalLayout implements Filterable {
    
    private static final long serialVersionUID = 43565321L;
    
    private Map<PublicRole, PublicRoleComponent> publicRolesMap;

    private final PublicRoleService publicRoleService; 
    
    //0.konstruktor
    public PublicRolesLayout(List<PublicRole> pubRoles, PublicRoleService prs){
        
        this.publicRoleService = prs;
        initLayout(pubRoles);

        this.setSpacing(true);
        this.setMargin(true);

        
    } 
    
    /**
     */
    private void initLayout(List<PublicRole> pubRoles){
        
        PublicRoleComponent prComp;
        this.publicRolesMap = new HashMap<>();
        this.removeAllComponents();
        
        for (PublicRole pr : pubRoles){
            prComp = new PublicRoleComponent(pr, publicRoleService);
            this.publicRolesMap.put(pr, prComp);
            this.addComponent(prComp);
        }
        
    }
    
    
    /**
     * @param prs
     */
    private void setpublicRoles(List<PublicRole> prs){
        
        this.initLayout(prs);
        
    }

    @Override
    public void applyFilter(List<Integer> ids) {
        
        this.setpublicRoles(publicRoleService.findNewPublicRoles(ids));
        
    }

    @Override
    public String getTableName() {
 
//        return "t_district";
        return null;
    
    }

    
}
