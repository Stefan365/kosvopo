/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.panContents;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import sk.stefan.mvps.model.entity.PublicRole;
import sk.stefan.mvps.model.entity.Vote;
import sk.stefan.mvps.model.entity.VoteOfRole;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.mvps.view.components.panContents.PUR_detPanContent;

/**
 *
 * @author stefan
 */
public class VOR_brief2PanContent extends GridLayout {

    private static final long serialVersionUID = 1L;

    private final VoteOfRole voteOfRole;

//    servisy:
    private final VoteService voteService;
//    private final PublicRoleService pubRoleService;
    
    //graficke komponenty:
    private PUR_detPanContent pubRoleComp;
    private Label roleDecisionLb; 

    //0.konstruktor:
    /**
     * @param vor
     * @param vs
     */
    public VOR_brief2PanContent(VoteOfRole vor, VoteService vs) {

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
        pubRoleComp = new PUR_detPanContent(pr, null);
        
        this.addComponent(pubRoleComp, 0, 0);
        this.addComponent(roleDecisionLb, 1, 0);
        
    }

}
