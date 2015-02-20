package sk.stefan.ui;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import javax.servlet.annotation.WebServlet;
import sk.stefan.MVP.view.AddressbookView;
import sk.stefan.MVP.view.FilaManager;
import sk.stefan.MVP.view.HomoView;
import sk.stefan.MVP.view.InputAllView;
import sk.stefan.MVP.view.K4_Verejna_osobaView;
import sk.stefan.MVP.view.K5_PoslanciView;
import sk.stefan.MVP.view.Kos2View;
import sk.stefan.MVP.view.Kos3View;
import sk.stefan.MVP.view.LoginView;
import sk.stefan.MVP.view.VstupniView;
import sk.stefan.MVP.view.Kos6View;

@SuppressWarnings("serial")
public class KosvopoUI extends UI {

    /**
     *
     */
    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = KosvopoUI.class)
    public static class Servlet extends VaadinServlet {
    }

    private Navigator navigator;

    @Override
    protected void init(VaadinRequest request) {

        navigator = new Navigator(this, this);

        navigator.addView("login", new LoginView(navigator));

        navigator.addView("vstupny", new InputAllView(navigator));
        //navigator.addView("druhy", new DruhyView(navigator));
        navigator.addView("homo", new HomoView(navigator));
        navigator.addView("addressbook", new AddressbookView(navigator));
        navigator.addView("filamanager", new FilaManager(navigator));
        //navigator.addView("kos1", new Kos1View(navigator));
        navigator.addView("kos2", new Kos2View(navigator));
        navigator.addView("kos3", new Kos3View(navigator));
        navigator.addView("kos4", new K4_Verejna_osobaView(navigator));
        navigator.addView("kos5", new K5_PoslanciView(navigator));
        navigator.addView("kos6", new Kos6View(navigator));
//        navigator.addView("download", new LettingUserDownladFile(navigator));
//        navigator.addView("kos8", new Kos8View(navigator));
//        navigator.addView("page1", new Page1(navigator));
//        navigator.addView("page2", new Page2(navigator));
//        navigator.addView("welcome", new Welcome(navigator));
        

        navigator.navigateTo("login");

    }

}
