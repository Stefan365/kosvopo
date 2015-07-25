/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components.layouts;

import com.vaadin.ui.VerticalLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import sk.stefan.MVP.model.entity.PublicRole;
import sk.stefan.MVP.model.service.PublicRoleService;
import sk.stefan.MVP.view.components.panContents.PUR_briefPanContent;
import sk.stefan.interfaces.Filterable;

/**
 *
 * @author stefan
 */
public class PubRolesBriefLayout extends VerticalLayout implements Filterable {
    
    private static final long serialVersionUID = 43565321L;

//    servisy:
    private final PublicRoleService publicRoleService; 

    private Map<PublicRole, PUR_briefPanContent> pubRolesBriefMap;

    
    //0.konstruktor
    public PubRolesBriefLayout(List<PublicRole> pubRoles, PublicRoleService prs){
        
        this.publicRoleService = prs;
        initLayout(pubRoles);

        this.setSpacing(true);
        this.setMargin(true);

        
    } 
    
    /**
     */
    private void initLayout(List<PublicRole> pubRoles){
        
        PUR_briefPanContent prComp;
        this.pubRolesBriefMap = new HashMap<>();
        this.removeAllComponents();
        
        for (PublicRole pr : pubRoles){
            prComp = new PUR_briefPanContent(pr, publicRoleService);
            this.pubRolesBriefMap.put(pr, prComp);
            this.addComponent(prComp);
        }
        
    }
    
    
    /**
     * @param prs
     */
    private void setpublicRoles(List<PublicRole> prs){
        
        this.initLayout(prs);
        
    }

    public void setActual(PublicRole actualPublicRole) {
        
        Integer aprId = actualPublicRole.getId();
        
        for (PublicRole pr : pubRolesBriefMap.keySet()){
            if( pr.getId().compareTo(aprId) == 0){
                pubRolesBriefMap.get(pr).setIsActual(Boolean.TRUE);
                break;
            }
        }
    }
    
    @Override
    public void applyFilter(List<Integer> ids) {
        
        this.setpublicRoles(publicRoleService.getPublicRoles(ids));
        
    }

    @Override
    public String getTableName() {
 
//        return "t_district";
        return null;
    
    }

    
    

    
}
