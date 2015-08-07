/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.panels;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author stefan
 * @param <E>
 * 
 */
public class MyUniPanel<E extends AbstractComponent> extends Panel {

    private static final long serialVersionUID = 1L;

    private final E myContent;

    
    
    public MyUniPanel(E cont) {
        
        this.setStyleName("uniPanel");
        this.setCaption("uni Panel");
        this.myContent = cont;
        VerticalLayout vl = new VerticalLayout();
        vl.addComponent(myContent);
        this.setContent(vl);   
    }


    
    public E getMyContent() {
        return myContent;
    }


}
