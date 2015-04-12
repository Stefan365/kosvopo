/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components;

import com.vaadin.event.LayoutEvents;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import sk.stefan.MVP.model.entity.dao.PublicBody;
import sk.stefan.MVP.model.service.PublicBodyService;
import sk.stefan.MVP.model.serviceImpl.PublicBodyServiceImpl;

/**
 *
 * @author stefan
 */
public class PublicBodyComponent extends HorizontalLayout {

    
    
    private static final long serialVersionUID = 1L;

    private final PublicBody pubBody;

    private final Navigator navigator;

    //service, ktory bude zdedeny z nadradenej komponenty.
    private final PublicBodyService publicBodyService; 
    //graficke komponenty:
    private Label titleLb;
    private Label predsedaLb;
    private Label adresaLb;
    
    

    //0.konstruktor:
    public PublicBodyComponent(PublicBody pb, PublicBodyService ps) {

        this.navigator = NavigationComponent.getNavigator();
        this.pubBody = pb;
        this.publicBodyService = ps;

        this.initLayout();
        this.initListener();
    }

    /**
     */
    private void initLayout() {
        
        this.setSpacing(true);
//        this.setMargin(true);
        
        
        this.titleLb = new Label(pubBody.getPresentationName());
        this.predsedaLb = new Label(publicBodyService.getPublicBodyChief(pubBody));
        this.adresaLb = new Label("Na karasiny 67/5");
        
        this.addComponents(titleLb, predsedaLb, adresaLb);
        
    }

    /**
     *
     */
    private void initListener() {

        this.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {

            private static final long serialVersionUID = 1L;

            @Override
            public void layoutClick(LayoutEvents.LayoutClickEvent event) {
                VaadinSession.getCurrent().setAttribute(PublicBody.class, pubBody);
                navigator.navigateTo("V3_PublicBodyView");
 
            }
        });

    }
}
