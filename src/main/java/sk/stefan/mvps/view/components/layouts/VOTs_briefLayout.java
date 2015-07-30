/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.layouts;

import com.vaadin.event.FieldEvents;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import sk.stefan.interfaces.Filterable;
import sk.stefan.mvps.model.entity.Vote;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.mvps.view.components.panels.MyBriefPanel;
import sk.stefan.mvps.view.components.panContents.VOT_briefPanContent;

/**
 *
 * @author stefan
 */
public class VOTs_briefLayout extends VerticalLayout implements Filterable {
    
    private static final long serialVersionUID = 43565321L;
    
//    private Map<Vote, VOT_briefPanContent> votesMap;
    private Map<Vote, MyBriefPanel> votesMap;
    
    private final TextField searchFd; 
    
    private final VerticalLayout temporaryLy;


    private final VoteService voteService; 
    
    //0.konstruktor
    public VOTs_briefLayout(List<Vote> votes, VoteService vots){
        
        
        this.setStyleName("voteLayout");
        
        this.voteService = vots;
        this.temporaryLy = new VerticalLayout();
        this.searchFd = new TextField("Vyhľadávanie");
        this.initSearch();
        this.initSearchListener();
        
        this.addComponents(searchFd, temporaryLy);
        
        initLayout(votes);

        this.setSpacing(true);
        this.setMargin(true);

        
    } 
    
    /**
     */
    private void initLayout(List<Vote> votes){
        
        temporaryLy.removeAllComponents();
        MyBriefPanel pan;
        VOT_briefPanContent votComp;
        this.votesMap = new HashMap<>();
        
        for (Vote vot : votes){
            votComp = new VOT_briefPanContent(vot, voteService);
            pan = new MyBriefPanel(votComp);
            this.votesMap.put(vot, pan);
            temporaryLy.addComponent(pan);
        }
        
    }
    
    
    /**
     * @param votes
     */
    private void setVotes(List<Vote> votes){
        
        this.initLayout(votes);
        
    }
    
   //3.
    /**
     */
    private void initSearch() {
        
        searchFd.setWidth("40%");
        searchFd.setInputPrompt("možeš použiť vyhľadávanie");
        searchFd.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.LAZY);
        
    }
    
    /**
     * Initializes listener
     */
    private void initSearchListener(){
        
        searchFd.addTextChangeListener(new FieldEvents.TextChangeListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void textChange(final FieldEvents.TextChangeEvent event) {
        
                String tx = event.getText();
                List<Integer> votIds = voteService.findNewVoteIdsByFilter(tx);
                applyFilter(votIds);
                
            }
        });
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
