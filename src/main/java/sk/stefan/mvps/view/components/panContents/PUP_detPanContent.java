/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.panContents;

import com.vaadin.event.LayoutEvents;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import sk.stefan.mvps.model.entity.PublicPerson;
import sk.stefan.mvps.model.service.PublicPersonService;

/**
 *
 * @author stefan
 * 
 */
public class PUP_detPanContent extends GridLayout {
    
    private static final long serialVersionUID = 15435464L;
    
    private final PublicPerson pubPerson;

    private final Navigator navigator;
 
    private final PublicPersonService publicPersonService; 

    //graficke komponenty:
    private Label menoLb;
    private Label datumNarodeniaLb;
    
    //implementovat neskvor
    private Embedded photoEm;    
    

    //0.konstruktor:
    /**
     * @param pp
     * @param pps
     */
    public PUP_detPanContent(PublicPerson pp, PublicPersonService pps) {

        this.setSpacing(true);
//        this.setMargin(true);

        this.navigator = UI.getCurrent().getNavigator();
        this.pubPerson = pp;
        this.publicPersonService = pps;
        

        this.initLayout();
        this.initListener();
    }

    /**
     */
    private void initLayout() {
        
        this.menoLb = new Label(pubPerson.getPresentationName());
        this.datumNarodeniaLb = new Label("" + pubPerson.getDate_of_birth());
        
        this.addComponents(menoLb, datumNarodeniaLb);
        
    }

    /**
     *
     */
    private void initListener() {

        this.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {

            private static final long serialVersionUID = 1L;

            @Override
            public void layoutClick(LayoutEvents.LayoutClickEvent event) {
                VaadinSession.getCurrent().setAttribute(PublicPerson.class, pubPerson);
                navigator.navigateTo("V4_PublicPersonView");
 
            }
        });

    }

}
