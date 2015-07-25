/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components.layouts;

import sk.stefan.MVP.view.components.ViewTitleLabel;
import com.vaadin.ui.VerticalLayout;

/**
 * This Layout is the base of each View. It need to be different from plain
 * Vertical Layout, due to graphical design of the component.
 *
 * @author stefan
 */
public class ViewLayout extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    private final ViewTitleLabel titleLb;

    /**
     *
     * @param titleStr
     */
    public ViewLayout(String titleStr) {
        this.setStyleName("ViewLayout");
        this.titleLb = new ViewTitleLabel(titleStr);
        this.addComponent(titleLb);

    }

    /**
     *
     */
    public ViewLayout() {
        this.setStyleName("ViewLayout");
        this.titleLb = new ViewTitleLabel();
        this.addComponent(titleLb);
        
        this.initLayout();

    }

    /**
     * Initialization of the layout.
     */
    private void initLayout() {
        this.setMargin(true);
        this.setSpacing(true);

    }

}
