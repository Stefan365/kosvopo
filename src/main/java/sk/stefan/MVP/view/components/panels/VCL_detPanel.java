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
public class VCL_detPanel  extends MyDetailedPanel {
    private static final long serialVersionUID = 1555L;
    
    public VCL_detPanel(AbstractLayout lay){
        
        this.setStyleName("VCL_detPanel");
        this.setContent(lay);
    }

    
}
