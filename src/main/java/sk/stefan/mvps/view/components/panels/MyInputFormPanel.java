/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.panels;

import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Panel;
import sk.stefan.mvps.view.components.layouts.InputFormLayout;

/**
 *
 * @author stefan
 * @param <E>
 * 
 */
public class MyInputFormPanel<E> extends Panel {

    private static final long serialVersionUID = 1L;

    private final InputFormLayout<E> inputFormLy;


    public MyInputFormPanel(InputFormLayout<E> lay) {
        this.setStyleName("inputFormLayoutPanel");
        this.inputFormLy = lay;
        this.setContent(lay);
    }


    
    public InputFormLayout<E> getInputFormLayout() {
        return inputFormLy;
    }


}
