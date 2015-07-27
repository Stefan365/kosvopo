/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import sk.stefan.listeners.YesNoWindowListener;

/**
 * Komponenta pro potvrzovací dialog.
 *
 * @author stefan
 *
 */
public class YesNoWindow extends Window {
    
    private static final long serialVersionUID = 1L;

    private final Button yesBt;
    private final Button noBt;
    private final YesNoWindowListener listener;
    private HorizontalLayout buttonLayout;
    private final VerticalLayout content;
    private final Label msgLb;

    //0.
    /**
     * Konstruktor.
     *
     * @param caption nadpis okna
     * @param message zpráva, která má být zobrazena
     * @param listener listener pro akci YES.
     */
    public YesNoWindow(String caption, String message, YesNoWindowListener listener) {

        super(caption);

        this.listener = listener;

        content = new VerticalLayout();
        this.setContent(content);
        content.setWidth(450, Unit.PIXELS);
        content.setMargin(true);
        content.setSpacing(true);
        setModal(true);

        buttonLayout = new HorizontalLayout();
        msgLb = new Label(message);
        content.addComponent(msgLb);

        buttonLayout = new HorizontalLayout();
        content.addComponent(buttonLayout);
        buttonLayout.setSpacing(true);

        yesBt = new Button("Ano");
        buttonLayout.addComponent(yesBt);

        yesBt.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void buttonClick(Button.ClickEvent event) {
                onYes(event);
            }
        });

        noBt = new Button("Ne");
        buttonLayout.addComponent(noBt);
        noBt.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void buttonClick(Button.ClickEvent event) {
                close();
            }
        });

        center();
    }

    /**
     * Spuštění akce po stlačení YES.
     * @param event
     */
    public void onYes(Event event) {
        listener.doYesAction(event);
        close();
    }
}
