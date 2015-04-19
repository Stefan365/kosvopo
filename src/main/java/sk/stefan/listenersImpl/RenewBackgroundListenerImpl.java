/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.listenersImpl;

import sk.stefan.MVP.view.components.ZBD_InputNewEntityButtonsPoolImpl;
import sk.stefan.listeners.RenewBackgroundListener;

/**
 * Vnitřní třída implementující Listener na refresh tohoto view.
 */
public class RenewBackgroundListenerImpl implements RenewBackgroundListener {

    private final ZBD_InputNewEntityButtonsPoolImpl outer;

    public RenewBackgroundListenerImpl(final ZBD_InputNewEntityButtonsPoolImpl outer) {
        this.outer = outer;
    }

    @Override
    public void refreshBackGround() {
        //            updateTodoInfo();
    }

    @Override
    public void refreshFilters() {
        //            addBasicFilter();
        //            addHistoryFilter();
    }
    
}
