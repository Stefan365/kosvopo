package sk.stefan.MVP.view;

import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import sk.stefan.MVP.model.entity.dao.User;
import sk.stefan.MVP.model.service.SecurityService;
import sk.stefan.MVP.model.service.SecurityServiceImpl;
import sk.stefan.MVP.model.service.UserService;
import sk.stefan.MVP.model.service.UserServiceImpl;
import sk.stefan.MVP.view.components.NavigationComponent;
import sk.stefan.MVP.view.helpers.Tools;

/**
 * Třída komponenty pro přihlášení do systému
 */
@SuppressWarnings("serial")
public class LoginView extends VerticalLayout implements View {

    private SecurityService securityService;

    private UserService userService;

    private Navigator nav;

    private Button loginBt;

    /**
     * Textové pole pro zadani uzivatelskeho emailu
     */
    private TextField tfEmail;

    /**
     * Textové pole pro zadání hesla
     */
    private PasswordField tfPassword;

    public LoginView(final Navigator nav) {

        this.nav = nav;
        this.addComponent(NavigationComponent.getNavComp());

        //inicializace BIS
        securityService = new SecurityServiceImpl();
        userService = new UserServiceImpl();

        // Vytvareni komponent
        tfEmail = Tools.createFormTextField("login", true);
        tfPassword = Tools.createFormPasswordField("Heslo", true);

        loginBt = new Button("Prihlásiť", new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {

                User user;

                user = userService.getUserByLogin(tfEmail.getValue());

                if (user != null) {
                    byte[] userPwHash = userService.getEncPasswordByLogin(user.getLogin());
                    if (securityService.checkPassword(tfPassword.getValue(), userPwHash)) {
                        securityService.login(user);
                        obohatNavigator();
                        NavigationComponent.getLoginBut().setCaption("logout");
                        Notification.show("prihlásenie prebehlo úspešne!");
                        nav.navigateTo("vstupny");
                    } else {
                        Notification.show("Neplatné prihlasovacie údaje!");
                    }
                } else {
                    Notification.show("Chyba prihlásenia!");
                }
            }
        });

        loginBt.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        loginBt.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        loginBt.setSizeFull();

        // Zarovnani komponent na stranku
        HorizontalLayout hlButtons = new HorizontalLayout(loginBt);
        hlButtons.setSizeFull();
        hlButtons.setSpacing(true);
        hlButtons.setExpandRatio(loginBt, 1.0f);

        VerticalLayout vlForm = new VerticalLayout(tfEmail, tfPassword, loginBt);
        vlForm.setMargin(true);
        vlForm.setSpacing(true);

        this.addComponent(Tools.createPanelCaption("Prihlásenie sa do KOSVOPO5"));
        this.addComponent(new Panel(vlForm));
        this.setWidth(300, Sizeable.Unit.PIXELS);
    }

    /**
     * Po uspesnom nalogovani, obohati navigator o stranky, ktore su beznym
     * uzivatelom neviditelne.
     *
     */
    private void obohatNavigator() {
        nav.addView("A_inputAll", new InputAllView(nav));
        nav.addView("A_kos5", new K5_Verejna_osobaView(nav));
        nav.addView("A_kos4", new K4_PoslanciView(nav));
        nav.addView("A_kos6", new Kos6View(nav));

        NavigationComponent.getNavComp().addAdminButtons();

    }


    @Override
    public void enter(ViewChangeEvent event) {
        tfPassword.setValue("");
    }

}
