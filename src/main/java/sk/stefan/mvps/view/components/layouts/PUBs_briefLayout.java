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
import sk.stefan.mvps.model.entity.PublicBody;
import sk.stefan.mvps.model.service.PublicBodyService;
import sk.stefan.mvps.view.components.panContents.PUB_briefPanContent;
import sk.stefan.mvps.view.components.panels.MyBriefPanel;

/**
 *
 * @author stefan
 */
public class PUBs_briefLayout extends VerticalLayout implements Filterable {
    
    private static final long serialVersionUID = 43565321L;
    
//    servisy:
    private final PublicBodyService publicBodyService; 
    
//    hlavna komponenta:
    private Map<PublicBody, MyBriefPanel<PUB_briefPanContent>> publicBodiesMap;

    
    
    
    //0.konstruktor
    public PUBs_briefLayout(List<PublicBody> pubBodies, PublicBodyService pbs){
        
        this.publicBodyService = pbs;
        initLayout(pubBodies);

        this.setSpacing(true);
        this.setMargin(true);

        
    } 
    
    /**
     */
    private void initLayout(List<PublicBody> pubBodies){
        
        PUB_briefPanContent pbComp;
        MyBriefPanel<PUB_briefPanContent> pan;
        this.publicBodiesMap = new HashMap<>();
        this.removeAllComponents();
        
        for (PublicBody pb : pubBodies){
            pbComp = new PUB_briefPanContent(pb, publicBodyService);
            pan = new MyBriefPanel<>(pbComp);
            this.publicBodiesMap.put(pb, pan);
            this.addComponent(pan);
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
