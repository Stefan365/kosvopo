package sk.stefan.ui;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import javax.servlet.annotation.WebServlet;
import org.apache.log4j.Logger;
import sk.stefan.mvps.view.MainView;

/**
 * HLAVNA TRIEDA CELEHO SYSTEMU, OD KTOREJ SA VSETKO ODVIJA.
 * 
 */
@com.vaadin.annotations.Theme("mytheme")
@SuppressWarnings("serial")
public class KosvopoUI extends UI {

    private static final Logger log = Logger.getLogger(KosvopoUI.class);

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = KosvopoUI.class, widgetset="sk.stefan.ui.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    
    private MainView mainView;

    @Override
    protected void init(VaadinRequest request) {
        //V BUDUCNOSTI SA TU BUDE MOCT ZAVIEST SPRING.
//        navComp = NavigationComponent.createNavigationComponent();
//        SpringContextHelper helper = 
//                new SpringContextHelper(VaadinServlet.getCurrent().getServletContext());
//         final MyBeanInterface bean = (MyBeanInterface) helper.getBean("myBean");

         mainView = new MainView();
         this.setContent(mainView);
         

        //pociatocna navigacia:
        UI.getCurrent().getNavigator().navigateTo("V2_EnterView");

    }
    
    
    public MainView getMainView() {
        return mainView;
    }




}
