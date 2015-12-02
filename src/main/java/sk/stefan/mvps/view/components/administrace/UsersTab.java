package sk.stefan.mvps.view.components.administrace;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import sk.stefan.annotations.ViewTab;
import sk.stefan.mvps.model.entity.A_User;
import sk.stefan.mvps.model.service.LinkService;
import sk.stefan.mvps.model.service.UserService;
import sk.stefan.mvps.view.tabs.TabComponent;

/**
 * Created by elopin on 29.11.2015.
 */
@ViewTab("uzivateleTab")
@SpringComponent
@Scope("prototype")
@DesignRoot
public class UsersTab extends VerticalLayout implements TabComponent {

    @Autowired
    private UserService userService;

    @Autowired
    private LinkService linkService;

    // Design
    private TextField searchFd;
    private Grid grid;
    private Button addUser;

    // data
    private BeanItemContainer<A_User> container;

    public UsersTab() {
        Design.read(this);

        container = new BeanItemContainer<>(A_User.class);
        grid.setContainerDataSource(container);
        grid.setHeightMode(HeightMode.ROW);
        grid.addSelectionListener(event -> Page.getCurrent().open(linkService.getUriFragmentForEntity((A_User) grid.getSelectedRow()), null));

        addUser.addClickListener(event -> Page.getCurrent().open(linkService.getUriFragmentForTab(NewUserForm.class), null));
    }

    @Override
    public String getTabCaption() {
        return "Použivatelovia";
    }

    @Override
    public void show() {

        container.removeAllItems();
        container.addAll(userService.findAllUsers());
        grid.setHeightByRows(container.size() >= 6 ? 6 : container.size() + 1);
    }

    @Override
    public String getTabId() {
        return "uzivateleTab";
    }
}
