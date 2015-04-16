package sk.stefan.MVP.view.components;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.BaseTheme;
import sk.stefan.MVP.model.service.SecurityService;
import sk.stefan.MVP.model.serviceImpl.SecurityServiceImpl;
import sk.stefan.MVP.view.V7_AdministrationView;

public class NavigationComponent extends HorizontalLayout {

    /**
     *
     */
    private static final long serialVersionUID = 8811699550804144740L;

    private Button b1, b1a, b2, b3a, b3, b4a, b4, b5, b6, b7;

    private final SecurityService securityService;

    private static Navigator navigator;

    private static NavigationComponent navComp;

    //0.konstruktor.
    /**
     *
     */
    private NavigationComponent(Navigator nav) {

        this.securityService = new SecurityServiceImpl();
        navigator = nav;

        this.initButtons();

    }

    /**
     * Inicializuje potrebne navigacne tlacitka.
     */
    private void initButtons() {
        
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
                        navigator.navigateTo("V2_EnterView");

                        break;
                    default:
                }
            }
        });

        b1a = new Button("Input", (ClickEvent event) -> {
            navigator.navigateTo("inputAllView");
        });

        b2 = new Button("Vstup", (ClickEvent event) -> {
            navigator.navigateTo("V2_EnterView");
        });

        b3a = new Button("Verejné orgány", (ClickEvent event) -> {
            navigator.navigateTo("V3s_PublicBodiesView");
        });
        
        b3 = new Button("Verejný orgán", (ClickEvent event) -> {
            navigator.navigateTo("V3_PublicBodyView");
        });

        b4a = new Button("Verejné osoby", (ClickEvent event) -> {
            navigator.navigateTo("V4s_PublicPersonsView");
        });

        b4 = new Button("Verejná osoba", (ClickEvent event) -> {
            navigator.navigateTo("V4_PublicPersonView");
        });

        b5 = new Button("Verejná funkcia", (ClickEvent event) -> {
            navigator.navigateTo("V5_PublicRoleView");
        });

        b6 = new Button("Hlasovanie", (ClickEvent event) -> {
            navigator.navigateTo("V6_VoteView");
        });

        b7 = new Button("Administrácia", (ClickEvent event) -> {
            navigator.navigateTo("V7_AdministrationView");
        });


        b1.setStyleName(BaseTheme.BUTTON_LINK);
//        b1a.setStyleName(BaseTheme.BUTTON_LINK);
//        b2.setStyleName(BaseTheme.BUTTON_LINK);
//        b3a.setStyleName(BaseTheme.BUTTON_LINK);
//        b3.setStyleName(BaseTheme.BUTTON_LINK);
//        
//        b4.setStyleName(BaseTheme.BUTTON_LINK);
//        b6.setStyleName(BaseTheme.BUTTON_LINK);
//        b7.setStyleName(BaseTheme.BUTTON_LINK);

        this.setSpacing(true);
        this.setMargin(true);

        this.addComponent(b1);
        this.addComponent(b2);
        this.addComponent(b3a);
        this.addComponent(b4a);
        this.addComponent(b5);
        this.addComponent(b6);

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

        navigator.addView("V7_AdministrationView", new V7_AdministrationView(navigator));

        navComp.addComponent(b1a);
        navComp.addComponent(b7);
    }

    /**
     * Ochudobni navigator o views, ktore nepatria do administracie.
     */
    private void ochudobniNavigator() {
        navigator.removeView("V7_AdministrationView");
        navigator.removeView("inputAllView");

        navComp.removeComponent(b1a);
        navComp.removeComponent(b7);

    }

    /**
     * @return the navigator
     */
    public static Navigator getNavigator() {
        return navigator;
    }

}
