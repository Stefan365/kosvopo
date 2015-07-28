/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components;

import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Panel;

/**
 *
 * @author stefan
 */
public final class MyBriefPanel extends Panel {
    
    private static final long serialVersionUID = 1L;
    
    private AbstractLayout panContent;
    
    public MyBriefPanel(){
        this.setStyleName("briefPanel");
    }
    
    
    public MyBriefPanel(AbstractLayout lay){
        this.setStyleName("briefPanel");
        this.setPanContent(lay);
        this.setContent(lay);
    }

    public AbstractLayout getPanContent() {
        return panContent;
    }

    public void setPanContent(AbstractLayout content) {
        this.panContent = content;
    }
    
}
