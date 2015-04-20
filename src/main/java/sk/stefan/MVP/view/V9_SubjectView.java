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
import sk.stefan.MVP.model.entity.dao.Subject;
import sk.stefan.MVP.model.entity.dao.Theme;
import sk.stefan.MVP.model.service.UserService;
import sk.stefan.MVP.model.service.VoteService;
import sk.stefan.MVP.model.serviceImpl.UserServiceImpl;
import sk.stefan.MVP.model.serviceImpl.VoteServiceImpl;
import sk.stefan.MVP.view.components.EditEntityButtonFactory;
import sk.stefan.MVP.view.components.InputNewEntityButtonFactory;
import sk.stefan.MVP.view.components.NavigationComponent;
import sk.stefan.MVP.view.components.SubjectDetailedComponent;
import sk.stefan.MVP.view.components.ThemeDetailedComponent;
import sk.stefan.documents.DownloaderLayout;
import sk.stefan.documents.UploaderLayout;
import sk.stefan.enums.UserType;

/**
 *
 * @author stefan
 */
public final class V9_SubjectView extends VerticalLayout implements View {

    private static final long serialVersionUID = 121322L;
    private static final Logger log = Logger.getLogger(V9_SubjectView.class);

    //hlavna entita tohoto VIew
    private Subject subject;
    
    private SubjectDetailedComponent subjectDetailedComp;
    private Button editSubjectBt;
    
    //servisy:
    private final VoteService voteService;
    private final UserService userService;
    
    private DownloaderLayout<Subject> downoaderLayout;
    private UploaderLayout<Subject> uploaderLayout;

    private final VerticalLayout temporaryLy;
    private final NavigationComponent navComp;
    private final Navigator nav;
    
    //konstruktor:
    public V9_SubjectView() {
        
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
        
        this.initSubjectDetailedComponent();
        
        temporaryLy.addComponents(subjectDetailedComp);
        
        if(isVolunteer){
            
            this.initEditSubjectButton();
            this.initUploadLayout();
            
        } else {
            this.initDownloadLayout();
        }

    }
    

    /**
     */
    private void initSubjectDetailedComponent() {

        //voteservice nieje potrebny, preto null;
        this.subjectDetailedComp = new SubjectDetailedComponent(subject, voteService);

    }

    /**
     * Prida tlacitko na pridavanie novej entity PublicBody.
     */
    private void initEditSubjectButton() {
        
        this.editSubjectBt = EditEntityButtonFactory.createMyButton(Subject.class, subject);
        
        temporaryLy.addComponent(editSubjectBt);

    }

    /**
     * Inicializuje editovatelny layout s dokumentami prisluchajucimi entite PublicBody
     */
    private void initUploadLayout() {
        
        this.uploaderLayout = new UploaderLayout<>(Subject.class);
        
        temporaryLy.addComponent(uploaderLayout);
        
    }

    /**
     * Komponenta na zobrazovanie dokumentov prisluchajucich entite PublicBody.
     */
    private void initDownloadLayout() {
        
        this.downoaderLayout = new DownloaderLayout<>(Subject.class);
        
        temporaryLy.addComponent(downoaderLayout);
        
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        this.subject = VaadinSession.getCurrent().getAttribute(Subject.class);
        
        A_User user = VaadinSession.getCurrent().getAttribute(A_User.class);

        Boolean isVolunteer = Boolean.FALSE;
        if (user != null){
            isVolunteer = Boolean.TRUE;
        }
//        UserType utype = userService.getUserType(user);
//                
//        Boolean isVolunteer = Boolean.FALSE;
//        if (user != null){
//            //moze byt dobrovolnik, alebo admin.
//            isVolunteer = ((UserType.USER).equals(utype) || (UserType.ADMIN).equals(utype));
//        }
        
        if (subject != null){
            initAllBasic(isVolunteer);
        }

    }

}
