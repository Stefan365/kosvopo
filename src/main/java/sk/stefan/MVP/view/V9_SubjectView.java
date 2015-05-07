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
import sk.stefan.MVP.model.service.UserService;
import sk.stefan.MVP.model.service.VoteService;
import sk.stefan.MVP.model.serviceImpl.UserServiceImpl;
import sk.stefan.MVP.model.serviceImpl.VoteServiceImpl;
import sk.stefan.MVP.view.components.SubjectDetailedComponent;
import sk.stefan.MVP.view.components.documents.DownloaderLayout;
import sk.stefan.MVP.view.components.documents.UploaderLayout;
import sk.stefan.factories.EditEntityButtonFactory;
import sk.stefan.wrappers.FunctionalEditWrapper;

/**
 *
 * @author stefan
 */
public final class V9_SubjectView extends VerticalLayout implements View {

    private static final long serialVersionUID = 121322L;
    private static final Logger log = Logger.getLogger(V9_SubjectView.class);

        //servisy:
    private final VoteService voteService;
    private final UserService userService;

    //hlavna entita tohoto VIew
    private Subject subject;
    
    //komponenty
    private SubjectDetailedComponent subjectDetailedComp;
    private Button editSubjectBt;
    private DownloaderLayout<Subject> downoaderLayout;
    private UploaderLayout<Subject> uploaderLayout;

    //konstruktor:
    public V9_SubjectView() {

        this.setMargin(true);
        this.setSpacing(true);

        this.voteService = new VoteServiceImpl();
        this.userService = new UserServiceImpl();
        

        
    }

    /**
     */
    private void initAllBasic(Boolean isVolunteer) {

        this.removeAllComponents();
        this.initSubjectDetailedComponent();
        this.addComponents(subjectDetailedComp);
        
        if(isVolunteer){
            this.initEditSubjectButton();
            this.initUploadLayout();
        } else {
            this.initDownloadLayout();
        }

    }
    
    private void setSubjectValue(Subject sub) {
        subject = sub;
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
        
        
        FunctionalEditWrapper<Subject> ew = new FunctionalEditWrapper<>(Subject.class, subject);
        this.editSubjectBt = EditEntityButtonFactory.createMyEditButton(ew);
        this.addComponent(editSubjectBt);

    }

    /**
     * Inicializuje editovatelny layout s dokumentami prisluchajucimi entite PublicBody
     */
    private void initUploadLayout() {
        
        this.uploaderLayout = new UploaderLayout<>(Subject.class, this.subject);
        this.addComponent(uploaderLayout);
        
    }

    /**
     * Komponenta na zobrazovanie dokumentov prisluchajucich entite PublicBody.
     */
    private void initDownloadLayout() {
        
        this.downoaderLayout = new DownloaderLayout<>(Subject.class, this.subject);
        this.addComponent(downoaderLayout);
        
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        Subject sub = VaadinSession.getCurrent().getAttribute(Subject.class);
        
        A_User user = VaadinSession.getCurrent().getAttribute(A_User.class);

        Boolean isVolunteer = Boolean.FALSE;
        if (user != null){
            isVolunteer = Boolean.TRUE;
        }
        if (sub != null){
            setSubjectValue(sub);
            initAllBasic(isVolunteer);
        } else {
            UI.getCurrent().getNavigator().navigateTo("V9s_SubjectsView");
        }

    }


}
