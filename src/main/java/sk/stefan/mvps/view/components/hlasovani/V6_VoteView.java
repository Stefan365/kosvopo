/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.hlasovani;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import sk.stefan.annotations.ViewTab;
import sk.stefan.interfaces.TabEntity;
import sk.stefan.mvps.model.entity.Vote;
import sk.stefan.mvps.model.service.UserService;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.mvps.view.components.DokumentyPanel;
import sk.stefan.mvps.view.tabs.TabComponent;


/**
 *
 * @author stefan
 */
@ViewTab("hlasovani")
@SpringComponent
@Scope("prototype")
@DesignRoot
public final class V6_VoteView extends VerticalLayout implements TabComponent {

    @Autowired
    private VoteService voteService;

    @Autowired
    private UserService userService;

    // Design
    private DetailHlasovaniPanel detailPanel;
    private GrafHlasovaniPanel grafHlasovaniPanel;
    private DokumentyPanel dokumentyPanel;
    private HlasovaniPanel hlasovaniPanel;

    // data
    private Vote vote;
    
    public V6_VoteView() {
        Design.read(this);
    }

    @Override
    public void setEntity(TabEntity tabEntity) {
        this.vote = (Vote) tabEntity;
        detailPanel.setVote(vote);
        dokumentyPanel.setEntity(vote);
        grafHlasovaniPanel.setVote(vote);
        hlasovaniPanel.setVote(vote);
    }

    @Override
    public TabEntity getEntity() {
        return vote;
    }

    @Override
    public String getTabCaption() {
        return vote.getPresentationName();
    }

    @Override
    public void show() {
        vote = voteService.findOne(vote.getId());
        setEntity(vote);
    }

    @Override
    public String getTabId() {
        return "hlasovani" + vote.getId();
    }
}
