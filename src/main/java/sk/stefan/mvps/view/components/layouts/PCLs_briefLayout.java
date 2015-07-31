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
import sk.stefan.mvps.view.components.panels.MyBriefPanel;

/**
 *
 * @author stefan
 */
public class PCLs_briefLayout extends VerticalLayout implements Filterable {
    
    private static final long serialVersionUID = 243432565321L;
    
//    servisy:
    private final ClassificationService classificationService; 
    
//    hlavny zoznam:
    private Map<PersonClassification, MyBriefPanel<PCL_briefPanContent>> personClassMap;


    
    
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
        
        PCL_briefPanContent pclCont;
        MyBriefPanel<PCL_briefPanContent> pan;
        this.personClassMap = new HashMap<>();
        
        for (PersonClassification pcl : personClassS){
            pclCont = new PCL_briefPanContent(pcl, classificationService);
            pan = new MyBriefPanel<>(pclCont);
            this.personClassMap.put(pcl, pan);
            this.addComponent(pan);
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
