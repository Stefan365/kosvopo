package sk.stefan.mvps.view;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import sk.stefan.ui.NavigationMenu;
import sk.stefan.mvps.view.components.TopPanel;

import javax.annotation.PostConstruct;

/*
 * Hlavne view, ktore nesie listu s tlacitkami.
 */
@SpringComponent
@VaadinSessionScope
public class MainView extends VerticalLayout implements TopPanel.LoginListener {

    @Autowired
    private NavigationMenu navigationMenu;

    @Autowired
    private TopPanel topPanel;

    @Autowired
    private SpringViewProvider provider;

    // view container
    private VerticalLayout content;

    public MainView() {
        setSizeFull();
    }

    @PostConstruct
    public void init() {

        topPanel.setLoginListener(this);

        HorizontalLayout split = new HorizontalLayout();
        split.setSizeFull();
        split.addComponent(navigationMenu);

        content = new VerticalLayout();
        content.setSizeFull();
        content.setMargin(true);
        content.addStyleName("view-container");
        split.addComponent(content);
        split.setExpandRatio(content, 1);
        addComponents(topPanel, split);
        setExpandRatio(split, 1);
    }

    public NavigationMenu getNavigationMenu() { return navigationMenu; }

    public ComponentContainer getViewContainer() {
        return content;
    }

    @Override
    public void refresh() {
        navigationMenu.createMenu();
        MainTabsheet tabsheet = (MainTabsheet) provider.getView("");
        tabsheet.refresh();
    }
}
