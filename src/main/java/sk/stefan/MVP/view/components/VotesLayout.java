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
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.MVP.model.service.VoteService;
import sk.stefan.interfaces.Filterable;

/**
 *
 * @author stefan
 */
public class VotesLayout extends VerticalLayout implements Filterable {
    
    private static final long serialVersionUID = 43565321L;
    
    private Map<Vote, VoteComponent> votesMap;

    private final VoteService voteService; 
    
    //0.konstruktor
    public VotesLayout(List<Vote> votes, VoteService vots){
        
        this.voteService = vots;
        initLayout(votes);

        this.setSpacing(true);
        this.setMargin(true);

        
    } 
    
    /**
     */
    private void initLayout(List<Vote> votes){
        
        VoteComponent votComp;
        this.votesMap = new HashMap<>();
        this.removeAllComponents();
        
        for (Vote vot : votes){
            votComp = new VoteComponent(vot, voteService);
            this.votesMap.put(vot, votComp);
            this.addComponent(votComp);
        }
        
    }
    
    
    /**
     * @param votes
     */
    private void setVotes(List<Vote> votes){
        
        this.initLayout(votes);
        
    }

    @Override
    public void applyFilter(List<Integer> voteIds) {
        
        this.setVotes(voteService.findNewVotes(voteIds));
        
    }

    @Override
    public String getTableName() {
 
//        return "t_district";
        return null;
    
    }

    
}
