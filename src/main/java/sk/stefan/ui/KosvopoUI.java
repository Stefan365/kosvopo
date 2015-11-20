package sk.stefan.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.declarative.Design;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import sk.stefan.CustomizedFactory;
import sk.stefan.mvps.view.MainView;

import javax.servlet.annotation.WebServlet;

/**
 * HLAVNA TRIEDA CELEHO SYSTEMU, OD KTOREJ SA VSETKO ODVIJA.
 */
@Theme("mytheme")
@Title("Kosvopo")
@SpringUI(path = "/*")
@Widgetset("sk.stefan.ui.AppWidgetSet")
public class KosvopoUI extends UI {

    @Autowired
    private SpringViewProvider viewProvider;

    @Autowired
    private MainView mainView;

    @Autowired
    private CustomizedFactory customizedFactory;

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = KosvopoUI.class)
    public static class Servlet extends SpringVaadinServlet {
    }


    @Override
    protected void init(VaadinRequest request) {
        // musíme přenastavit výchozí továrnu na designové kompnenty, aby fungovalo autowirování
        Design.setComponentFactory(customizedFactory);

        addStyleName(ValoTheme.UI_WITH_MENU);

        Navigator navigator = new Navigator(this, mainView.getViewContainer());
        navigator.addProvider(viewProvider);
        navigator.addViewChangeListener(mainView.getNavigationMenu());
        setNavigator(navigator);

        mainView.getNavigationMenu().createMenu();
        setContent(mainView);
    }
}
