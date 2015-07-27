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
import sk.stefan.mvps.model.entity.PublicPerson;
import sk.stefan.mvps.model.service.PublicPersonService;
import sk.stefan.mvps.view.components.panContents.PUP_briefPanContent;
import sk.stefan.interfaces.Filterable;

/**
 *
 * @author stefan
 */
public class PUPs_briefLayout extends VerticalLayout implements Filterable {
    
    private static final long serialVersionUID = 43565321L;
    
    private Map<PublicPerson, PUP_briefPanContent> publicPersonsMap;

    private final PublicPersonService publicPersonService; 
    
    
    //0.konstruktor
    public PUPs_briefLayout(List<PublicPerson> pubPersons, PublicPersonService pps){
        
        this.setSpacing(true);
        this.setMargin(true);

        this.publicPersonService = pps;
        
        initLayout(pubPersons);

    } 
    
    /**
     * 
     */
    private void initLayout(List<PublicPerson> pubPersons){
        
        PUP_briefPanContent ppComp;
        this.publicPersonsMap = new HashMap<>();
        this.removeAllComponents();
        
        for (PublicPerson pp : pubPersons){
            ppComp = new PUP_briefPanContent(pp, publicPersonService);
            this.publicPersonsMap.put(pp, ppComp);
            this.addComponent(ppComp);
        }
        
    }
    
    
    /**
     * @param pbs
     */
    private void setPublicBodies(List<PublicPerson> pbs){
        
        this.initLayout(pbs);
        
    }

    @Override
    public void applyFilter(List<Integer> ids) {
        
        this.setPublicBodies(publicPersonService.findPublicPersons(ids));
        
    }

    @Override
    public String getTableName() {
 
//        return "t_district";
        return null;
        
    }

    
}
