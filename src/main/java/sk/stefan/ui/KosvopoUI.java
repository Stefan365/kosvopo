package sk.stefan.ui;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import javax.servlet.annotation.WebServlet;
import sk.stefan.MVP.model.entity.dao.Kraj;
import sk.stefan.MVP.model.entity.dao.User;
import sk.stefan.MVP.view.AddressbookView;
import sk.stefan.MVP.view.FilaManagerView;
import sk.stefan.MVP.view.HomoView;
import sk.stefan.MVP.view.InputAllView;
import sk.stefan.MVP.view.Kos2View;
import sk.stefan.MVP.view.Kos3View;
import sk.stefan.MVP.view.LoginView;
import sk.stefan.MVP.view.UniEditableTableView;
import sk.stefan.MVP.view.VstupniView;
import sk.stefan.MVP.view.components.NavigationComponent;
import sk.stefan.enums.NonEditableFields;

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
        NavigationComponent.createNavComp(navigator);

        navigator.addView("login", new LoginView(navigator));
        navigator.addView("vstupny", new VstupniView());
        //navigator.addView("druhy", new DruhyView(navigator));
        navigator.addView("homo", new HomoView());
        navigator.addView("user", new UniEditableTableView<>(User.class, NonEditableFields.A_USER.getNonEditableParams()));
        navigator.addView("okres", new UniEditableTableView<>(Kraj.class, NonEditableFields.KRAJ.getNonEditableParams()));
        
        navigator.addView("addressbook", new AddressbookView());
        navigator.addView("filamanager", new FilaManagerView());
        //navigator.addView("kos1", new Kos1View(navigator));
        navigator.addView("kos2", new Kos2View());
        navigator.addView("kos3", new Kos3View());
//        navigator.addView("A_kos5", new K4_Verejna_osobaView(navigator));
//        navigator.addView("A_kos4", new K5_PoslanciView(navigator));
//        navigator.addView("A_kos6", new Kos6View(navigator));
//        navigator.addView("download", new LettingUserDownladFile(navigator));
//        navigator.addView("kos8", new Kos8View(navigator));
//        navigator.addView("page1", new Page1(navigator));
//        navigator.addView("page2", new Page2(navigator));
//        navigator.addView("welcome", new Welcome(navigator));
        
        //navigator.navigateTo("login");
        navigator.navigateTo("vstupny");
        

    }

}
