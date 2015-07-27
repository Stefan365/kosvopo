/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.apache.log4j.Logger;
import sk.stefan.mvps.model.entity.A_User;
import sk.stefan.mvps.model.entity.Theme;
import sk.stefan.mvps.model.service.UserService;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.mvps.model.serviceImpl.UserServiceImpl;
import sk.stefan.mvps.model.serviceImpl.VoteServiceImpl;
import sk.stefan.mvps.view.components.panContents.THE_detPanContent;
import sk.stefan.mvps.view.components.layouts.DownloaderBriefLayout;
import sk.stefan.mvps.view.components.layouts.DownAndUploaderBriefLayout;
import sk.stefan.mvps.view.components.layouts.ViewLayout;
import sk.stefan.enums.UserType;
import sk.stefan.factories.EditEntityButtonFactory;
import sk.stefan.wrappers.FunctionalEditWrapper;

/**
 * View tykajuce sa zvoleneho tematickeho okruhu.
 *
 * @author stefan
 */
public final class V10_ThemeView extends ViewLayout implements View {

    private static final long serialVersionUID = 121322L;
    private static final Logger log = Logger.getLogger(V10_ThemeView.class);

    //servisy:
    private final VoteService voteService;
    private final UserService userService;

    //hlavna entita tohoto VIew
    private Theme theme;
    
//    private final EditEntityButtonFactory<Theme> editButtonFactory;
        
    //komponenty:
    private THE_detPanContent themeDetailedComp;
    private Button editThemeBt;
    private DownloaderBriefLayout<Theme> downloaderLayout;
    private DownAndUploaderBriefLayout<Theme> downAnUploaderLayout;

    //konstruktor:
    public V10_ThemeView() {
        super("Tématický Okruh Hlasovania");
        this.voteService = new VoteServiceImpl();
        this.userService = new UserServiceImpl();
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
        this.themeDetailedComp = new THE_detPanContent(theme, null);
    }

    /**
     * Prida tlacitko na pridavanie novej entity PublicBody.
     */
    private void initEditThemeButton() {
        
        FunctionalEditWrapper<Theme> ew = new FunctionalEditWrapper<>(Theme.class, theme);
        this.editThemeBt = EditEntityButtonFactory.createMyEditButton(ew);
        
        this.addComponent(editThemeBt);

    }

    /**
     * Inicializuje editovatelny layout s dokumentami prisluchajucimi entite PublicBody
     */
    private void initUploadLayout() {
        
        this.downAnUploaderLayout = new DownAndUploaderBriefLayout<>(Theme.class, this.theme);
        this.addComponent(downAnUploaderLayout);
    }
    

    /**
     * Komponenta na zobrazovanie dokumentov prisluchajucich entite PublicBody.
     */
    private void initDownloadLayout() {
        
        this.downloaderLayout = new DownloaderBriefLayout<>(Theme.class, this.theme);
        this.addComponent(downloaderLayout);
        
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
