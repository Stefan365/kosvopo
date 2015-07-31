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
import sk.stefan.mvps.view.components.panContents.VOT_briefPanContent;
import sk.stefan.mvps.view.components.panels.MyBriefPanel;

/**
 *
 * @author stefan
 */
public class VOTs_briefLayout extends VerticalLayout implements Filterable {
    
    private static final long serialVersionUID = 43565321L;
    
//    servisy:
    private final VoteService voteService; 
    
//    komponenty:
    private final TextField searchFd; 
    private Map<Vote, MyBriefPanel<VOT_briefPanContent>> votesBriefMap;

    
    //0.konstruktor
    public VOTs_briefLayout(List<Vote> votes, VoteService vots){
        
        
        this.setStyleName("voteLayout");
        
        this.voteService = vots;
        this.searchFd = new TextField("Vyhľadávanie");
        this.initSearch();
        this.initSearchListener();
        this.addComponents(searchFd);
        
        initLayout(votes);

        this.setSpacing(true);
        this.setMargin(true);

        
    } 
    
    /**
     */
    private void initLayout(List<Vote> votes){
        
        VOT_briefPanContent votCont;
        MyBriefPanel<VOT_briefPanContent> pan;
        
        this.removeAllComponents();
        this.votesBriefMap = new HashMap<>();
        
        for (Vote vot : votes){
            votCont = new VOT_briefPanContent(vot, voteService);
            pan = new MyBriefPanel<>(votCont);
            this.votesBriefMap.put(vot, pan);
            this.addComponent(pan);
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
