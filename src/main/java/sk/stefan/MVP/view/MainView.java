package sk.stefan.MVP.view;

import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import sk.stefan.MVP.view.components.NavigationComponent;

/*
 * Hlavne view, ktore nesie listu s tlacitkami.
 */
@SuppressWarnings("serial")
public class MainView extends VerticalLayout {

    private final ComponentContainer content;
    
    private final MyNavigator nav;
    
    private final NavigationComponent navComp;
    
    
    
    public MainView() {
        
        content = new VerticalLayout();
        
        nav = new MyNavigator(UI.getCurrent(), content);

        navComp = NavigationComponent.createNavigationComponent();

        this.addComponents(navComp, content);
        
    }

    
    public NavigationComponent getNavComp() {
        return navComp;
    }
}
