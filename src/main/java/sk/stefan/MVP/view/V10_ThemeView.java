/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.dao.A_User;
import sk.stefan.MVP.model.entity.dao.PublicRole;
import sk.stefan.MVP.model.entity.dao.Theme;
import sk.stefan.MVP.model.service.UserService;
import sk.stefan.MVP.model.service.VoteService;
import sk.stefan.MVP.model.serviceImpl.UserServiceImpl;
import sk.stefan.MVP.model.serviceImpl.VoteServiceImpl;
import sk.stefan.MVP.view.components.EditEntityButtonFactory;
import sk.stefan.MVP.view.components.InputNewEntityButtonFactory;
import sk.stefan.MVP.view.components.NavigationComponent;
import sk.stefan.MVP.view.components.ThemeDetailedComponent;
import sk.stefan.documents.DownloaderLayout;
import sk.stefan.documents.UploaderLayout;
import sk.stefan.enums.UserType;

/**
 *
 * @author stefan
 */
public final class V10_ThemeView extends VerticalLayout implements View {

    private static final long serialVersionUID = 121322L;
    private static final Logger log = Logger.getLogger(V10_ThemeView.class);

    //hlavna entita tohoto VIew
    private Theme theme;
    
    private ThemeDetailedComponent themeDetailedComp;
    private Button editThemeBt;
    
    //servisy:
    private final VoteService voteService;
    private final UserService userService;
    
    private DownloaderLayout<Theme> downoaderLayout;
    private UploaderLayout<Theme> uploaderLayout;

    private final VerticalLayout temporaryLy;
    private final NavigationComponent navComp;
    private final Navigator nav;
    
    //konstruktor:
    public V10_ThemeView() {
        
        this.nav = UI.getCurrent().getNavigator();

        navComp = NavigationComponent.createNavigationComponent();
        this.addComponent(navComp);
        
        temporaryLy = new VerticalLayout();
        this.addComponent(temporaryLy);

        this.voteService = new VoteServiceImpl();
        this.userService = new UserServiceImpl();
        
    }

    /**
     */
    private void initAllBasic(Boolean isVolunteer) {

        temporaryLy.removeAllComponents();
        
        this.initThemeDetailedComponent();
        
        temporaryLy.addComponents(themeDetailedComp);
        
        if(isVolunteer){
            
            this.initEditThemeButton();
            this.initUploadLayout();
            
        } else {
            this.initDownloadLayout();
        }

    }
    

    /**
     */
    private void initThemeDetailedComponent() {

        //voteservice nieje potrebny, preto null;
        this.themeDetailedComp = new ThemeDetailedComponent(theme, null);

    }

    /**
     * Prida tlacitko na pridavanie novej entity PublicBody.
     */
    private void initEditThemeButton() {
        
        this.editThemeBt = EditEntityButtonFactory.createMyButton(Theme.class, theme);
        
        temporaryLy.addComponent(editThemeBt);

    }

    /**
     * Inicializuje editovatelny layout s dokumentami prisluchajucimi entite PublicBody
     */
    private void initUploadLayout() {
        
        this.uploaderLayout = new UploaderLayout<>(Theme.class);
        
        temporaryLy.addComponent(uploaderLayout);
        
    }

    /**
     * Komponenta na zobrazovanie dokumentov prisluchajucich entite PublicBody.
     */
    private void initDownloadLayout() {
        
        this.downoaderLayout = new DownloaderLayout<>(Theme.class);
        
        temporaryLy.addComponent(downoaderLayout);
        
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        this.theme = VaadinSession.getCurrent().getAttribute(Theme.class);
        
        A_User user = VaadinSession.getCurrent().getAttribute(A_User.class);

        UserType utype = userService.getUserType(user);
                
        Boolean isVolunteer = Boolean.FALSE;
        if (user != null){
            //moze byt dobrovolnik, alebo admin.
            isVolunteer = ((UserType.USER).equals(utype) || (UserType.ADMIN).equals(utype));
        }
        
        if (theme != null){
            initAllBasic(isVolunteer);
        }

    }



}
