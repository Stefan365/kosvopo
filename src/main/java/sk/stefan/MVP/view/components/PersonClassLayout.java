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
import sk.stefan.MVP.model.entity.dao.PersonClassification;
import sk.stefan.MVP.model.service.ClassificationService;
import sk.stefan.interfaces.Filterable;

/**
 *
 * @author stefan
 */
public class PersonClassLayout extends VerticalLayout implements Filterable {
    
    private static final long serialVersionUID = 243432565321L;
    
    private Map<PersonClassification, PersonClassComponent> personClassMap;

    private final ClassificationService classificationService; 
    
    //0.konstruktor
    public PersonClassLayout(List<PersonClassification> personClass, ClassificationService pcls){

        this.setSpacing(true);
        this.setMargin(true);
        
        this.classificationService = pcls;
        initLayout(personClass);
    } 
    
    /**
     */
    private void initLayout(List<PersonClassification> personClassS){

        this.removeAllComponents();
        
        PersonClassComponent pclComp;
        this.personClassMap = new HashMap<>();
        
        for (PersonClassification pcl : personClassS){
            pclComp = new PersonClassComponent(pcl, classificationService);
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

    @Override
    public String getTableName() {
 
//        return "t_district";
        return null;
    
    }

    
}
