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
import com.vaadin.ui.VerticalLayout;
import java.util.List;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.dao.A_User;
import sk.stefan.MVP.model.entity.dao.PublicBody;
import sk.stefan.MVP.model.entity.dao.PublicRole;
import sk.stefan.MVP.model.service.PublicRoleService;
import sk.stefan.MVP.model.service.VoteService;
import sk.stefan.MVP.model.serviceImpl.PublicRoleServiceImpl;
import sk.stefan.MVP.model.serviceImpl.VoteServiceImpl;
import sk.stefan.MVP.view.components.PublicRolesLayout;
import sk.stefan.MVP.view.components.VotesLayout;

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
    
    
    
            
    
    
    
    //componenty pre TimeLine:
    private IndexedContainer container;
    
    private Object timestampProperty;
    
    private Object valueProperty;
    
    private Timeline timeline;

    
    
    //konstruktor:
    public V3_PublicBodyView(){
        
        this.publicRoleService = new PublicRoleServiceImpl();
        this.voteService = new VoteServiceImpl();
        
        
        this.initPublicRolesLayout();
        this.initVoteLayout();
        this.initTimeline();
        
    }
    
    private void initPublicRolesLayout() {
        
        List<Integer> prIds = publicRoleService.findPublicRoleIdsByPubBodyId(publicBody.getId());
        
        List<PublicRole> publicRoles = publicRoleService.findNewPublicRoles(prIds);
        
        this.publicRolesLayout = new PublicRolesLayout(publicRoles, publicRoleService);
        
    }

    private void initVoteLayout() {
        
        List<Integer> votIds = voteService.findPublicRoleIdsByPubBodyId(publicBody.getId());
        
        List<PublicRole> publicRoles = publicRoleService.findNewPublicRoles(votIds);
        
        this.publicRolesLayout = new PublicRolesLayout(publicRoles, publicRoleService);
        
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
        timeline = new Timeline();

        // Add the container as a graph container
        timeline.addGraphDataSource(container, timestampProperty,
                valueProperty);
        
        this.addComponent(timeline);
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

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        
        this.publicBody = VaadinSession.getCurrent().getAttribute(PublicBody.class);
    
    }

}
