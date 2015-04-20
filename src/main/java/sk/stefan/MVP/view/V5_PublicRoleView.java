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
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.util.List;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.dao.A_User;
import sk.stefan.MVP.model.entity.dao.PublicRole;
import sk.stefan.MVP.model.entity.dao.VoteOfRole;
import sk.stefan.MVP.model.service.PublicRoleService;
import sk.stefan.MVP.model.service.UserService;
import sk.stefan.MVP.model.service.VoteService;
import sk.stefan.MVP.model.serviceImpl.PublicRoleServiceImpl;
import sk.stefan.MVP.model.serviceImpl.UserServiceImpl;
import sk.stefan.MVP.model.serviceImpl.VoteServiceImpl;
import sk.stefan.MVP.view.components.NavigationComponent;
import sk.stefan.MVP.view.components.PublicRoleComponent;
import sk.stefan.MVP.view.components.VotesOfRoleLayout;
import sk.stefan.documents.DownloaderLayout;
import sk.stefan.documents.UploaderLayout;
import sk.stefan.enums.UserType;

/**
 * View zobrazujúci roľu danej verejnej osoby.
 *
 * @author stefan
 */
public final class V5_PublicRoleView extends VerticalLayout implements View {

    private static final long serialVersionUID = 121322L;
    private static final Logger log = Logger.getLogger(V5_PublicRoleView.class);

    //hlavna entita tohoto VIew
    private PublicRole publicRole;
    

    //servisy:
    private final PublicRoleService publicRoleService;
    private final VoteService voteService;
    private final UserService userService;

    //componenty pre TimeLine:
    private IndexedContainer container;
    private Object timestampProperty;
    private Object valueProperty;
    private Timeline timeLine;

    //layout pre zobrazenie zakladnych udajov danej osoby.
    private PublicRoleComponent publicRoleComponent;
    private VotesOfRoleLayout votesOfRoleLayout;
    
    //pre uzivatela obcan   
    private DownloaderLayout<PublicRole> downoaderLayout;
    //pre uzivatela admin a dobrovolnik
    private UploaderLayout<PublicRole> uploaderLayout;
    
    private final VerticalLayout temporaryLy;
    
    private final NavigationComponent navComp;
    
    private final Navigator nav;

    
    //konstruktor:
    /**
     * 
     */
    public V5_PublicRoleView() {

        this.nav = UI.getCurrent().getNavigator();

        navComp = NavigationComponent.createNavigationComponent();
        this.addComponent(navComp);
        
        temporaryLy = new VerticalLayout();
        this.addComponent(temporaryLy);

        this.publicRoleService = new PublicRoleServiceImpl();
        this.voteService = new VoteServiceImpl();
        this.userService = new UserServiceImpl();

    }

    /**
     */
    private void initAllBasic(Boolean isVolunteer) {

        temporaryLy.removeAllComponents();
        
        this.initPublicPersonComponent();
        this.initVotesOrRoleLayout();
        this.initTimeline();

        temporaryLy.addComponents(publicRoleComponent, votesOfRoleLayout, timeLine);
        
        if(isVolunteer){
            
            this.initUploadLayout();
            
        } else {
            
            this.initDownloadLayout();
        
        }

    }
    
    /**
     */
    private void initPublicPersonComponent() {

        this.publicRoleComponent = new PublicRoleComponent(publicRole, publicRoleService);

    }

    /**
     * 
     */
    private void initVotesOrRoleLayout() {

        List<Integer> vorIds = voteService.findVoteOfRoleIdsByPubRoleId(publicRole.getId());

        List<VoteOfRole> votesOfRole = voteService.findNewVotesOfRole(vorIds);

        this.votesOfRoleLayout = new VotesOfRoleLayout(votesOfRole, voteService);

    }

    
    /**
     * Timeline inicializacia.
     */
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

 
//    /**
//     * Prida tlacitko na pridavanie novej entity PublicBody.
//     */
//    private void initNewPublicBodyButton() {
//        
//        this.addNewPublicRoleBt = InputNewEntityButtonFactory.createMyButton(PublicBody.class);
//        
//        this.addComponent(addNewPublicRoleBt);
//
//    }

    /**
     * Inicializuje editovatelny layout s dokumentami prisluchajucimi entite PublicBody.
     */
    private void initUploadLayout() {
        
        this.uploaderLayout = new UploaderLayout<>(PublicRole.class);
        
        temporaryLy.addComponent(uploaderLayout);
        
    }

    /**
     * Komponenta na zobrazovanie dokumentov prisluchajucich entite PublicBody.
     */
    private void initDownloadLayout() {
        
        this.downoaderLayout = new DownloaderLayout<>(PublicRole.class);
        
        temporaryLy.addComponent(downoaderLayout);
        
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        this.publicRole = VaadinSession.getCurrent().getAttribute(PublicRole.class);
        
        A_User user = VaadinSession.getCurrent().getAttribute(A_User.class);

        UserType utype = userService.getUserType(user);
                
        Boolean isVolunteer = Boolean.FALSE;
        if (user != null){
            //moze byt dobrovolnik, alebo admin.
            isVolunteer = ((UserType.USER).equals(utype) || (UserType.ADMIN).equals(utype));
        }
        
        if (publicRole != null){
            initAllBasic(isVolunteer);
        }

    }


}
