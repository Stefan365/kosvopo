/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components.layouts;

import com.vaadin.ui.Label;

/**
 *
 * @author stefan
 */
class TitleLabel extends Label {
    
    private static final long serialVersionUID = 1L;
    
    public TitleLabel(){
        
        this.setStyleName("titleLabel");
    }

    TitleLabel(String title) {
        super.setCaption(title);
        this.setStyleName("titleLabel");
    }
    
}
