/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.panContents;

import com.vaadin.event.LayoutEvents;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import sk.stefan.mvps.model.entity.PublicBody;
import sk.stefan.mvps.model.service.PublicBodyService;

/**
 *
 * @author stefan
 */
public class PUB_detPanContent extends GridLayout {

    private static final long serialVersionUID = 1L;

//    servisy: tento bude zdedeny z nadradenej komponenty.
    private final PublicBodyService publicBodyService; 
    
//    komponenty:
    private Label titleLb;
    private Label predsedaLb;
    private Label adresaLb;
    
//    hlavna entita komponenty:
    private final PublicBody pubBody;

    private final Navigator navigator;
    

    //0.konstruktor:
    public PUB_detPanContent(PublicBody pb, PublicBodyService ps) {

        this.navigator = UI.getCurrent().getNavigator();
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
