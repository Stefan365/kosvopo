package sk.stefan.MVP.view;



import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import sk.stefan.MVP.view.components.NavigationComponent;

/*
 * Dashboard MainView is a simple HorizontalLayout that wraps the menu on the
 * left and creates a simple container for the navigator on the right.
 */
@SuppressWarnings("serial")
public class MainView extends VerticalLayout {

    private final ComponentContainer content;
    
    private final MyNavigator nav;
    
    private final NavigationComponent navComp;
    
    
    
    public MainView() {
        
//        this.setSizeFull();
//        this.addStyleName("mainview");

        content = new VerticalLayout();
//        content.setSizeFull();
        nav = new MyNavigator(UI.getCurrent(), content);

        navComp = NavigationComponent.createNavigationComponent();

        
        this.addComponents(navComp, content);
        
//        setExpandRatio(content, 0.1f);

        
    }

    /**
     * @return the navComp
     */
    public NavigationComponent getNavComp() {
        return navComp;
    }
}
