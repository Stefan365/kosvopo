package sk.stefan.mvps.view;

import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import sk.stefan.mvps.view.components.NavigationPanel;

/*
 * Hlavne view, ktore nesie listu s tlacitkami.
 */
@SuppressWarnings("serial")
public class MainView extends VerticalLayout {

    private final ComponentContainer content;
    
    private final MyNavigator nav;
    
    private final NavigationPanel navComp;
    
    
    
    public MainView() {
        
        content = new VerticalLayout();
        
        nav = new MyNavigator(UI.getCurrent(), content);

        navComp = NavigationPanel.createNavigationComponent();

        this.addComponents(navComp, content);
        
    }

    
    public NavigationPanel getNavComp() {
        return navComp;
    }
}
