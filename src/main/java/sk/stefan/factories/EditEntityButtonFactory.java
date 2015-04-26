/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.factories;

import com.vaadin.ui.Button;
import sk.stefan.listeners.EditWrapper;

/**
 * Trieda, vytvara tlacitka na editaciu entit. 
 *
 *
 * @author stefan
 */
public abstract class EditEntityButtonFactory {

    private static final long serialVersionUID = 1645436L;

    
    public static Button createMyEditButton(EditWrapper<?> wrap) {

        return wrap.getB();
    
    }
    
}
