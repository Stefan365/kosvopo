package sk.stefan.MVP.view.components;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.BaseTheme;
import java.util.ArrayList;
import java.util.List;
import sk.stefan.MVP.model.service.SecurityService;
import sk.stefan.MVP.model.service.SecurityServiceImpl;

public class NavigationComponent extends HorizontalLayout {

    /**
     *
     */
    private static final long serialVersionUID = 8811699550804144740L;

    private final Button b1, b1a, b2, b3, b4, b6, b7, b8, b9, b10, b11, b12, badv, hlas;

    private SecurityService securityService;

    private static Navigator navigator;

    private static NavigationComponent navComp;

    private NavigationComponent(Navigator nav) {

        this.securityService = new SecurityServiceImpl();
        navigator = nav;

        b1 = new Button("login");
        b1.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 7834517499543650204L;

            @Override
            public void buttonClick(ClickEvent event) {
                String butN = event.getButton().getCaption();
                switch (butN) {
                    case "login":
                        navigator.navigateTo("login");
                        break;

                    case "logout":
                        securityService.logout();
                        ochudobniNavigator();
                        event.getButton().setCaption("login");
                        Notification.show("odhlásenie prebehlo úspešne!");
                        nav.navigateTo("vstupny");

                        break;
                    default:
                }
            }
        });

        b1a = new Button("A_inputAll", (ClickEvent event) -> {
            navigator.navigateTo("A_inputAll");
        });

        b2 = new Button("vstupny", (ClickEvent event) -> {
            navigator.navigateTo("vstupny");
        });

        b3 = new Button("druhy", (ClickEvent event) -> {
            navigator.navigateTo("druhy");
        });

        b4 = new Button("homo", (ClickEvent event) -> {
            navigator.navigateTo("homo");
        });

        b6 = new Button("fila", (ClickEvent event) -> {
            navigator.navigateTo("filamanager");
        });

        List<String> ls = new ArrayList<>();
        b7 = new Button("kos1", (ClickEvent event) -> {
            navigator.navigateTo("kos1");
        });

        b8 = new Button("kos2", (ClickEvent event) -> {
            navigator.navigateTo("kos2");
        });

        b9 = new Button("kos3", (ClickEvent event) -> {
            navigator.navigateTo("kos3");
        });

        b10 = new Button("A_kos4", (ClickEvent event) -> {
            navigator.navigateTo("A_kos4");
        });

        b11 = new Button("A_kos5", (ClickEvent event) -> {
            navigator.navigateTo("A_kos5");
        });

        b12 = new Button("A_kos6", (ClickEvent event) -> {
            navigator.navigateTo("A_kos6");
        });
        
        badv = new Button("admin_view_1", (ClickEvent event) -> {
            navigator.navigateTo("adminview1");
        });
        
        hlas = new Button("hlasovanie", (ClickEvent event) -> {
            navigator.navigateTo("hlasovanie");
        });
        
        
        

//        b13 = new Button("download", (ClickEvent event) -> {
//            navigator.navigateTo("download");
//        });
//
//        b14 = new Button("kos8", (ClickEvent event) -> {
//            navigator.navigateTo("kos8");
//        });
//
//        b15 = new Button("page1", (ClickEvent event) -> {
//            navigator.navigateTo("page1");
//        });
//
//        b16 = new Button("page2", (ClickEvent event) -> {
//            navigator.navigateTo("page2");
//        });
//
//        b17 = new Button("welcome", (ClickEvent event) -> {
//            navigator.navigateTo("welcome");
//        });
        
        b1.setStyleName(BaseTheme.BUTTON_LINK);
        b1a.setStyleName(BaseTheme.BUTTON_LINK);
        b2.setStyleName(BaseTheme.BUTTON_LINK);
        b3.setStyleName(BaseTheme.BUTTON_LINK);
        b4.setStyleName(BaseTheme.BUTTON_LINK);
        b6.setStyleName(BaseTheme.BUTTON_LINK);
        b7.setStyleName(BaseTheme.BUTTON_LINK);
        b8.setStyleName(BaseTheme.BUTTON_LINK);
        b9.setStyleName(BaseTheme.BUTTON_LINK);
        b10.setStyleName(BaseTheme.BUTTON_LINK);
        b11.setStyleName(BaseTheme.BUTTON_LINK);
        b12.setStyleName(BaseTheme.BUTTON_LINK);
        badv.setStyleName(BaseTheme.BUTTON_LINK);
        hlas.setStyleName(BaseTheme.BUTTON_LINK);
        

        this.setSpacing(true);
        this.setMargin(true);

        this.addComponent(b1);
        this.addComponent(b2);
        this.addComponent(b4);
        this.addComponent(b6);
        this.addComponent(b8);
        this.addComponent(b9);
        this.addComponent(hlas);

    }

    /**
     *
     * @param nav
     */
    public static void createNavComp(Navigator nav) {
        navComp = new NavigationComponent(nav);
    }

    public static Button getLoginBut() {
        return navComp.b1;
    }

    /**
     * provides the singleton. //later to be done by Spring.
     *
     * @return
     */
    public static NavigationComponent getNavComp() {
        return navComp;
    }

    public void addAdminButtons() {
        navComp.addComponent(b1a);
        navComp.addComponent(b10);
        navComp.addComponent(b11);
        navComp.addComponent(b12);
        navComp.addComponent(badv);        
    }

    /**
     * Ochudobni navigator o views, ktore nepatria do administracie.
     */
    private void ochudobniNavigator() {
        navigator.removeView("A_inputAll");
        navigator.removeView("A_kos5");
        navigator.removeView("A_kos4");
        navigator.removeView("A_kos6");
        navigator.removeView("adminview1");

        
        navComp.removeComponent(b1a);
        navComp.removeComponent(b10);
        navComp.removeComponent(b11);
        navComp.removeComponent(b12);

    }

    /**
     * @return the navigator
     */
    public static Navigator getNavigator() {
        return navigator;
    }

}
