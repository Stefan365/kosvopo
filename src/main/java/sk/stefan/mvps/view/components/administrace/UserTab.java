package sk.stefan.mvps.view.components.administrace;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.springframework.context.annotation.Scope;
import sk.stefan.annotations.ViewTab;
import sk.stefan.interfaces.TabEntity;
import sk.stefan.mvps.model.entity.A_User;
import sk.stefan.mvps.view.tabs.TabComponent;

/**
 * Created by elopin on 01.12.2015.
 */
@ViewTab("uzivatel")
@SpringComponent
@Scope("prototype")
@DesignRoot
public class UserTab extends VerticalLayout implements TabComponent {

    //Design
    private DetailUzivatelePanel detailPanel;
    private RoleUzivatelePanel rolePanel;

    //data
    private A_User user;

    public UserTab() {
        Design.read(this);
    }

    @Override
    public void setEntity(TabEntity tabEntity) {
        this.user = (A_User) tabEntity;
        detailPanel.setUzivatel(user);
        rolePanel.setUzivatel(user);
    }

    @Override
    public TabEntity getEntity() {
        return user;
    }

    @Override
    public String getTabCaption() {
        return user.getPresentationName();
    }

    @Override
    public void show() {

    }

    @Override
    public String getTabId() {
        return "uzivatel" + user.getId();
    }
}
