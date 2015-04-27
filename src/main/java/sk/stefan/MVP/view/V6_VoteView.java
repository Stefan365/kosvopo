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
import sk.stefan.MVP.model.entity.A_User;
import sk.stefan.MVP.model.entity.PublicPerson;
import sk.stefan.MVP.model.entity.PublicRole;
import sk.stefan.MVP.model.entity.Subject;
import sk.stefan.MVP.model.entity.Theme;
import sk.stefan.MVP.model.entity.Vote;
import sk.stefan.MVP.model.entity.VoteClassification;
import sk.stefan.MVP.model.service.ClassificationService;
import sk.stefan.MVP.model.service.UserService;
import sk.stefan.MVP.model.service.VoteService;
import sk.stefan.MVP.model.serviceImpl.ClassificationServiceImpl;
import sk.stefan.MVP.model.serviceImpl.UserServiceImpl;
import sk.stefan.MVP.model.serviceImpl.VoteServiceImpl;
import sk.stefan.factories.InputNewEntityButtonFactory;
import sk.stefan.MVP.view.components.NavigationComponent;
import sk.stefan.MVP.view.components.SubjectComponent;
import sk.stefan.MVP.view.components.ThemeComponent;
import sk.stefan.MVP.view.components.VoteClassComponent;
import sk.stefan.MVP.view.components.VoteDetailedComponent;
import sk.stefan.MVP.view.components.documents.DownloaderLayout;
import sk.stefan.MVP.view.components.documents.UploaderLayout;
import sk.stefan.enums.UserType;


/**
 *
 * @author stefan
 */
public final class V6_VoteView extends VerticalLayout implements View {

    private static final long serialVersionUID = 121322L;
    private static final Logger log = Logger.getLogger(V3_PublicBodyView.class);

    //hlavna entita tohoto VIew
    private Vote vote;

//    private List<VoteOfRole> voteOfRoles;

    private Subject subject;
    
    private Theme theme;
    
    private VoteClassification voteClassification;
    

    //servisy:
    private final VoteService voteService;
    private final UserService userService;
    private final ClassificationService classificationService;

    
    //komponnety:
    private VoteDetailedComponent voteDetailedComponent;
    
    private ThemeComponent themeComponent;
  
//    bude sucastou voteDetailedComponent:
//    private PublicRoleComponent navrhovatelComponent;

    private SubjectComponent subjectComponent;

    private VoteClassComponent voteClassComponent;
    
    private Button editVoteBt;
    private DownloaderLayout<PublicPerson> downoaderLayout;
    private UploaderLayout<PublicPerson> uploaderLayout;

    //zakladne, vseobecne:
    private final VerticalLayout temporaryLy;
    private final NavigationComponent navComp;
    private final Navigator nav;

    //konstruktor:
    public V6_VoteView() {
        
        this.setMargin(true);
        this.setSpacing(true);

        this.nav = UI.getCurrent().getNavigator();

        navComp = NavigationComponent.createNavigationComponent();
        this.addComponent(navComp);

        
        temporaryLy = new VerticalLayout();
        this.addComponent(temporaryLy);

        
        
//        this.publicRoleService = new PublicRoleServiceImpl();
        this.voteService = new VoteServiceImpl();
        this.userService = new UserServiceImpl();
        this.classificationService = new ClassificationServiceImpl();

//        if (vote != null) {
//            initAllBasic(Boolean.FALSE);
//        }

    }

    /**
     */
    private void initAllBasic(Boolean isVolunteer) {

        temporaryLy.removeAllComponents();

        initVoteDetailedComponent();
        this.initSubjectComponent();
        initThemeComponent();
        this.initVoteClassComponent();
        
        temporaryLy.addComponents(voteDetailedComponent, subjectComponent, themeComponent, 
                voteClassComponent);

        if (isVolunteer) {

            this.initNewPublicRoleButton();
            this.initUploadLayout();

        } else {
            this.initDownloadLayout();
        }

    }

    private void setVoteValue(Vote vot) {
        
        this.vote = vot;
        this.subject = voteService.findSubjectById(vote.getSubject_id());
        this.theme = voteService.findThemeBySubjectId(vote.getSubject_id());
//        this.voteOfRoles = voteService.findVoteOfRolesByVoteId(vote.getId());
        this.voteClassification = classificationService.findVoteClassByVoteId(vote.getId());
        
    }

    private void initVoteDetailedComponent() {
        
        this.voteDetailedComponent = new VoteDetailedComponent(vote, voteService);
        
    }

    /**
     */
    private void initSubjectComponent() {

        this.subjectComponent = new SubjectComponent(subject, voteService);

    }
    
    /**
     */
    private void initThemeComponent() {

        this.themeComponent = new ThemeComponent(theme, voteService);

    }

    /**
     */
    private void initVoteClassComponent() {
        
        this.voteClassComponent = new VoteClassComponent(voteClassification, classificationService);

    }


    /**
     * Prida tlacitko na pridavanie novej entity PublicBody.
     */
    private void initNewPublicRoleButton() {

        this.editVoteBt = InputNewEntityButtonFactory.createMyInputButton(PublicRole.class);

        temporaryLy.addComponent(editVoteBt);

    }

    /**
     * Inicializuje editovatelny layout s dokumentami prisluchajucimi entite
     * PublicBody
     */
    private void initUploadLayout() {

        this.uploaderLayout = new UploaderLayout<>(PublicPerson.class);

        temporaryLy.addComponent(uploaderLayout);

    }

    /**
     * Komponenta na zobrazovanie dokumentov prisluchajucich entite PublicBody.
     */
    private void initDownloadLayout() {

        this.downoaderLayout = new DownloaderLayout<>(PublicPerson.class);

        temporaryLy.addComponent(downoaderLayout);

    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        Vote vot = VaadinSession.getCurrent().getAttribute(Vote.class);
        
        
        A_User user = VaadinSession.getCurrent().getAttribute(A_User.class);

        Boolean isVolunteer = Boolean.FALSE;
        if (user != null) {
            UserType utype = userService.getUserType(user);
            //moze byt dobrovolnik, alebo admin.
            isVolunteer = ((UserType.VOLUNTEER).equals(utype) || (UserType.ADMIN).equals(utype));
        }

        if (vot != null) {
            this.setVoteValue(vot);
            initAllBasic(isVolunteer);
        } else {
            UI.getCurrent().getNavigator().navigateTo("V6s_VotesView");
        }

    }

//        


}
