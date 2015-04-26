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
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.Map;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.A_User;
import sk.stefan.MVP.model.service.SecurityService;
import sk.stefan.MVP.model.service.UserService;
import sk.stefan.MVP.model.serviceImpl.SecurityServiceImpl;
import sk.stefan.MVP.model.serviceImpl.UserServiceImpl;
import sk.stefan.MVP.view.components.InputOptionGroup;
import sk.stefan.MVP.view.components.NavigationComponent;
import sk.stefan.utils.ToolsNazvy;

/**
 * Třída komponenty pro přihlášení do systému
 */
@SuppressWarnings("serial")
public class V1_LoginView extends VerticalLayout implements View {

    private static final Logger log = Logger.getLogger(V1_LoginView.class);

    //servisy:
    private SecurityService securityService;
    private UserService userService;

    private Button loginBt;

    private TextField emailTf;

    private PasswordField passwordPf;

//    private InputOptionGroup<Integer> userRoleOg;
    private HorizontalLayout buttonsHl;

    private VerticalLayout formVl;

    private VerticalLayout temporaryLy;
    private final NavigationComponent navComp;
    private final Navigator nav;

    public V1_LoginView() {

        this.setMargin(true);
        this.setSpacing(true);

        this.nav = UI.getCurrent().getNavigator();

        navComp = NavigationComponent.createNavigationComponent();
        this.addComponent(navComp);

        temporaryLy = new VerticalLayout();
        this.addComponent(temporaryLy);

        //inicializace BIS
        securityService = new SecurityServiceImpl();
        userService = new UserServiceImpl();

        this.initFields();
        this.initLayout();
    }

    private void initLayout() {

        formVl = new VerticalLayout(emailTf, passwordPf, buttonsHl);
        formVl.setMargin(true);
        formVl.setSpacing(true);

        temporaryLy.addComponent(ToolsNazvy.createPanelCaption("Prihlásenie sa do KOSVOPO"));
        temporaryLy.addComponent(new Panel(formVl));
        temporaryLy.setWidth(300, Sizeable.Unit.PIXELS);

    }

    private void initFields() {
        // Vytvareni komponent
        emailTf = ToolsNazvy.createFormTextField("login", true);
        passwordPf = ToolsNazvy.createFormPasswordField("Heslo", true);

        loginBt = new Button("Prihlásiť", new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {

                A_User user;

                user = userService.getUserByLogin(emailTf.getValue());

                if (user != null) {
                    byte[] userPwHash = userService.getEncPasswordByLogin(user.getLogin());

//                    if (true) {
                    if (securityService.checkPassword(passwordPf.getValue(), userPwHash)) {
                        securityService.login(user);
                        navComp.obohatNavigator();
                        navComp.getLoginBut().setCaption("logout");
                        Notification.show("prihlásenie prebehlo úspešne!");
                        nav.navigateTo("V2_EnterView");
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
        buttonsHl = new HorizontalLayout(loginBt);
        buttonsHl.setSizeFull();
        buttonsHl.setSpacing(true);
        buttonsHl.setExpandRatio(loginBt, 1.0f);

    }

//    /**
//     * toto je vlastne zbytočné, pretože náš systém podporuje len 1 roľu naraz.
//     */
//    private void initOptionGroup() throws NoSuchMethodException {
//
//        Map<String, Integer> map = ToolsNazvy.getUserTypes();
//
//        this.userRoleOg = new InputOptionGroup<Integer>(map);
////            this.userRoleOg.select(UserType.values()[0]);
////            this.userRoleOg.select(0);
//        this.userRoleOg.setValue(0);
//
//    }
    @Override
    public void enter(ViewChangeEvent event) {
        Notification.show("VŠECHEN SPĚCH JEST OD ĎÁBLA!");
        passwordPf.setValue("");
    }

}
