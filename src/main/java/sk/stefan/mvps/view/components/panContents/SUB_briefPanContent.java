/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.panContents;

import com.vaadin.event.LayoutEvents;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import sk.stefan.mvps.model.entity.PublicRole;
import sk.stefan.mvps.model.entity.Subject;
import sk.stefan.mvps.model.entity.Theme;
import sk.stefan.mvps.model.service.VoteService;

/**
 *
 * @author stefan
 */
public class SUB_briefPanContent extends HorizontalLayout{
    
    private static final long serialVersionUID = 15435464L;
    
    private final Subject subject;

    private final Navigator navigator;
 
    private final VoteService voteService; 

    //graficke komponenty:
    private Label briefDescriptionLb;
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
    public SUB_briefPanContent(Subject sub, VoteService vots) {

        this.setSpacing(true);
        this.setMargin(true);

        this.navigator = UI.getCurrent().getNavigator();
        this.subject = sub;
        this.voteService = vots;
        

        this.initLayout();
        this.initListeners();
    }

    /**
     */
    private void initLayout() {
        
        this.briefDescriptionLb = new Label(subject.getBrief_description());
        this.briefDescriptionLb.setCaption("Predmet hlasovania");
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
        
        
        this.addComponents(briefDescriptionLb, descriptionLb);
        
    }

    /**
     * inicializuje vsetky listenery,
     * tj. kam sa klikne tam to skoci.
     *
     */
    private void initListeners() {

        
        //1. Listener pre cely Subejct component.
        this.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {

            private static final long serialVersionUID = 1L;

            @Override
            public void layoutClick(LayoutEvents.LayoutClickEvent event) {
                VaadinSession.getCurrent().setAttribute(Subject.class, subject);
                navigator.navigateTo("V9_SubjectView");
 
            }
        });

        
        
        //2. Listener pre Label Theme.
        this.themeInternalLy.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {

            private static final long serialVersionUID = 13L;

            @Override
            public void layoutClick(LayoutEvents.LayoutClickEvent event) {
                
                VaadinSession.getCurrent().setAttribute(Theme.class, voteService.getThemeById(subject.getTheme_id()));
                navigator.navigateTo("V10_ThemeView"); 
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
