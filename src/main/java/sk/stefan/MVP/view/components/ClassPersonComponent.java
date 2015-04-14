/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components;

import com.vaadin.ui.GridLayout;
import sk.stefan.MVP.model.entity.dao.PersonClassification;
import sk.stefan.MVP.model.entity.dao.PublicPerson;
import sk.stefan.MVP.model.service.ClassificationService;
import sk.stefan.MVP.model.serviceImpl.ClassificationServiceImpl;

/**
 *
 * @author stefan
 */
public class ClassPersonComponent extends GridLayout {
    
    private static final long serialVersionUID = 1729L;
    
    private PersonClassification personClass;
    
    private final PublicPerson publicPerson;
    
    private final ClassificationService classificationService;  
            
    public ClassPersonComponent(PublicPerson pp){
        
        super(2,2);
        this.publicPerson = pp;
        this.classificationService = new ClassificationServiceImpl();
        
    } 
    
    private void initPersonClass(){
        
        
        
        
    }
    
}
