/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import sk.stefan.MVP.model.entity.dao.PersonClassification;
import sk.stefan.MVP.model.service.ClassificationService;

/**
 *
 * @author stefan
 */
public class PersonClassComponent extends GridLayout {
    
    private static final long serialVersionUID = 1729L;
    
    private final ClassificationService classificationService;  

    private final PersonClassification personClass;
    
    
    //komponetY:
    private Label stabilityLb;
    
    private Label usefulnessLb;
    
            
    public PersonClassComponent(PersonClassification pcl, ClassificationService pcls){
        
        super(2,2);

        this.classificationService = pcls;
        this.personClass = pcl;
//        this.publicPerson = pp;
        
        
        this.initLayout();
        this.initListener();

    } 
    
    private void initLayout(){
        
        this.stabilityLb = new Label();
        this.usefulnessLb = new Label();
        
        this.stabilityLb.setCaption("Stabilita");
        this.stabilityLb.setValue(personClass.getStability().getName());
        
        this.usefulnessLb.setCaption("Verejná užitočnosť");
        this.usefulnessLb.setValue(personClass.getPublic_usefulness().getName());
        
        this.addComponent(stabilityLb, 0,0);
        this.addComponent(usefulnessLb, 1,0);
        
    }
    
    
    private void initListener(){
        //
    }
    
}
