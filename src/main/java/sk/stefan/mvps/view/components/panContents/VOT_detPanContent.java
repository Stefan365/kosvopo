/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.panContents;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import java.util.List;
import sk.stefan.mvps.model.entity.Vote;
import sk.stefan.mvps.model.entity.VoteOfRole;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.mvps.view.components.layouts.VORs_brief2Layout;

/**
 *
 * @author stefan
 */
public class VOT_detPanContent extends GridLayout {

    private static final long serialVersionUID = 1L;

    //hlavna entita:
    private final Vote vote;

    
    //service, ktory bude zdedeny z nadradenej komponenty.
    private final VoteService voteService;
    
    //graficke komponenty:
    private List<VoteOfRole> voteOfRoles;
    private VORs_brief2Layout voteOfRolesBriefLy;
    
    private Label dateLb; 
    private Label internalNrLb;
    private Label publicBodyLb;
    private Label resultLb;
    private Label numbersLb;

    private final Navigator nav;

    //0.konstruktor:
    public VOT_detPanContent(Vote vot, VoteService vs) {
        
        super(2, 5);//column , row
        
        this.setStyleName("voteDetailedPanel");
        this.setMargin(true);
        this.setSpacing(true);
        
        this.voteService = vs;
        this.nav = UI.getCurrent().getNavigator();
        this.vote = vot;
        
        this.initLayout();
        
    }

    /**
     */
    private void initLayout() {

        this.removeAllComponents();
        
        this.setSpacing(true);

        this.dateLb = new Label(voteService.getVoteDate(vote));
        this.internalNrLb = new Label(voteService.getVoteIntNr(vote));
        this.publicBodyLb = new Label(voteService.getVotePublicBodyName(vote));
        this.resultLb = new Label(voteService.getVoteResultAsString(vote));
        this.numbersLb = new Label(voteService.getVoteNumbers(vote));
        
        this.voteOfRoles = voteService.findVoteOfRolesByVoteId(vote.getId());
        this.voteOfRolesBriefLy = new VORs_brief2Layout(voteOfRoles,voteService);
         
        
        this.addComponent(dateLb, 0, 0);
        this.addComponent(internalNrLb, 1, 0);
        this.addComponent(publicBodyLb, 0, 1);
        this.addComponent(resultLb, 0, 3);
        this.addComponent(numbersLb, 1, 3);
        
        this.addComponent(voteOfRolesBriefLy, 0, 4);
        
    }

}