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
import com.vaadin.ui.VerticalLayout;
import sk.stefan.MVP.model.entity.Vote;
import sk.stefan.MVP.model.service.VoteService;

/**
 *
 * @author stefan
 */
public class VoteComponent extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    private final Vote vote;

    private final Navigator navigator;

    //service, ktory bude zdedeny z nadradenej komponenty.
    private final VoteService voteService;
    
    //graficke komponenty:
    private Label dateLb; 
    private Label internalNrLb;
    private Label publicBodyLb;
    private Label subjectLb;
    private Label resultLb;
    private Label numbersLb;

    //0.konstruktor:
    public VoteComponent(Vote vot, VoteService vs) {

        this.setSpacing(true);
        this.setMargin(true);
        
        this.setStyleName("voteComponent");
        
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
        this.dateLb.setCaption("dátum hlasovania");
        this.internalNrLb = new Label(voteService.getVoteIntNr(vote));
        this.internalNrLb.setCaption("interné číslo hlasovania");
        this.publicBodyLb = new Label(voteService.getVotePublicBodyName(vote));
        this.publicBodyLb.setCaption("verejný orgán");
        this.subjectLb = new Label(voteService.getVoteSubjectName(vote)); 
        this.subjectLb.setCaption("predmet hlasovania");
        this.resultLb = new Label(voteService.getVoteResultAsString(vote));
        this.resultLb.setCaption("výsledok hlasovania");
        this.numbersLb = new Label(voteService.getVoteNumbers(vote));
        this.numbersLb.setCaption("čísla");
//        this.addComponents(dateLb, 0, 0);
//        this.addComponent(internalNrLb, 1, 0);
//        this.addComponent(publicBodyLb, 0, 1);
//        this.addComponent(subjectLb, 0, 2);
//        this.addComponent(resultLb, 0, 3);
//        this.addComponent(numbersLb, 1, 3);
        this.addComponents(dateLb, internalNrLb, publicBodyLb, subjectLb, resultLb, numbersLb);
        
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
