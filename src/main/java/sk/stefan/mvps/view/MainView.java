package sk.stefan.mvps.view;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import sk.stefan.mvps.view.components.NavigationPanel;
import sk.stefan.ui.NavigationMenu;
import sk.stefan.ui.TopPanel;

/*
 * Hlavne view, ktore nesie listu s tlacitkami.
 */
@SpringComponent
@VaadinSessionScope
public class MainView extends VerticalLayout {

    private static final long serialVersionUID = 2131432L;

    @Autowired
    private NavigationMenu navigationMenu;

    @Autowired
    private TopPanel topPanel;

    // view container
    private VerticalLayout content;

    public MainView() {
        setSizeFull();
    }

    @PostConstruct
    public void init() {

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

    
    public NavigationPanel getNavComp() {
        return null;
//        return navComp;
    }

    public ComponentContainer getViewContainer() {
        return content;
    }
}
