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
public class MyBriefPanel extends Panel {
    
    private static final long serialVersionUID = 1L;
    
    public MyBriefPanel(){
        this.setStyleName("briefPanel");
    }
    
    
    public MyBriefPanel(AbstractLayout lay){
        this.setStyleName("briefPanel");
        this.setContent(lay);
    }
    
}
