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
import sk.stefan.MVP.model.entity.dao.PublicBody;
import sk.stefan.MVP.model.entity.dao.PublicPerson;
import sk.stefan.MVP.model.entity.dao.PublicRole;
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.MVP.model.service.PublicPersonService;
import sk.stefan.MVP.model.service.PublicRoleService;
import sk.stefan.MVP.model.service.VoteService;
import sk.stefan.MVP.model.serviceImpl.PublicPersonServiceImpl;
import sk.stefan.MVP.model.serviceImpl.PublicRoleServiceImpl;
import sk.stefan.MVP.model.serviceImpl.VoteServiceImpl;
import sk.stefan.MVP.view.components.PublicPersonComponent;
import sk.stefan.MVP.view.components.PublicRolesLayout;
import sk.stefan.MVP.view.components.VotesLayout;

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

//    private final PublicPersonService publicPersonService;
    //componenty pre TimeLine:
    private IndexedContainer container;

    private Object timestampProperty;

    private Object valueProperty;

    private Timeline timeLine;

    //konstruktor:
    public V4_PublicPersonView() {

        this.publicRoleService = new PublicRoleServiceImpl();
        this.voteService = new VoteServiceImpl();
//        this.publicPersonService = new PublicPersonServiceImpl();

        if (publicPerson != null) {
            initAll();
        }

    }

    private void initAll() {

        this.removeAllComponents();
        
        this.setActualPublicRole();
        this.initPublicPersonComponent();
        this.initPublicRolesLayout();
        this.initVoteLayout();
        this.initTimeline();

        this.addComponents(publicPersonComponent, publicRolesLayout, votesLayout, timeLine);

    }
    
    //
    private void setActualPublicRole(){
        
        PublicBody pb = VaadinSession.getCurrent().getAttribute(PublicBody.class);
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

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        this.publicPerson = VaadinSession.getCurrent().getAttribute(PublicPerson.class);
        initAll();

    }

}
