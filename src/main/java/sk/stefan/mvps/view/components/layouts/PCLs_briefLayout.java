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
import sk.stefan.mvps.model.entity.PersonClassification;
import sk.stefan.mvps.model.service.ClassificationService;
import sk.stefan.mvps.view.components.panContents.PCL_briefPanContent;

/**
 *
 * @author stefan
 */
public class PCLs_briefLayout extends VerticalLayout implements Filterable {
    
    private static final long serialVersionUID = 243432565321L;
    
    private Map<PersonClassification, PCL_briefPanContent> personClassMap;

    private final ClassificationService classificationService; 
    
    //0.konstruktor
    public PCLs_briefLayout(List<PersonClassification> personClass, ClassificationService pcls){

        this.setSpacing(true);
        this.setMargin(true);
        
        this.classificationService = pcls;
        initLayout(personClass);
    } 
    
    /**
     */
    private void initLayout(List<PersonClassification> personClassS){

        this.removeAllComponents();
        
        PCL_briefPanContent pclComp;
        this.personClassMap = new HashMap<>();
        
        for (PersonClassification pcl : personClassS){
            pclComp = new PCL_briefPanContent(pcl, classificationService);
            this.personClassMap.put(pcl, pclComp);
            this.addComponent(pclComp);
        }
        
    }
    
    
    /**
     * @param pbs
     */
    private void setPersonClass(List<PersonClassification> pcls){
        
        this.initLayout(pcls);
        
    }

    @Override
    public void applyFilter(List<Integer> ids) {
        
        this.setPersonClass(classificationService.findNewPersonClass(ids));
        
    }

    /**
     * V tejto komponente nevyuzita metoda.
     */
    @Override
    public String getTableName() {
 
        return null;
    
    }

    
}
