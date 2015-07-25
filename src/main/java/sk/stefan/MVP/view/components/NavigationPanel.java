package sk.stefan.MVP.view.components;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.BaseTheme;
import sk.stefan.MVP.model.entity.A_User;
import sk.stefan.MVP.model.service.SecurityService;
import sk.stefan.MVP.model.serviceImpl.SecurityServiceImpl;


/**
 * Komponenta pripojena na MainView, obsahujuca navigacne tlacitka (login, Hlasovania, etc...)
 * 
 */
public class NavigationPanel extends HorizontalLayout {

    /**
     *
     */
    private static final long serialVersionUID = 8811699550804144740L;

    private Button b1, b2, b3s, b4s, b6s, b7;

    private final SecurityService securityService;

    private final Navigator navigator;
    

    //0.konstruktor.
    /**
     *
     * @param nav
     */
    private NavigationPanel(Navigator nav) {

        this.securityService = new SecurityServiceImpl();
        navigator = nav;

        this.initButtons();

    }

    public static NavigationPanel createNavigationComponent() {
        
        Navigator nav = UI.getCurrent().getNavigator(); 
        return new NavigationPanel(nav);
    
    }

    
    /**
     * Inicializuje potrebne navigacne tlacitka.
     */
    private void initButtons() {
        
        String loginCaption;
        A_User usr = UI.getCurrent().getSession().getAttribute(A_User.class);
        if (usr == null){
            loginCaption = "login";
        } else {
            loginCaption="logout";
        }
        
        
        b1 = new Button(loginCaption);
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


        b2 = new Button("Hlavná stránka", (ClickEvent event) -> {
            navigator.navigateTo("V2_EnterView");
        });

        b3s = new Button("Verejné orgány", (ClickEvent event) -> {
            navigator.navigateTo("V3s_PublicBodiesView");
        });
        
        b4s = new Button("Verejné osoby", (ClickEvent event) -> {
            navigator.navigateTo("V4s_PublicPersonsView");
        });

        b6s = new Button("Hlasovania", (ClickEvent event) -> {
            navigator.navigateTo("V6s_VotesView");
        });

        b7 = new Button("Administrácia", (ClickEvent event) -> {
            navigator.navigateTo("V7_AdministrationView");
        });


        b1.setStyleName(BaseTheme.BUTTON_LINK);

        this.setSpacing(true);
        this.setMargin(true);

        this.addComponent(b1);
        this.addComponent(b2);
        this.addComponent(b3s);
        this.addComponent(b4s);
        this.addComponent(b6s);
//        this.addComponent(b7);
        

    }


    public Button getLoginBut() {
        return this.b1;
    }


    /**
     * Ochudobni navigator o views, ktore nepatria do administracie.
     */
    public void ochudobniNavigator() {
        
        this.removeComponent(b7);
    }
    
    /**
     * Po uspesnom nalogovani, obohati navigator o stranky, kt
     */
    public void obohatNavigator() {
        
        this.addComponent(b7);
    }
    
}
