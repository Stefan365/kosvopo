/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.A_User;
import sk.stefan.MVP.model.entity.Subject;
import sk.stefan.MVP.model.entity.Theme;
import sk.stefan.MVP.model.service.UserService;
import sk.stefan.MVP.model.service.VoteService;
import sk.stefan.MVP.model.serviceImpl.UserServiceImpl;
import sk.stefan.MVP.model.serviceImpl.VoteServiceImpl;
import sk.stefan.MVP.view.components.ThemeDetailedComponent;
import sk.stefan.MVP.view.components.documents.DownloaderLayout;
import sk.stefan.MVP.view.components.documents.UploaderLayout;
import sk.stefan.enums.UserType;
import sk.stefan.factories.EditEntityButtonFactory;
import sk.stefan.wrappers.FunctionalEditWrapper;

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
    
    private final EditEntityButtonFactory<Theme> editButtonFactory;
        
    //komponenty:
    private ThemeDetailedComponent themeDetailedComp;
    private Button editThemeBt;
    private DownloaderLayout<Theme> downoaderLayout;
    private UploaderLayout<Theme> uploaderLayout;

    //konstruktor:
    public V10_ThemeView() {
        
        this.setMargin(true);
        this.setSpacing(true);
                
        this.voteService = new VoteServiceImpl();
        this.userService = new UserServiceImpl();
        this.editButtonFactory = new  EditEntityButtonFactory<>();
    }

    /**
     */
    private void initAllBasic(Boolean isVolunteer) {

        this.removeAllComponents();
        this.initThemeDetailedComponent();
        this.addComponents(themeDetailedComp);
        
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
        
        FunctionalEditWrapper<Theme> ew = new FunctionalEditWrapper<>(editThemeBt, Theme.class, theme);
        this.editThemeBt = editButtonFactory.createMyEditButton(ew);
        
        this.addComponent(editThemeBt);

    }

    /**
     * Inicializuje editovatelny layout s dokumentami prisluchajucimi entite PublicBody
     */
    private void initUploadLayout() {
        
        this.uploaderLayout = new UploaderLayout<>(Theme.class, this.theme);
        this.addComponent(uploaderLayout);
    }
    

    /**
     * Komponenta na zobrazovanie dokumentov prisluchajucich entite PublicBody.
     */
    private void initDownloadLayout() {
        
        this.downoaderLayout = new DownloaderLayout<>(Theme.class, this.theme);
        this.addComponent(downoaderLayout);
        
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        
        Theme th = VaadinSession.getCurrent().getAttribute(Theme.class);
        
        A_User user = VaadinSession.getCurrent().getAttribute(A_User.class);
        
        Boolean isVolunteer = Boolean.FALSE;
        if (user != null){
                       
            UserType utype = userService.getUserType(user);
            //moze byt dobrovolnik, alebo admin.
            isVolunteer = ((UserType.VOLUNTEER).equals(utype) || (UserType.ADMIN).equals(utype));
        }

        if (th != null){
            setThemeValue(th);
            initAllBasic(isVolunteer);
        } else {
            UI.getCurrent().getNavigator().navigateTo("V10s_ThemesView");
        }

    }

    


}
