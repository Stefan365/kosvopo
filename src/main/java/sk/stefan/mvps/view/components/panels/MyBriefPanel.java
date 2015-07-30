/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.panels;

import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Panel;

/**
 *
 * @author stefan
 * @param <E>
 */
public final class MyBriefPanel<E extends AbstractLayout> extends Panel {

    private static final long serialVersionUID = 1L;
    
    private final E panContent;
    
    public MyBriefPanel(E lay){
        
        this.setStyleName("briefPanel");
        this.panContent = lay;
        this.setContent(lay);
    }

    
    
    
    public AbstractLayout getPanContent() {
        return panContent;
    }

    
}
