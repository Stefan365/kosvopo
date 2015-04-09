/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components;

import com.vaadin.ui.VerticalLayout;
import java.util.List;
import sk.stefan.MVP.model.entity.dao.PublicBody;
import sk.stefan.MVP.model.repo.dao.UniRepo;

/**
 *
 * @author stefan
 */
public class PublicBodiesComponent extends VerticalLayout {
    
    private List<PublicBodyComponent> publicBodyComponents;
    
    private List<PublicBody> publicBodies;
    
    private UniRepo<PublicBody> pubBodyRepo;
    
    //0.konstruktor
    public PublicBodiesComponent(){
        
        this.pubBodyRepo = new UniRepo<>(PublicBody.class);
        
        this.publicBodies = pubBodyRepo.findAll();
        
        this.initLayout();
        
    } 
    
    /**
     */
    private void initLayout(){
        
        PublicBodyComponent pbComp;
        
        for (PublicBody pb : publicBodies){
            pbComp = new PublicBodyComponent(pb);
            this.addComponent(pbComp);
        }
        
    }
    
    
}
