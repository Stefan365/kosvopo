/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.factories;

import com.vaadin.ui.Button;
import sk.stefan.wrappers.FunctionalEditWrapper;

/**
 * Trieda, vytvara tlacitka na editaciu entit.
 *
 *
 * @author stefan
 */
public abstract class EditEntityButtonFactory {

    private static final long serialVersionUID = 1645436L;


    /**
     *  
     * 
     * @param wrap This wrapper has inside some funcionality inside  
     * to make possible construct editButtonFactory as abstract class with static factory method.
     * Otherwise factory must be generic and thus, factory method cannot be static.
     * 
     * @return 
     */
    public static Button createMyEditButton(FunctionalEditWrapper<?> wrap) {

        return wrap.getB();

    }

}
