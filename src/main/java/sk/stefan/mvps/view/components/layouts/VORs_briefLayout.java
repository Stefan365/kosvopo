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
import sk.stefan.mvps.model.entity.VoteOfRole;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.mvps.view.components.panContents.VOR_briefPanContent;
import sk.stefan.mvps.view.components.panels.MyBriefPanel;

/**
 *
 * @author stefan
 */
public class VORs_briefLayout extends VerticalLayout implements Filterable {

    private static final long serialVersionUID = 43565321L;

//    servisy:
    private final VoteService voteService;

//    komponenty:
    private TextField searchFd;
    private Map<VoteOfRole, MyBriefPanel<VOR_briefPanContent>> votesOfRoleMap;

    //0.konstruktor
    /**
     * @param votesOfRole
     * @param vots
     */
    public VORs_briefLayout(List<VoteOfRole> votesOfRole, VoteService vots) {

        this.setSpacing(true);
        this.setMargin(true);

        this.voteService = vots;
        initLayout(votesOfRole);

    }

    /**
     */
    private void initLayout(List<VoteOfRole> votesOfRole) {

        VOR_briefPanContent vorCont;
        MyBriefPanel<VOR_briefPanContent> pan;
        
        this.removeAllComponents();
        this.votesOfRoleMap = new HashMap<>();

        this.searchFd = new TextField("Vyhľadávanie");
        this.initSearch();
        this.initSearchListener();
        this.addComponent(searchFd);

        for (VoteOfRole vor : votesOfRole) {

            vorCont = new VOR_briefPanContent(vor, voteService);
            pan = new MyBriefPanel<>(vorCont);
            this.votesOfRoleMap.put(vor, pan);
            this.addComponent(pan);
        
        }

    }

    /**
     * @param votesOfRole
     */
    private void setVotes(List<VoteOfRole> votesOfRole) {

        this.initLayout(votesOfRole);

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
    private void initSearchListener() {

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
    public void applyFilter(List<Integer> votesOfRoleIds) {

        this.setVotes(voteService.findNewVotesOfRole(votesOfRoleIds));

    }

    @Override
    public String getTableName() {

        return null;

    }

}
