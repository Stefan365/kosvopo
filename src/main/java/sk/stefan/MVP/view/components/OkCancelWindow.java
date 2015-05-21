/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import sk.stefan.listeners.OkCancelListener;

/**
 * Komponenta pre potvrzovací dialog.
 *
 * @author stefan
 *
 */
public class OkCancelWindow extends Window {
    
    private static final long serialVersionUID = 1L;

    private final Button okBt;
    private final Button cancelBt;
    private final OkCancelListener listener;
    private HorizontalLayout buttonLayout;
    private final VerticalLayout content;
    private final Label msgLb;
    private final Component comp;

    //0.
    /**
     * Konstruktor.
     *
     * @param caption nadpis okna
     * @param message zpráva, která má být zobrazena
     * @param cp komponenta, musi implementovat okCancelListener, tj. je zaroven aj listenerom.
     */
    public OkCancelWindow(String caption, String message, Component cp) {

        super(caption);
        
        this.comp = cp;
        this.listener = (OkCancelListener)cp;
        
        this.content = new VerticalLayout();
        this.setContent(content);
        this.content.setWidth(450, Sizeable.Unit.PIXELS);
        this.content.setMargin(true);
        this.content.setSpacing(true);
        setModal(true);

        this.buttonLayout = new HorizontalLayout();
        this.msgLb = new Label(message);
        this.content.addComponent(msgLb);
        this.content.addComponent(comp);
        

        this.buttonLayout = new HorizontalLayout();
        this.content.addComponent(buttonLayout);
        this.buttonLayout.setSpacing(true);

        this.okBt = new Button("Ok");
        this.buttonLayout.addComponent(okBt);

        this.okBt.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void buttonClick(Button.ClickEvent event) {
                onOk();
            }
        });

        this.cancelBt = new Button("Zrušiť");
        this.buttonLayout.addComponent(cancelBt);
        this.cancelBt.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void buttonClick(Button.ClickEvent event) {
                close();
            }
        });

        center();
    }

    /**
     * Spustenie akcie po stlačení YES.
     */
    public void onOk() {
        listener.doOkAction(null);
        close();
    }
}
