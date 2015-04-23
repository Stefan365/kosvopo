/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import sk.stefan.MVP.model.entity.dao.VoteClassification;
import sk.stefan.MVP.model.service.ClassificationService;
import sk.stefan.MVP.model.service.VoteService;
import sk.stefan.MVP.model.serviceImpl.VoteServiceImpl;

/**
 *
 * @author stefan
 */
public class VoteClassComponent extends GridLayout {
    
    private static final long serialVersionUID = 1729L;
    
    private final ClassificationService classificationService;  
    
    //entity
    private final VoteClassification voteClass;
    
    //komponetY:
    private Label briefDescriptionLb;
    
    private Label usefulnessLb;
    
            
    public VoteClassComponent(VoteClassification vcl, ClassificationService cls){
        
        super(2,2);

        this.classificationService = cls;
        this.voteClass = vcl;
        
        this.initLayout();
        this.initListener();

    } 
    
    private void initLayout(){
        
        this.briefDescriptionLb = new Label();
        this.usefulnessLb = new Label();
        
        
        this.briefDescriptionLb.setCaption("Stručný popis");
        this.briefDescriptionLb.setValue(voteClass.getBrief_description());
        
        this.usefulnessLb.setCaption("Verejná užitočnosť");
        this.usefulnessLb.setValue(voteClass.getPublic_usefulness().getName());
        
        this.addComponent(briefDescriptionLb, 0,0);
        this.addComponent(usefulnessLb, 1,0);
        
    }
    
    
    private void initListener(){
        //
    }
    
}
