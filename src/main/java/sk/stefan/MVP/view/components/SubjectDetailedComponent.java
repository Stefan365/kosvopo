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
import com.vaadin.ui.UI;
import sk.stefan.MVP.model.entity.dao.PublicRole;
import sk.stefan.MVP.model.entity.dao.Subject;
import sk.stefan.MVP.model.entity.dao.Theme;
import sk.stefan.MVP.model.service.VoteService;

/**
 *
 * @author stefan
 */
public class SubjectDetailedComponent extends HorizontalLayout{
    
    private static final long serialVersionUID = 15435464L;
    
    private final Subject subject;

    private final Navigator navigator;
 
    private final VoteService voteService; 

    //graficke komponenty:
    private Label BriefDescriptionLb;
    private Label descriptionLb;
    private HorizontalLayout themeInternalLy;
    private Label themeLb;
    private HorizontalLayout navrhovatelLy;
    private Label navrhovatelLb;
//    private Theme theme;
//    private PublicRole publicRole;
    
    
    //0.konstruktor:
    /**
     * @param sub
     * @param vots
     */
    public SubjectDetailedComponent(Subject sub, VoteService vots) {

        this.setSpacing(true);
//        this.setMargin(true);

        this.navigator = UI.getCurrent().getNavigator();
        this.subject = sub;
        this.voteService = vots;
        

        this.initLayout();
        this.initListeners();
    }

    /**
     */
    private void initLayout() {
        
        this.BriefDescriptionLb = new Label(subject.getBrief_description());
        this.descriptionLb = new Label(subject.getDescription());
        
        //theme:
        this.themeInternalLy = new HorizontalLayout();
        this.themeLb = new Label();
        themeLb.setCaption(Theme.PRES_NAME);
        String themeName = voteService.getThemeNameById(subject.getTheme_id());
        themeLb.setValue(themeName);
        themeInternalLy.addComponent(themeLb);
        
        //publicRole:
        this.navrhovatelLy = new HorizontalLayout();
        this.navrhovatelLb = new Label();
        navrhovatelLb.setCaption("Navrhovateľ");
        String navrThemeName = voteService.getThemeNameById(subject.getTheme_id());
        themeLb.setValue(themeName);
        themeInternalLy.addComponent(themeLb);
        
        
        this.addComponents(BriefDescriptionLb, descriptionLb);
        
    }

    /**
     * inicializuje vsetky listenery,
     * tj. kam sa klikne tam to skoci.
     *
     */
    private void initListeners() {

        //2. Listener pre Label Theme.
        this.themeInternalLy.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {

            private static final long serialVersionUID = 13L;

            @Override
            public void layoutClick(LayoutEvents.LayoutClickEvent event) {
                
                VaadinSession.getCurrent().setAttribute(Theme.class, voteService.getThemeById(subject.getTheme_id()));
                navigator.navigateTo("V10_ThemesView"); 
            }
        });
        
        //3. Public Role Listener.
        this.navrhovatelLy.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {

            private static final long serialVersionUID = 134L;

            @Override
            public void layoutClick(LayoutEvents.LayoutClickEvent event) {
                PublicRole pr =voteService.getPublicRoleById(subject.getPublic_role_id());
                VaadinSession.getCurrent().setAttribute(PublicRole.class, pr);
                navigator.navigateTo("V5_PublicRoleView"); 
            }
        });

    }
   
}
