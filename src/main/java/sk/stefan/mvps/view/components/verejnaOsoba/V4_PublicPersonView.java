/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.verejnaOsoba;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.spring.annotation.SpringComponent;

import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import sk.stefan.annotations.ViewTab;
import sk.stefan.listeners.RemoveListener;
import sk.stefan.listeners.SaveListener;
import sk.stefan.mvps.model.entity.PublicPerson;
import sk.stefan.mvps.model.entity.PublicRole;
import sk.stefan.interfaces.TabEntity;
import sk.stefan.mvps.model.service.ClassificationService;
import sk.stefan.mvps.model.service.PublicPersonService;
import sk.stefan.mvps.model.service.PublicRoleService;
import sk.stefan.mvps.model.service.UserService;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.mvps.model.serviceImpl.ClassificationServiceImpl;
import sk.stefan.mvps.model.serviceImpl.PublicRoleServiceImpl;
import sk.stefan.mvps.model.serviceImpl.UserServiceImpl;
import sk.stefan.mvps.model.serviceImpl.VoteServiceImpl;
import sk.stefan.mvps.view.components.DokumentyPanel;
import sk.stefan.mvps.view.components.TimelinePanel;
import sk.stefan.mvps.view.tabs.TabComponent;

/**
 *
 * @author stefan
 */
@ViewTab("verejnaOsoba")
@SpringComponent
@Scope("prototype")
@DesignRoot
public final class V4_PublicPersonView extends VerticalLayout implements TabComponent {

    private static final Logger log = Logger.getLogger(V4_PublicPersonView.class);

    @Autowired
    private PublicPersonService publicPersonService;

    @Autowired
    private VoteService voteService;

    // Design
    private DetailOsobyPanel detailPanel;
    private VerejneFunkceOsobyPanel verejneFunkcePanel;
    private TimelinePanel timelinePanel;
    private DokumentyPanel dokumentyPanel;
    // data
    private PublicPerson publicPerson;

    //dvolezita entita, ktora predstavuje aktualnu verejnu funkciu danej osoby.
    //bude vyznacena farebne.
    private PublicRole actualPublicRole;

    public V4_PublicPersonView() {
        Design.read(this);
    }

    @Override
    public void setSaveListener(SaveListener<TabEntity> saveListener) {
        detailPanel.setSaveListener(l -> saveListener.save(publicPerson));
    }

    @Override
    public void setRemoveListener(RemoveListener<TabEntity> removeListener) {
        detailPanel.setRemoveListener(l -> removeListener.remove(publicPerson));
    }

    @Override
    public TabEntity getEntity() {
        return publicPerson;
    }

    @Override
    public void setEntity(TabEntity tabEntity) {
        this.publicPerson = (PublicPerson) tabEntity;
        detailPanel.setPublicPerson(publicPerson);
        verejneFunkcePanel.setPublicPerson(publicPerson);
        timelinePanel.setRelatedEntity(publicPerson);
        timelinePanel.setVotes(voteService.getAllVotesForPublicPerson(publicPerson));
        dokumentyPanel.setEntity(publicPerson);
    }

    @Override
    public String getTabCaption() {
        return publicPerson.getPresentationName();
    }

    @Override
    public void show() {
        publicPerson = publicPersonService.findOne(publicPerson.getId());
        setEntity(publicPerson);
    }

    @Override
    public String getTabId() {
        return "verejnaOsoba" + publicPerson.getId();
    }
}
