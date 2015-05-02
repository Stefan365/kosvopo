/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components;

import com.vaadin.ui.VerticalLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import sk.stefan.MVP.model.entity.VoteOfRole;
import sk.stefan.MVP.model.service.VoteService;
import sk.stefan.interfaces.Filterable;

/**
 *
 * @author stefan
 */
public class VoteOfRolesSimpleLayout extends VerticalLayout implements Filterable {
    
    private static final long serialVersionUID = 43565321L;
    
    private Map<VoteOfRole, VoteOfRoleSimpleComponent> votesOfRoleMap;

    private final VoteService voteService; 
    
    //0.konstruktor
    /**
     * @param votesOfRole
     * @param vots
     */
    public VoteOfRolesSimpleLayout(List<VoteOfRole> votesOfRole, VoteService vots){
        
        this.setSpacing(true);
        this.setMargin(true);

        this.voteService = vots;
        initLayout(votesOfRole);

        
    } 
    
    /**
     */
    private void initLayout(List<VoteOfRole> votesOfRole){
        
        this.removeAllComponents();
        this.votesOfRoleMap = new HashMap<>();
        
        
        VoteOfRoleSimpleComponent votComp;
        
        for (VoteOfRole vor : votesOfRole){
            votComp = new VoteOfRoleSimpleComponent(vor, voteService);
            this.votesOfRoleMap.put(vor, votComp);
            this.addComponent(votComp);
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