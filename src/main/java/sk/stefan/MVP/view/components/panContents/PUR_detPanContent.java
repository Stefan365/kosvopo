/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components.panContents;

import com.vaadin.event.LayoutEvents;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import sk.stefan.MVP.model.entity.PublicRole;
import sk.stefan.MVP.model.service.PublicRoleService;

/**
 *
 * @author stefan
 */
public class PUR_detPanContent extends HorizontalLayout {

    
    
    private static final long serialVersionUID = 1L;

    private final PublicRole pubRole;

    private final Navigator navigator;
    
    private Boolean isActual = Boolean.FALSE;

    //service, ktory bude zdedeny z nadradenej komponenty.
    private final PublicRoleService publicRoleService; 

    //graficke komponenty:
    private Label nameLb;
    
    

    //0.konstruktor:
    public PUR_detPanContent(PublicRole pb, PublicRoleService ps) {

        this.navigator = UI.getCurrent().getNavigator();
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
        
        this.addComponents(nameLb);
        
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

    public Boolean getIsActual() {
        return isActual;
    }

    public void setIsActual(Boolean isActual) {
        this.isActual = isActual;
    }
}
