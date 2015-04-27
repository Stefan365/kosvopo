/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components;

import com.vaadin.event.LayoutEvents;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import sk.stefan.MVP.model.entity.Vote;
import sk.stefan.MVP.model.entity.VoteOfRole;
import sk.stefan.MVP.model.service.VoteService;

/**
 *
 * @author stefan
 */
public class VoteOfRoleDetailedComponent extends HorizontalLayout {

    private static final long serialVersionUID = 1L;

//    entity:
    private final VoteOfRole voteOfRole;
    private Vote vote;

//    servisy:
    private final VoteService voteService;
    
    //graficke komponenty:
    private Label voteDescriptionLb; 
    private Label roleDecisionLb; 

    private Navigator nav;
    
    //0.konstruktor:
    /**
     * @param vor
     * @param vs
     */
    public VoteOfRoleDetailedComponent(VoteOfRole vor, VoteService vs) {

        this.voteOfRole = vor;
        this.voteService = vs;

        
        nav = UI.getCurrent().getNavigator();
        
        this.initLayout();
        this.initListener();
        
    }

    /**
     */
    private void initLayout() {

        this.removeAllComponents();
        
        this.setMargin(true);
        this.setSpacing(true);

        vote = voteService.getVote(voteOfRole);//voteService.getVoteResultAsString(, voteService);
        
        this.voteDescriptionLb = new Label(vote.getPresentationName());
        this.roleDecisionLb = new Label(voteOfRole.getDecision().getName());
        
        
        this.addComponents(voteDescriptionLb, roleDecisionLb);
        
    }
    
        /**
     *
     */
    private void initListener() {

        this.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {

            private static final long serialVersionUID = 1L;

            @Override
            public void layoutClick(LayoutEvents.LayoutClickEvent event) {
                VaadinSession.getCurrent().setAttribute(Vote.class, vote);
                nav.navigateTo("V6_VoteView");

            }
        });

    }

    

}
