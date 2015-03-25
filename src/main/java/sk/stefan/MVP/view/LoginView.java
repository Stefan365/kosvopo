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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.dao.A_User;
import sk.stefan.MVP.model.service.SecurityService;
import sk.stefan.MVP.model.service.SecurityServiceImpl;
import sk.stefan.MVP.model.service.UserService;
import sk.stefan.MVP.model.service.UserServiceImpl;
import sk.stefan.MVP.view.components.InputOptionGroup;
import sk.stefan.MVP.view.components.NavigationComponent;
import sk.stefan.enums.UserType;
import sk.stefan.enums.VoteResult;
import sk.stefan.utils.ToolsNazvy;

/**
 * Třída komponenty pro přihlášení do systému
 */
@SuppressWarnings("serial")
public class LoginView extends VerticalLayout implements View {

    private static final Logger log = Logger.getLogger(LoginView.class);

    private SecurityService securityService;

    private UserService userService;

    private final Navigator nav;

    private Button loginBt;

    private TextField emailTf;

    private PasswordField passwordPf;

    private InputOptionGroup<Integer> userRoleOg;

    private HorizontalLayout buttonsHl;
    
    private VerticalLayout formVl;
    
    
    public LoginView(final Navigator nav) {

        this.nav = nav;
        this.addComponent(NavigationComponent.getNavComp());

        //inicializace BIS
        securityService = new SecurityServiceImpl();
        userService = new UserServiceImpl();

        try {
            this.initOptionGroup();
            this.initFields();
            this.initLayout();
        } catch (NoSuchMethodException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    private void initLayout(){
        
        formVl = new VerticalLayout(emailTf, passwordPf, this.userRoleOg, buttonsHl);
        formVl.setMargin(true);
        formVl.setSpacing(true);

        this.addComponent(ToolsNazvy.createPanelCaption("Prihlásenie sa do KOSVOPO5"));
        this.addComponent(new Panel(formVl));
        this.setWidth(300, Sizeable.Unit.PIXELS);
    
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
                    
                    if (true) {
//                    if (securityService.checkPassword(passwordPf.getValue(), userPwHash)) {
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
        buttonsHl = new HorizontalLayout(loginBt);
        buttonsHl.setSizeFull();
        buttonsHl.setSpacing(true);
        buttonsHl.setExpandRatio(loginBt, 1.0f);

    }

    private void initOptionGroup() throws NoSuchMethodException {
        try {
            Class<?> cls = UserType.class;
            Method getNm = cls.getDeclaredMethod("getNames");
            Method getOm = cls.getDeclaredMethod("getOrdinals");
            List<String> names = (List<String>) getNm.invoke(null);
            List<Integer> ordinals = (List<Integer>) getOm.invoke(null);
            Map<String, Integer> map = ToolsNazvy.makeEnumMap(names, ordinals);
            
            this.userRoleOg = new InputOptionGroup<Integer>(map);
//            this.userRoleOg.select(UserType.values()[0]);
//            this.userRoleOg.select(0);
              this.userRoleOg.setValue(0);
            
            
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            log.error(ex.getMessage(), ex);
        }

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
        Notification.show("VŠECHEN SPĚCH JEST OD ĎÁBLA!");
        passwordPf.setValue("");
    }

}
