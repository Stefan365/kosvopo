package sk.stefan.mvps.view.components.administrace;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import sk.stefan.annotations.ViewTab;
import sk.stefan.interfaces.TabEntity;
import sk.stefan.listeners.RemoveListener;
import sk.stefan.listeners.SaveListener;
import sk.stefan.mvps.model.entity.A_User;
import sk.stefan.mvps.model.service.UserService;
import sk.stefan.mvps.view.tabs.TabComponent;

/**
 * Záložka s detailem uživatele.
 * @author elopin on 01.12.2015.
 */
@ViewTab("uzivatel")
@SpringComponent
@Scope("prototype")
@DesignRoot
public class UserTab extends VerticalLayout implements TabComponent {

    @Autowired
    private UserService userService;

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
    public void setSaveListener(SaveListener<TabEntity> saveListener) {
        detailPanel.setSaveListener(saveListener);
        rolePanel.setSaveListener(saveListener);
    }

    @Override
    public void setRemoveListener(RemoveListener<TabEntity> removeListener) {
        detailPanel.setRemoveListener(removeListener);
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
        user = userService.findOneUser(user.getId());
        setEntity(user);
    }

    @Override
    public String getTabId() {
        return "uzivatel" + user.getId();
    }
}
