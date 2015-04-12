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
import sk.stefan.MVP.model.entity.dao.PublicPerson;
import sk.stefan.MVP.model.service.PublicPersonService;
import sk.stefan.MVP.model.service.PublicPersonService;
import sk.stefan.interfaces.Filterable;

/**
 *
 * @author stefan
 */
public class PublicPersonsComponent extends VerticalLayout implements Filterable {
    
    private static final long serialVersionUID = 43565321L;
    
    private Map<PublicPerson, PublicPersonComponent> publicPersonsMap;

    private final PublicPersonService publicPersonService; 
    
    
    //0.konstruktor
    public PublicPersonsComponent(List<PublicPerson> pubPersons, PublicPersonService pps){
        
        this.publicPersonService = pps;
        initLayout(pubPersons);

        this.setSpacing(true);
        this.setMargin(true);

        
    } 
    
    /**
     * 
     */
    private void initLayout(List<PublicPerson> pubPersons){
        
        PublicPersonComponent ppComp;
        this.publicPersonsMap = new HashMap<>();
        this.removeAllComponents();
        
        for (PublicPerson pp : pubPersons){
            ppComp = new PublicPersonComponent(pp, publicPersonService);
            this.publicPersonsMap.put(pp, ppComp);
            this.addComponent(ppComp);
        }
        
    }
    
    /**
     * @param pb
     */
    public void addPublicPerson(PublicPerson pb){
        
        if(!publicPersonsMap.containsKey(pb)){
            
            PublicPersonComponent pbCom = new PublicPersonComponent(pb, publicPersonService);
            publicPersonsMap.put(pb, pbCom);
        }
    }
    
    /**
     * @param pb
     */
    public void removePublicPerson(PublicPerson pb){
        if(publicPersonsMap.containsKey(pb)){
            publicPersonsMap.remove(pb);
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
        
        this.setPublicBodies(publicPersonService.findNewPublicPersons(ids));
        
    }

    @Override
    public String getTableName() {
 
//        return "t_district";
        return null;
        
    }

    
}
