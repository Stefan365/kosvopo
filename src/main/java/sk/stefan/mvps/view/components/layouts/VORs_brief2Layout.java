/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.layouts;

import com.vaadin.ui.VerticalLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import sk.stefan.interfaces.Filterable;
import sk.stefan.mvps.model.entity.VoteOfRole;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.mvps.view.components.panContents.VOR_brief2PanContent;
import sk.stefan.mvps.view.components.panels.MyBriefPanel;

/**
 *
 * @author stefan
 */
public class VORs_brief2Layout extends VerticalLayout implements Filterable {
    
    private static final long serialVersionUID = 43565321L;

//    servisy:
    private final VoteService voteService; 
    
//    komponenty:
    private Map<VoteOfRole, MyBriefPanel<VOR_brief2PanContent>> votesOfRoleMap;


    
    
    //0.konstruktor
    /**
     * @param votesOfRole
     * @param vots
     */
    public VORs_brief2Layout(List<VoteOfRole> votesOfRole, VoteService vots){
        
        this.setSpacing(true);
        this.setMargin(true);

        this.voteService = vots;
        initLayout(votesOfRole);

        
    } 
    
    /**
     */
    private void initLayout(List<VoteOfRole> votesOfRole){
        
        VOR_brief2PanContent votCont;
        MyBriefPanel<VOR_brief2PanContent> pan;
        
        this.removeAllComponents();
        this.votesOfRoleMap = new HashMap<>();
        
        
        for (VoteOfRole vor : votesOfRole){
        
            votCont = new VOR_brief2PanContent(vor, voteService);
            pan = new MyBriefPanel<>(votCont);
            this.votesOfRoleMap.put(vor, pan);
            this.addComponent(pan);
        
        }
        
    }
    
    
    /**
     * @param votesOfRole
     */
    private void setVotes(List<VoteOfRole> votesOfRole){
        
        this.initLayout(votesOfRole);
        
    }

    @Override
    public void applyFilter(List<Integer> votesOfRoleIds) {
        
        this.setVotes(voteService.findNewVotesOfRole(votesOfRoleIds));
        
    }

    @Override
    public String getTableName() {
 
        return null;
    
    }

    
}
