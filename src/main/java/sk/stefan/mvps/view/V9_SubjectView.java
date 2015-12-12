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
import org.apache.log4j.Logger;
import sk.stefan.factories.EditEntityButtonFactory;
import sk.stefan.mvps.model.entity.A_User;
import sk.stefan.mvps.model.entity.Subject;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.mvps.model.serviceImpl.VoteServiceImpl;
import sk.stefan.mvps.view.components.layouts.DownAndUploaderBriefLayout;
import sk.stefan.mvps.view.components.layouts.DownloaderBriefLayout;
import sk.stefan.mvps.view.components.layouts.MyViewLayout;
import sk.stefan.mvps.view.components.panContents.SUB_detPanContent;
import sk.stefan.mvps.view.components.panels.MyDetailedPanel;
import sk.stefan.wrappers.FunctionalEditWrapper;

/**
 *
 * @author stefan
 */
public final class V9_SubjectView extends MyViewLayout implements View {

    private static final long serialVersionUID = 121322L;
    
    private static final Logger log = Logger.getLogger(V9_SubjectView.class);

//    entity:
    private Subject subject;

//    servisy:
    private final VoteService voteService;
    
//    komponenty:
    private MyDetailedPanel<SUB_detPanContent> subjectDetPanel;
    private Button editSubjectBt;
    private DownloaderBriefLayout<Subject> downoaderLayout;
    private DownAndUploaderBriefLayout<Subject> uploaderLayout;

    
    
    
    
//    konstruktor:
    public V9_SubjectView() {

        super("Predmet Hlasovania");
        this.voteService = new VoteServiceImpl();
        
    }

    /**
     */
    private void initAllBasic(Boolean isVolunteer) {

        this.removeAllComponents();
        this.initSubjectDetailedComponent();
        this.addComponents(subjectDetPanel);
        
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

        SUB_detPanContent subCont = new SUB_detPanContent(subject, voteService);
        this.subjectDetPanel = new MyDetailedPanel<>(subCont);

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
        
        this.uploaderLayout = new DownAndUploaderBriefLayout<>(Subject.class, this.subject);
        this.addComponent(uploaderLayout);
        
    }

    /**
     * Komponenta na zobrazovanie dokumentov prisluchajucich entite PublicBody.
     */
    private void initDownloadLayout() {
        
        this.downoaderLayout = new DownloaderBriefLayout<>(Subject.class, this.subject);
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
