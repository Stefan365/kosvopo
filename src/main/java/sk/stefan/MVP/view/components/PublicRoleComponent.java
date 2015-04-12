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
import sk.stefan.MVP.model.entity.dao.PublicRole;
import sk.stefan.MVP.model.service.PublicRoleService;

/**
 *
 * @author stefan
 */
public class PublicRoleComponent extends HorizontalLayout {

    
    
    private static final long serialVersionUID = 1L;

    private final PublicRole pubRole;

    private final Navigator navigator;

    //service, ktory bude zdedeny z nadradenej komponenty.
    private final PublicRoleService publicRoleService; 

    //graficke komponenty:
    private Label nameLb;
    private Label pubBodyLb;
    private Label tenureLb;
    
    

    //0.konstruktor:
    public PublicRoleComponent(PublicRole pb, PublicRoleService ps) {

        this.navigator = NavigationComponent.getNavigator();
        this.pubRole = pb;
        this.publicRoleService = ps;

        this.initLayout();
        this.initLayoutListener();
    }

    /**
     */
    private void initLayout() {
        
        this.setSpacing(true);
//        this.setMargin(true);
        
        
        this.nameLb = new Label(pubRole.getPresentationName());
        this.pubBodyLb = new Label(publicRoleService.getPublicBody(pubRole));
        this.tenureLb = new Label(publicRoleService.getTenure(pubRole));
        
        this.addComponents(nameLb, pubBodyLb, tenureLb);
        
    }

    /**
     *
     */
    private void initLayoutListener() {

        this.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {

            private static final long serialVersionUID = 1L;

            @Override
            public void layoutClick(LayoutEvents.LayoutClickEvent event) {
                VaadinSession.getCurrent().setAttribute(PublicRole.class, pubRole);
                navigator.navigateTo("V5_PublicRoleView");
 
            }
        });

    }
}
