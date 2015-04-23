/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view;

import com.vaadin.addon.timeline.Timeline;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.util.List;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.dao.A_User;
import sk.stefan.MVP.model.entity.dao.PersonClassification;
import sk.stefan.MVP.model.entity.dao.PublicBody;
import sk.stefan.MVP.model.entity.dao.PublicPerson;
import sk.stefan.MVP.model.entity.dao.PublicRole;
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.MVP.model.service.ClassificationService;
import sk.stefan.MVP.model.service.PublicRoleService;
import sk.stefan.MVP.model.service.UserService;
import sk.stefan.MVP.model.service.VoteService;
import sk.stefan.MVP.model.serviceImpl.ClassificationServiceImpl;
import sk.stefan.MVP.model.serviceImpl.PublicRoleServiceImpl;
import sk.stefan.MVP.model.serviceImpl.UserServiceImpl;
import sk.stefan.MVP.model.serviceImpl.VoteServiceImpl;
import sk.stefan.MVP.view.components.InputNewEntityButtonFactory;
import sk.stefan.MVP.view.components.NavigationComponent;
import sk.stefan.MVP.view.components.PersonClassLayout;
import sk.stefan.MVP.view.components.PublicPersonComponent;
import sk.stefan.MVP.view.components.PublicRolesLayout;
import sk.stefan.MVP.view.components.VotesLayout;
import sk.stefan.documents.DownloaderLayout;
import sk.stefan.documents.UploaderLayout;
import sk.stefan.enums.UserType;

/**
 *
 * @author stefan
 */
public final class V4_PublicPersonView extends VerticalLayout implements View {

    private static final long serialVersionUID = 121322L;
    private static final Logger log = Logger.getLogger(V3_PublicBodyView.class);

    //hlavna entita tohoto VIew
    private PublicPerson publicPerson;
    
    //dvolezita entita, ktora predstavuje aktualnu verejnu funkciu danej osoby. 
    //bude vyznacena farebne.
    private PublicRole actualPublicRole;
    

    
    //komponenty pre zobrazeneie verejnych roli dane osoby (tj. jedna aktivna a 
    //zvysok stare): 
    private PublicRolesLayout publicRolesLayout;

    //hlasovania ktorych sa dana osoba zucastnila, filtrovane podla osoby.
    //tj. aj historicke, v inych roliach.
    private VotesLayout votesLayout;

    //layout pre zobrazenie zakladnych udajov danej osoby.
    private PublicPersonComponent publicPersonComponent;

    //servisy:
    private final PublicRoleService publicRoleService;
    private final VoteService voteService;
    private final UserService userService;
    private final ClassificationService classificationService;  
    
//    private final PublicPersonService publicPersonService;
    //componenty pre TimeLine:
    private IndexedContainer container;
    private Object timestampProperty;
    private Object valueProperty;
    private Timeline timeLine;
    
    private PersonClassLayout classPersonLayout;

    
    //tlacitko na pridavanie novej entity:
    private Button addNewPublicRoleBt;
    //pre uzivatela obcan   
    private DownloaderLayout<PublicPerson> downoaderLayout;
    //pre uzivatela admin a dobrovolnik
    private UploaderLayout<PublicPerson> uploaderLayout;

    private final VerticalLayout temporaryLy;
    private final NavigationComponent navComp;
    private final Navigator nav;
    
    //konstruktor:
    public V4_PublicPersonView() {
        
        this.nav = UI.getCurrent().getNavigator();

        navComp = NavigationComponent.createNavigationComponent();
        this.addComponent(navComp);
        
        temporaryLy = new VerticalLayout();
        this.addComponent(temporaryLy);


        this.publicRoleService = new PublicRoleServiceImpl();
        this.voteService = new VoteServiceImpl();
        this.userService = new UserServiceImpl();
        this.classificationService = new ClassificationServiceImpl();  

        
        
        if (publicPerson != null) {
            initAllBasic(Boolean.FALSE);
        }

    }

    /**
     */
    private void initAllBasic(Boolean isVolunteer) {

        temporaryLy.removeAllComponents();
        
        this.initPublicPersonComponent();
        this.initPublicRolesLayout();
        this.initVoteLayout();
        this.initTimeline();
        this.classPersonLayout();

        
        temporaryLy.addComponents(publicPersonComponent, publicRolesLayout, votesLayout, classPersonLayout, timeLine);
        
        if(isVolunteer){
            
            this.initNewPublicRoleButton();
            this.initUploadLayout();
            
        } else {
            this.initDownloadLayout();
        }

    }
    
    //
    private void setPublicPersonValue(PublicPerson pp){
        
        this.publicPerson = pp;
        PublicBody pb = UI.getCurrent().getSession().getAttribute(PublicBody.class);
        this.actualPublicRole = publicRoleService.getActualRoleForPublicBody(publicPerson, pb);
        
    }

    /**
     */
    private void initPublicPersonComponent() {

        this.publicPersonComponent = new PublicPersonComponent(publicPerson, null);

    }

    /**
     *
     */
    private void initPublicRolesLayout() {

        List<Integer> prIds = publicRoleService.findPublicRoleIdsByPubPersonId(publicPerson.getId());

        List<PublicRole> publicRoles = publicRoleService.getPublicRoles(prIds);

        this.publicRolesLayout = new PublicRolesLayout(publicRoles, publicRoleService);
        
        this.publicRolesLayout.setActual(actualPublicRole);

    }

    private void initVoteLayout() {

        List<Integer> votIds = voteService.findVoteIdsByPubPersonId(publicPerson.getId());

        List<Vote> votes = voteService.findNewVotes(votIds);

        this.votesLayout = new VotesLayout(votes, voteService);

    }

    public void initTimeline() {
        // Construct a container which implements Container.Indexed       
        container = new IndexedContainer();

        // Add the Timestamp property to the container
        timestampProperty = "Our timestamp property";
        container.addContainerProperty(timestampProperty,
                java.util.Date.class, null);

        // Add the value property
        valueProperty = "Our value property";
        container.addContainerProperty(valueProperty, Float.class, null);

        // Our timeline
        timeLine = new Timeline();

        // Add the container as a graph container
        timeLine.addGraphDataSource(container, timestampProperty,
                valueProperty);

    }

    public PublicPerson getPublicPerson() {
        return publicPerson;
    }

    public void setPublicPerson(PublicPerson publicPerson) {
        this.publicPerson = publicPerson;
    }

    public PublicRolesLayout getPublicRolesLayout() {
        return publicRolesLayout;
    }

    public void setPublicRolesLayout(PublicRolesLayout publicRolesLy) {
        this.publicRolesLayout = publicRolesLy;
    }
    /**
     * Prida tlacitko na pridavanie novej entity PublicBody.
     */
    private void initNewPublicRoleButton() {
        
        this.addNewPublicRoleBt = InputNewEntityButtonFactory.createMyButton(PublicRole.class);
        
        temporaryLy.addComponent(addNewPublicRoleBt);

    }

    /**
     * Inicializuje editovatelny layout s dokumentami prisluchajucimi entite PublicBody
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

    /**
     */
    private void classPersonLayout() {
        
        List<Integer> pclIds = classificationService.findActualPersonClassIds(publicPerson.getId());

        List<PersonClassification> pcls = classificationService.findNewPersonClass(pclIds);

        this.classPersonLayout = new PersonClassLayout(pcls, classificationService);
        
        
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        PublicPerson pp = UI.getCurrent().getSession().getAttribute(PublicPerson.class);
         
        A_User user = VaadinSession.getCurrent().getAttribute(A_User.class);

                
        Boolean isVolunteer = Boolean.FALSE;
        if (user != null){
            UserType utype = userService.getUserType(user);
            //moze byt dobrovolnik, alebo admin.
            isVolunteer = ((UserType.USER).equals(utype) || (UserType.ADMIN).equals(utype));
        }
        
        if (pp != null){
            setPublicPersonValue(pp);
            initAllBasic(isVolunteer);
        } else {
            UI.getCurrent().getNavigator().navigateTo("V4s_PublicPersonsView");
        }

    }



}
