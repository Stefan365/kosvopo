/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import sk.stefan.MVP.model.entity.PublicRole;
import sk.stefan.MVP.model.entity.Vote;
import sk.stefan.MVP.model.entity.VoteOfRole;
import sk.stefan.MVP.model.service.VoteService;

/**
 *
 * @author stefan
 */
public class VoteOfRoleSimpleComponent extends GridLayout {

    private static final long serialVersionUID = 1L;

    private final VoteOfRole voteOfRole;

//    servisy:
    private final VoteService voteService;
//    private final PublicRoleService pubRoleService;
    
    //graficke komponenty:
    private PublicRoleSimpleComponent pubRoleComp;
    private Label roleDecisionLb; 

    //0.konstruktor:
    /**
     * @param vor
     * @param vs
     */
    public VoteOfRoleSimpleComponent(VoteOfRole vor, VoteService vs) {

        super(2, 1);//column , row
        this.voteOfRole = vor;
        this.voteService = vs;
//        this.pubRoleService = new PublicRoleServiceImpl();

        this.initLayout();
     
    }

    /**
     */
    private void initLayout() {

        this.removeAllComponents();
        
        this.setSpacing(true);

        Vote vot = voteService.getVote(voteOfRole);//voteService.getVoteResultAsString(, voteService);
        this.roleDecisionLb = new Label(voteOfRole.getDecision().getName());
        
        PublicRole pr = voteService.getPublicRoleById(voteOfRole.getPublic_role_id());
        pubRoleComp = new PublicRoleSimpleComponent(pr, null);
        
        this.addComponent(pubRoleComp, 0, 0);
        this.addComponent(roleDecisionLb, 1, 0);
        
    }

}
