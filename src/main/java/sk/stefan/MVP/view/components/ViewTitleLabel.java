/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components;

import com.vaadin.ui.Label;

/**
 *
 * @author stefan
 */
public class ViewTitleLabel extends Label {
    
    private static final long serialVersionUID = 1L;
    
    public ViewTitleLabel(){
        
        this.setStyleName("viewTitleLabel");
    }

    public ViewTitleLabel(String title) {
        super.setCaption(title);
        this.setStyleName("viewTitleLabel");
    }
    
}
