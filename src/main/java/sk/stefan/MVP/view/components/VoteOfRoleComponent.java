/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.MVP.model.entity.dao.VoteOfRole;
import sk.stefan.MVP.model.service.VoteService;

/**
 *
 * @author stefan
 */
public class VoteOfRoleComponent extends GridLayout {

    private static final long serialVersionUID = 1L;

    private final VoteOfRole voteOfRole;

    private final VoteService voteService;
    
    //graficke komponenty:
    private VoteComponent voteComp;
    private Label roleDecisionLb; 

    //0.konstruktor:
    /**
     * @param vor
     * @param vs
     */
    public VoteOfRoleComponent(VoteOfRole vor, VoteService vs) {

        super(2, 1);//column , row
        this.voteOfRole = vor;
        this.voteService = vs;

        this.initLayout();
     
    }

    /**
     */
    private void initLayout() {

        this.removeAllComponents();
        
        this.setSpacing(true);

        Vote vot = voteService.getVote(voteOfRole);//voteService.getVoteResultAsString(, voteService);
        this.voteComp = new VoteComponent(vot, voteService);
        this.roleDecisionLb = new Label(voteOfRole.getDecision().getName());
        
        this.addComponent(voteComp, 0, 0);
        this.addComponent(roleDecisionLb, 1, 0);
        
    }

}
