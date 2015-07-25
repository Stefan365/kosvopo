/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components.panels;

import com.vaadin.ui.AbstractLayout;
import sk.stefan.MVP.view.components.MyDetailedPanel;

/**
 *
 * @author stefan
 */
public class VOT_detPanel extends MyDetailedPanel {
    
    private static final long serialVersionUID = 1789L;

    public VOT_detPanel(AbstractLayout lay){
        
        this.setStyleName("VOT_detPanel");
        this.setContent(lay);
    }

}
