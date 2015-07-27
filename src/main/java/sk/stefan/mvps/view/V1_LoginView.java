package sk.stefan.mvps.view;

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
import org.apache.log4j.Logger;
import sk.stefan.mvps.model.entity.A_User;
import sk.stefan.mvps.model.service.SecurityService;
import sk.stefan.mvps.model.service.UserService;
import sk.stefan.mvps.model.serviceImpl.SecurityServiceImpl;
import sk.stefan.mvps.model.serviceImpl.UserServiceImpl;
import sk.stefan.mvps.view.components.layouts.ViewLayout;
import sk.stefan.ui.KosvopoUI;
import sk.stefan.utils.ToolsNames;

/**
 * View pre login.
 */
@SuppressWarnings("serial")
public class V1_LoginView extends ViewLayout implements View {

    private static final Logger log = Logger.getLogger(V1_LoginView.class);

    //servisy:
    private final SecurityService securityService;
    
    private final UserService userService;

    private Button loginBt;

    private TextField loginTf;

    private PasswordField passwordPf;

//    private InputOptionGroup<Integer> userRoleOg;
    private HorizontalLayout buttonsHl;

    private VerticalLayout formVl;

    private final VerticalLayout temporaryLy;
    private final Navigator nav;

    public V1_LoginView() {
        super("Prihlásenie sa do systému");
        this.nav = UI.getCurrent().getNavigator();

        temporaryLy = new VerticalLayout();
        this.addComponent(temporaryLy);

        //inicializace BIS
        securityService = new SecurityServiceImpl();
        userService = new UserServiceImpl();

        this.initFields();
        this.initLayoutContent();
    }

    private void initLayoutContent() {

        formVl = new VerticalLayout(loginTf, passwordPf, buttonsHl);
        formVl.setMargin(true);
        formVl.setSpacing(true);

        temporaryLy.addComponent(ToolsNames.createPanelCaption("Prihlásenie sa do KOSVOPO"));
        temporaryLy.addComponent(new Panel(formVl));
        temporaryLy.setWidth(300, Sizeable.Unit.PIXELS);

    }

    private void initFields() {
        // Vytvareni komponent
        loginTf = ToolsNames.createFormTextField("login", true);
        passwordPf = ToolsNames.createFormPasswordField("Heslo", true);

        loginBt = new Button("Prihlásiť", new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {

                log.info("1. SOM TU!");
                
                A_User user;

                user = userService.getUserByLogin(loginTf.getValue());
                
                log.info("2. SOM TU! *" + loginTf.getValue() + "*");

                if (user != null) {
                    
                    log.info("3. SOM TU! *" + user.getLogin() + "*");
                    
                    byte[] userPwHash = userService.getEncPasswordByLogin(user.getLogin());
//                    
                    if (securityService.checkPassword(passwordPf.getValue(), userPwHash)) {
                        securityService.login(user);
                        ((KosvopoUI)UI.getCurrent()).getMainView().getNavComp().obohatNavigator();
                        ((KosvopoUI)UI.getCurrent()).getMainView().getNavComp().getLoginBut().setCaption("logout");
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

    
    @Override
    public void enter(ViewChangeEvent event) {
        
        passwordPf.setValue("");
    }

}
