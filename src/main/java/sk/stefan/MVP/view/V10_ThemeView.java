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
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.A_User;
import sk.stefan.MVP.model.entity.Theme;
import sk.stefan.MVP.model.service.UserService;
import sk.stefan.MVP.model.service.VoteService;
import sk.stefan.MVP.model.serviceImpl.UserServiceImpl;
import sk.stefan.MVP.model.serviceImpl.VoteServiceImpl;
import sk.stefan.factories.EditEntityButtonFactory;
import sk.stefan.MVP.view.components.NavigationComponent;
import sk.stefan.MVP.view.components.ThemeDetailedComponent;
import sk.stefan.MVP.view.components.documents.DownloaderLayout;
import sk.stefan.MVP.view.components.documents.UploaderLayout;
import sk.stefan.enums.UserType;
import sk.stefan.wrappers.EditWrapper;

/**
 *
 * @author stefan
 */
public final class V10_ThemeView extends VerticalLayout implements View {

    private static final long serialVersionUID = 121322L;
    private static final Logger log = Logger.getLogger(V10_ThemeView.class);

    //servisy:
    private final VoteService voteService;
    private final UserService userService;

    //hlavna entita tohoto VIew
    private Theme theme;
    
    
    //komponenty:
    private ThemeDetailedComponent themeDetailedComp;
    private Button editThemeBt;
    
    private DownloaderLayout<Theme> downoaderLayout;
    private UploaderLayout<Theme> uploaderLayout;

    private final VerticalLayout temporaryLy;
    private final NavigationComponent navComp;
    private final Navigator nav;
    
    //konstruktor:
    public V10_ThemeView() {
        
        this.setMargin(true);
        this.setSpacing(true);
                
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
    
    private void setThemeValue(Theme th) {
        theme = th;
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
        
        EditWrapper<Theme> ew = new EditWrapper<>(editThemeBt, Theme.class, theme);
        this.editThemeBt = EditEntityButtonFactory.createMyEditButton(ew);
        
        temporaryLy.addComponent(editThemeBt);

    }

    /**
     * Inicializuje editovatelny layout s dokumentami prisluchajucimi entite PublicBody
     */
    private void initUploadLayout() {
        
        this.uploaderLayout = new UploaderLayout<>(Theme.class, this.theme);
        
        temporaryLy.addComponent(uploaderLayout);
        
    }
    

    /**
     * Komponenta na zobrazovanie dokumentov prisluchajucich entite PublicBody.
     */
    private void initDownloadLayout() {
        
        this.downoaderLayout = new DownloaderLayout<>(Theme.class, this.theme);
        
        temporaryLy.addComponent(downoaderLayout);
        
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
//        Notification.show("ThemeView");
        
        Theme th = VaadinSession.getCurrent().getAttribute(Theme.class);
        
        A_User user = VaadinSession.getCurrent().getAttribute(A_User.class);
        
        Boolean isVolunteer = Boolean.FALSE;
        if (user != null){
            UserType utype = userService.getUserType(user);
            //moze byt dobrovolnik, alebo admin.
            isVolunteer = ((UserType.VOLUNTEER).equals(utype) || (UserType.ADMIN).equals(utype));
        }
//        Notification.show("ThemeView, theme is null" + (th == null));

        if (th != null){
            setThemeValue(th);
            initAllBasic(isVolunteer);
        } else {
            UI.getCurrent().getNavigator().navigateTo("V10s_ThemesView");
        }

    }

    


}
