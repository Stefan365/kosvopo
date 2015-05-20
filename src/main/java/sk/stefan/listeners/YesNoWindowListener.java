/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sk.stefan.listeners;

import com.vaadin.ui.Component.Event;

/**
 * Listener pre vsetky dialogove okna s vyberem yes/no
 *
 * @author stefan
 */
public interface YesNoWindowListener {
    
	public void doYesAction(Event event);
    
}

