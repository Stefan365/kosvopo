/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.panContents;

import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Panel;

/**
 *
 * @author stefan
 */
public class PUP_detPanContent extends Panel {
    
    private static final long serialVersionUID = 165353L;
    
    public PUP_detPanContent(){
        
        Button b = new Button("KAROL");
        Button b1 = new Button("KAROL1");
        Button b2 = new Button("KAROL2");
        
        FormLayout ly = new FormLayout();
        ly.addComponent(b);
        ly.addComponent(b1);
        ly.addComponent(b2);
        
        
        this.setContent(ly);
    
    }
    
}
