package sk.stefan.MVP.view.components;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.BaseTheme;
import sk.stefan.MVP.model.service.SecurityService;
import sk.stefan.MVP.model.serviceImpl.SecurityServiceImpl;

public class NavigationComponent extends HorizontalLayout {

    /**
     *
     */
    private static final long serialVersionUID = 8811699550804144740L;

    private Button b1, b1a, b2, b3s, b3, b4s, b4, b5, b6s, b7;

    private final SecurityService securityService;

    private final Navigator navigator;

    //0.konstruktor.
    /**
     *
     * @param nav
     */
    private NavigationComponent(Navigator nav) {

        this.securityService = new SecurityServiceImpl();
        navigator = nav;

        this.initButtons();

    }

    public static NavigationComponent createNavigationComponent() {
        
        Navigator nav = UI.getCurrent().getNavigator(); 
        return new NavigationComponent(nav);
    
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
                        navigator.navigateTo("V1_LoginView");
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

//        b2 = new Button("Vstup", (ClickEvent event) -> {
//            navigator.navigateTo("V2_EnterView");
//        });

        b3s = new Button("Verejné orgány", (ClickEvent event) -> {
            navigator.navigateTo("V3s_PublicBodiesView");
        });
        
        b3 = new Button("Verejný orgán", (ClickEvent event) -> {
            navigator.navigateTo("V3_PublicBodyView");
        });

        b4s = new Button("Verejné osoby", (ClickEvent event) -> {
            navigator.navigateTo("V4s_PublicPersonsView");
        });

//        b4 = new Button("Verejná osoba", (ClickEvent event) -> {
//            navigator.navigateTo("V4_PublicPersonView");
//        });

//        b5 = new Button("Verejná funkcia", (ClickEvent event) -> {
//            navigator.navigateTo("V5_PublicRoleView");
//        });

//        b6 = new Button("Hlasovanie", (ClickEvent event) -> {
//            navigator.navigateTo("V6_VoteView");
//        });
        b6s = new Button("Hlasovania", (ClickEvent event) -> {
            navigator.navigateTo("V6s_VotesView");
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
//        this.addComponent(b2);
        this.addComponent(b3s);
        this.addComponent(b4s);
//        this.addComponent(b5);
        this.addComponent(b6s);

    }


    public Button getLoginBut() {
        return this.b1;
    }


    /**
     * Ochudobni navigator o views, ktore nepatria do administracie.
     */
    public void ochudobniNavigator() {
        
        
//        navigator.removeView("V7_AdministrationView");

        this.removeComponent(b7);

    }
    
    /**
     * Po uspesnom nalogovani, obohati navigator o stranky, kt
     *
     */
    public void obohatNavigator() {
        


        this.addComponent(b7);
    }


    /**
     * @return the navigator
     */
    public Navigator getNavigator() {
        return navigator;
    }

}
