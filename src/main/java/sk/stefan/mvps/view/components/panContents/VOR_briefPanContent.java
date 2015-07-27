/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.panContents;

import com.vaadin.event.LayoutEvents;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import sk.stefan.mvps.model.entity.Vote;
import sk.stefan.mvps.model.entity.VoteOfRole;
import sk.stefan.mvps.model.service.VoteService;

/**
 *
 * @author stefan
 */
public class VOR_briefPanContent extends HorizontalLayout {

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
    public VOR_briefPanContent(VoteOfRole vor, VoteService vs) {

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
        
        this.voteDescriptionLb = new Label();
        if (vote != null) {
            this.voteDescriptionLb.setValue(vote.getPresentationName());
        } else {
            this.voteDescriptionLb.setValue("tato rola nemá hlasovanie");
        }
        
        
        
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
