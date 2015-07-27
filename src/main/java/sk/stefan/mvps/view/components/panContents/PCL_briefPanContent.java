/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.panContents;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import sk.stefan.mvps.model.entity.PersonClassification;
import sk.stefan.mvps.model.service.ClassificationService;

/**
 * Komponenta na zobrazonie klasifikacie verejnej osoby.
 * 
 * @author stefan
 */
public class PCL_briefPanContent extends GridLayout {
    
    private static final long serialVersionUID = 1729L;
    
    private final ClassificationService classificationService;  

    private final PersonClassification personClass;
    
    
    //komponetY:
    private Label stabilityLb;
    
    private Label usefulnessLb;
    
            
    public PCL_briefPanContent(PersonClassification pcl, ClassificationService pcls){
        
        super(2,2);

        this.classificationService = pcls;
        this.personClass = pcl;
        
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
