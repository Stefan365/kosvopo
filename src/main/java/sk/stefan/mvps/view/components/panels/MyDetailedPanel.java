/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.panels;

import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Panel;

/**
 *
 * @author stefan
 * @param <E>
 */
public class MyDetailedPanel<E extends AbstractLayout> extends Panel {
//public class MyDetailedPanel extends Panel {

    private static final long serialVersionUID = 1L;

    private E panContent;

//    this is here just due to entities detailed panels - but this idea probably won't be 
//    developed:
    public MyDetailedPanel() {
        this.setStyleName("detailedPanel");
    }

    public MyDetailedPanel(E lay) {
        this.setStyleName("detailedPanel");
        this.panContent = lay;
        this.setContent(lay);
    }

    public AbstractLayout getPanContent() {
        return panContent;
    }

}
