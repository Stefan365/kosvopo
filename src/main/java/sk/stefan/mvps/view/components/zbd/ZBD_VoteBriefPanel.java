/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.zbd;

import com.vaadin.event.LayoutEvents;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import sk.stefan.mvps.model.entity.Vote;
import sk.stefan.mvps.model.service.VoteService;

/**
 *
 * @author stefan
 */
public class ZBD_VoteBriefPanel extends GridLayout {

    private static final long serialVersionUID = 1L;

    private final Vote vote;

    private final Navigator navigator;

    //service, ktory bude zdedeny z nadradenej komponenty.
    private final VoteService voteService;
    
    //graficke komponenty:
    private Label dateLb; 
    private Label internalNrLb;
    private Label publicBodyLb;
    private Label numbersLb;

    //0.konstruktor:
    public ZBD_VoteBriefPanel(Vote vot, VoteService vs) {

        super(2, 4);//column , row
        
        this.setMargin(true);
        this.setSpacing(true);
        
        this.navigator = UI.getCurrent().getNavigator();
        this.vote = vot;
        this.voteService = vs;

        this.initLayout();
        this.initListener();
    }

    /**
     */
    private void initLayout() {

        this.removeAllComponents();
        
        this.setSpacing(true);

        this.dateLb = new Label(voteService.getVoteDate(vote));
        this.internalNrLb = new Label(voteService.getVoteIntNr(vote));
        this.publicBodyLb = new Label(voteService.getVotePublicBodyName(vote));
        this.numbersLb = new Label(voteService.getVoteNumbers(vote));
        
        this.addComponent(dateLb, 0, 0);
        this.addComponent(internalNrLb, 1, 0);
        this.addComponent(publicBodyLb, 0, 1);
        this.addComponent(numbersLb, 1, 3);
        
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
                navigator.navigateTo("V6_VoteView");

            }
        });

    }
}
