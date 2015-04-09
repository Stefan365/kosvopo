/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.HorizontalLayout;
import sk.stefan.MVP.model.entity.dao.PublicBody;

/**
 *
 * @author stefan
 */
public class PublicBodyComponent extends HorizontalLayout {
    
    private static final long serialVersionUID = 1L;
    
    private final PublicBody pubBody;
    
    private final Navigator navigator;
    
    
    //0.konstruktor:
    public PublicBodyComponent(PublicBody pb){
        
        this.navigator = NavigationComponent.getNavigator();
        this.pubBody = pb;
            
    }
    
    /**
     */
    private void initLayout(){
        
    }
}
