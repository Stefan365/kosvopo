/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view;

import com.vaadin.addon.timeline.Timeline;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import java.util.List;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.dao.A_User;
import sk.stefan.MVP.model.entity.dao.A_UserRole;
import sk.stefan.MVP.model.entity.dao.PublicBody;
import sk.stefan.MVP.model.entity.dao.PublicPerson;
import sk.stefan.MVP.model.entity.dao.PublicRole;
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.MVP.model.service.PublicRoleService;
import sk.stefan.MVP.model.service.UserService;
import sk.stefan.MVP.model.service.VoteService;
import sk.stefan.MVP.model.serviceImpl.PublicRoleServiceImpl;
import sk.stefan.MVP.model.serviceImpl.UserServiceImpl;
import sk.stefan.MVP.model.serviceImpl.VoteServiceImpl;
import sk.stefan.MVP.view.components.InputNewEntityButtonFactory;
import sk.stefan.MVP.view.components.PublicRolesLayout;
import sk.stefan.MVP.view.components.VotesLayout;
import sk.stefan.documents.DownloaderLayout;
import sk.stefan.documents.UploaderLayout;
import sk.stefan.enums.UserType;

/**
 *
 * @author stefan
 */
public final class V3_PublicBodyView extends VerticalLayout implements View {

    
    private static final long serialVersionUID = 121322L;
    private static final Logger log = Logger.getLogger(V3_PublicBodyView.class);

    //hlavna entita tohoto VIew
    private PublicBody publicBody;
    
    //komponenty pre zobrazeneie aktivnych verejnych roli: 
    private PublicRolesLayout publicRolesLayout;
    
    private VotesLayout votesLayout;
    

    private final PublicRoleService publicRoleService;
    private final VoteService voteService;
    private final UserService userService;
    
    
    //componenty pre TimeLine:
    private IndexedContainer container;
    private Object timestampProperty;
    private Object valueProperty;
    private Timeline timeLine;
    
    //tlacitko na pridavanie novej entity:
    private Button addNewPublicRoleBt;
    private DownloaderLayout<PublicBody> downoaderLayout;
    private UploaderLayout<PublicBody> uploaderLayout;

    
    
    
    //konstruktor:
    public V3_PublicBodyView(){
        
        this.publicRoleService = new PublicRoleServiceImpl();
        this.voteService = new VoteServiceImpl();
        this.userService = new UserServiceImpl();
        
        
//        if (publicBody != null){
//            initAllBasic();
//        }
        
    }
    
    private void initAllBasic(Boolean isVolunteer){
        
        
        this.removeAllComponents();
        
        this.initPublicRolesLayout();
        this.initVoteLayout();
        this.initTimeline();
        
        this.addComponents(publicRolesLayout, votesLayout, timeLine);
        
        if(isVolunteer){
            
            this.initNewPublicRoleButton();
            this.initUploadLayout();
            
        } else {
            this.initDownloadLayout();
        }
    }
    
    
    private void initPublicRolesLayout() {
        
        List<Integer> prIds = publicRoleService.findPublicRoleIdsByPubBodyId(publicBody.getId());
        log.info("KAROLKO1: " + prIds.size());
        
        List<PublicRole> publicRoles = publicRoleService.getPublicRoles(prIds);
        log.info("KAROLKO2: " + publicRoles.size());
        this.publicRolesLayout = new PublicRolesLayout(publicRoles, publicRoleService);
        
        
    }

    private void initVoteLayout() {
        
        List<Integer> votIds = voteService.findVoteIdsByPubBodyId(publicBody.getId());
        
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

    
    public PublicBody getPublicBody() {
        return publicBody;
    }

    public void setPublicBody(PublicBody publicBody) {
        this.publicBody = publicBody;
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
        
        this.addComponent(addNewPublicRoleBt);
    
    }

    /**
     * Inicializuje editovatelny layout s dokumentami prisluchajucimi entite PublicBody
     */
    private void initUploadLayout() {
        
        this.uploaderLayout = new UploaderLayout<>(PublicBody.class);
        
        this.addComponent(uploaderLayout);
        
    }

    /**
     * Komponenta na zobrazovanie dokumentov prisluchajucich entite PublicBody.
     */
    private void initDownloadLayout() {
        
        this.downoaderLayout = new DownloaderLayout<>(PublicBody.class);
        
        this.addComponent(downoaderLayout);
        
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        
        this.publicBody = VaadinSession.getCurrent().getAttribute(PublicBody.class);

        A_User user = VaadinSession.getCurrent().getAttribute(A_User.class);
        
        UserType utype = userService.getUserType(user);
                
        Boolean isVolunteer = Boolean.FALSE;
        if (user != null){
            //moze byt dobrovolnik, alebo admin.
            isVolunteer = ((UserType.USER).equals(utype) || (UserType.ADMIN).equals(utype));
        }
        
        if (publicBody != null){
            initAllBasic(isVolunteer);
        }
        
    }

}
