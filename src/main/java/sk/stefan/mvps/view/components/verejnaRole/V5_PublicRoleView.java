/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.verejnaRole;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import sk.stefan.annotations.ViewTab;
import sk.stefan.interfaces.TabEntity;
import sk.stefan.listeners.RemoveListener;
import sk.stefan.listeners.SaveListener;
import sk.stefan.mvps.model.entity.PublicRole;
import sk.stefan.mvps.model.service.PublicRoleService;
import sk.stefan.mvps.model.service.UserService;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.mvps.model.serviceImpl.PublicRoleServiceImpl;
import sk.stefan.mvps.model.serviceImpl.UserServiceImpl;
import sk.stefan.mvps.model.serviceImpl.VoteServiceImpl;
import sk.stefan.mvps.view.components.DokumentyPanel;
import sk.stefan.mvps.view.components.TimelinePanel;
import sk.stefan.mvps.view.tabs.TabComponent;

/**
 * View zobrazujúci roľu danej verejnej osoby.
 *
 * @author stefan
 */
@SpringComponent
@Scope("prototype")
@ViewTab("verejnaRole")
@DesignRoot
public final class V5_PublicRoleView extends VerticalLayout implements TabComponent {

    private static final Logger log = Logger.getLogger(V5_PublicRoleView.class);

    private static final long serialVersionUID = 1L;

    // Design
    private DetailVerejneFunkcePanel detailPanel;
    private DokumentyPanel dokumentyPanel;
    private HlasovaniFunkcePanel hlasovaniFunkcePanel;
    private TimelinePanel timelinePanel;

    //data
    private PublicRole publicRole;

    @Autowired
    private VoteService voteService;

    public V5_PublicRoleView() {
        Design.read(this);
    }

    @Override
    public void setSaveListener(SaveListener<TabEntity> saveListener) {
        detailPanel.setSaveListener(new SaveListener<PublicRole>() {
            @Override
            public void accept(Object l) {
                saveListener.save(publicRole);
            }
        });
    }

    @Override
    public void setRemoveListener(RemoveListener<TabEntity> removeListener) {
        detailPanel.setRemoveListener(new RemoveListener<PublicRole>() {
            @Override
            public void accept(Object l) {
                removeListener.remove(publicRole);
            }
        });
    }

    public void setEntity(TabEntity entity) {
        this.publicRole = (PublicRole) entity;
        detailPanel.setPublicRole(publicRole);
        hlasovaniFunkcePanel.setPublicRole(publicRole);
        timelinePanel.setRelatedEntity(publicRole);
        timelinePanel.setVotes(voteService.getAllVotesForPublicRole(publicRole));
        dokumentyPanel.setEntity(publicRole);
    }

    @Override
    public String getTabCaption() {
        return publicRole.getPresentationName();
    }

    @Override
    public void show() {

    }

    @Override
    public String getTabId() {
        return "verejnaRole" + publicRole.getId();
    }

    @Override
    public TabEntity getEntity() { return publicRole; }
}
