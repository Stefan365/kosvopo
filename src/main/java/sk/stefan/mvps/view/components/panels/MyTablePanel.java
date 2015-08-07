/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.panels;

import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import sk.stefan.mvps.view.components.MyTable;
import sk.stefan.mvps.view.components.layouts.InputFormLayout;

/**
 *
 * @author stefan
 * 
 */
public class MyTablePanel extends Panel {

    private static final long serialVersionUID = 1L;

    private final MyTable table;

    
    

    public MyTablePanel(MyTable tab) {
        this.setStyleName("tablePanel");
        this.table = tab;
        VerticalLayout vl = new VerticalLayout();
        vl.addComponent(table);
        this.setContent(vl);   
    }


    
    public MyTable getTable() {
        return table;
    }


}
