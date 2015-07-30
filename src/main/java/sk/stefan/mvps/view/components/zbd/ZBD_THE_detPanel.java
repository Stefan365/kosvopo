/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.zbd;

import com.vaadin.ui.AbstractLayout;
import sk.stefan.mvps.view.components.panels.MyDetailedPanel;

/**
 *
 * @author stefan
 * @param <E>
 */
public class ZBD_THE_detPanel<E extends AbstractLayout>  extends MyDetailedPanel<E> {
    
    private static final long serialVersionUID = 132L;
 
    public ZBD_THE_detPanel(E lay){
        
        this.setStyleName("THE_detPanel");
        this.setContent(lay);
    }

    
}
