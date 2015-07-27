/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.panels;

import com.vaadin.ui.AbstractLayout;
import sk.stefan.mvps.view.components.MyDetailedPanel;

/**
 *
 * @author stefan
 */
public class PUR_detPanel  extends MyDetailedPanel {
    private static final long serialVersionUID = 1444L;
 
    public PUR_detPanel(AbstractLayout lay){
        
        this.setStyleName("PUR_detPanel");
        this.setContent(lay);
    }
    
}
