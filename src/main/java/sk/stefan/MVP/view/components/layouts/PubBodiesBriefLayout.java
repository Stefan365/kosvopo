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
import sk.stefan.MVP.model.entity.PublicBody;
import sk.stefan.MVP.model.service.PublicBodyService;
import sk.stefan.MVP.view.components.panContents.PUB_briefPanContent;
import sk.stefan.interfaces.Filterable;

/**
 *
 * @author stefan
 */
public class PubBodiesBriefLayout extends VerticalLayout implements Filterable {
    
    private static final long serialVersionUID = 43565321L;
    
    private Map<PublicBody, PUB_briefPanContent> publicBodiesMap;

    private final PublicBodyService publicBodyService; 
    
    //0.konstruktor
    public PubBodiesBriefLayout(List<PublicBody> pubBodies, PublicBodyService pbs){
        
        this.publicBodyService = pbs;
        initLayout(pubBodies);

        this.setSpacing(true);
        this.setMargin(true);

        
    } 
    
    /**
     */
    private void initLayout(List<PublicBody> pubBodies){
        
        PUB_briefPanContent pbComp;
        this.publicBodiesMap = new HashMap<>();
        this.removeAllComponents();
        
        for (PublicBody pb : pubBodies){
            pbComp = new PUB_briefPanContent(pb, publicBodyService);
            this.publicBodiesMap.put(pb, pbComp);
            this.addComponent(pbComp);
        }
        
    }
    
    
    /**
     * @param pbs
     */
    private void setPublicBodies(List<PublicBody> pbs){
        
        this.initLayout(pbs);
        
    }

    @Override
    public void applyFilter(List<Integer> ids) {
        
        this.setPublicBodies(publicBodyService.findPublicBodies(ids));
        
    }

    @Override
    public String getTableName() {
 
//        return "t_district";
        return null;
    
    }

    
}
