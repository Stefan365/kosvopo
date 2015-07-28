/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.layouts;

import com.vaadin.ui.VerticalLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import sk.stefan.interfaces.Filterable;
import sk.stefan.mvps.view.components.MyBriefPanel;
import sk.stefan.mvps.model.entity.PublicRole;
import sk.stefan.mvps.model.service.PublicRoleService;
import sk.stefan.mvps.view.components.panContents.PUR_briefPanContent;

/**
 *
 * @author stefan
 */
public class PURs_briefLayout extends VerticalLayout implements Filterable {
    
    private static final long serialVersionUID = 43565321L;

//    servisy:
    private final PublicRoleService publicRoleService; 

    private Map<PublicRole, MyBriefPanel> pubRolesBriefMap;

    
    //0.konstruktor
    public PURs_briefLayout(List<PublicRole> pubRoles, PublicRoleService prs){
        
        this.publicRoleService = prs;
        initLayout(pubRoles);

        this.setSpacing(true);
        this.setMargin(true);

        
    } 
    
    /**
     */
    private void initLayout(List<PublicRole> pubRoles){
        
        PUR_briefPanContent prPanCont;
        this.pubRolesBriefMap = new HashMap<>();
        this.removeAllComponents();
        
        for (PublicRole pr : pubRoles){
            
            
            prPanCont = new PUR_briefPanContent(pr, publicRoleService);
            MyBriefPanel pan = new MyBriefPanel(prPanCont);
            
            this.pubRolesBriefMap.put(pr, pan);
            this.addComponent(pan);
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
                ((PUR_briefPanContent)pubRolesBriefMap.get(pr).getPanContent()).setIsActual(Boolean.TRUE);
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
