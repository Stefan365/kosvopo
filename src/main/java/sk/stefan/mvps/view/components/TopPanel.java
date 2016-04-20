package sk.stefan.mvps.view.components;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.client.ui.VTabsheet;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HasComponents;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.declarative.Design;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import sk.stefan.mvps.model.entity.A_User;
import sk.stefan.mvps.model.service.SecurityService;
import sk.stefan.mvps.model.service.UserService;
import sk.stefan.mvps.view.MainTabsheet;
import sk.stefan.mvps.view.tabs.TabComponent;
import sk.stefan.ui.NavigationMenu;
import sk.stefan.utils.Localizator;
import sk.stefan.utils.VaadinUtils;

import java.util.LinkedList;
import java.util.Locale;
import java.util.function.Consumer;

/**
 * Horní panel s přihlášením uživatele.
 *
 * @author elopin on 02.11.2015.
 */
@SpringComponent
@VaadinSessionScope
@DesignRoot
public class TopPanel extends HorizontalLayout {

    private static final Logger LOG = LoggerFactory.getLogger(TopPanel.class);

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    private Label lblCaption;
    private HorizontalLayout basic;
    private Button butLang;
    private Label lblUser;
    private Button butLogin;
    private Button butLogout;
    private HorizontalLayout loginForm;
    private TextField tfUsername;
    private PasswordField pfPassword;
    private Button butSend;

    private LoginListener loginListener;

    public TopPanel() {
        Design.read(this);
        Localizator.localizeDesign(this);
        lblCaption.setSizeUndefined();

        butLogin.addClickListener(event -> onLogin());
        butLogout.addClickListener(event -> onLogout());
        butSend.addClickListener(event -> onSend());
        butSend.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        butLang.setIcon(new ThemeResource("images/flags/united_states.png"));
        butLang.addClickListener(e -> localizationChanged());
    }

    private void localizationChanged() {
        Locale locale = VaadinSession.getCurrent().getLocale();
        if (locale.equals(Locale.US)) {
            VaadinSession.getCurrent().setLocale(VaadinUtils.DEFAULT_LOCALE);
            butLang.setIcon(new ThemeResource("images/flags/united_states.png"));
        } else {
            VaadinSession.getCurrent().setLocale(VaadinUtils.US_LOCALE);
            butLang.setIcon(new ThemeResource("images/flags/slovakia.png"));
        }
        changeLocale();
    }

    public void changeLocale() {
        LOG.info("Current locale: " + VaadinSession.getCurrent().getLocale());
        walkComponentTree(UI.getCurrent(), (component) -> {
            if (MainTabsheet.class.isAssignableFrom(component.getClass())) {
                MainTabsheet tabsheet = (MainTabsheet) component;
                LinkedList<Component> tabs = new LinkedList<>();
                tabsheet.iterator().forEachRemaining(tab -> {
                    tabs.add(tab);
                });
                tabsheet.removeAllComponents();

                tabs.forEach(tab -> {
                    String caption = Localizator.getLocalizedMessage(tab.getClass().getCanonicalName(), ".cap", null, VaadinUtils.getLocale());
                    if (caption == null) {
                        caption = ((TabComponent) tab).getTabCaption();
                    }
                    tab.setCaption(caption);
                    tabsheet.addTab(tab, caption).setClosable(true);
                });
            } else if (component.getClass().getAnnotation(DesignRoot.class) != null){
                Localizator.localizeDesign(component);
            } else if (NavigationMenu.class.isAssignableFrom(component.getClass())) {
                NavigationMenu navigationMenu = (NavigationMenu) component;
                navigationMenu.updateLocalization();
            }
        });
    }

    private void walkComponentTree(Component component, Consumer<Component> consumer) {
        consumer.accept(component);
        if (component instanceof HasComponents) {
            for (Component child : ((HasComponents)component)) {
                walkComponentTree(child, consumer);
            }
        }
    }

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    private void onSend() {
        A_User user = userService.getUserByLogin(tfUsername.getValue());
        if (user != null) {
            byte[] userPwHash = userService.getEncPasswordByLogin(user.getLogin());
            if (securityService.checkPassword(pfPassword.getValue(), userPwHash)) {
                securityService.login(user);
                Notification.show(Localizator.getLocalizedMessage(getClass().getCanonicalName(), ".notification.loginSuccess", null, VaadinUtils.getLocale()));
                lblUser.setValue(user.getPresentationName());
                lblUser.setVisible(true);
                setLoginFormVisible(false);
                butLogin.setVisible(false);
                butLogout.setVisible(true);
                refreshApplication();
            } else {
                Notification.show(Localizator.getLocalizedMessage(getClass().getCanonicalName(), ".notification.loginIncorrect", null, VaadinUtils.getLocale()));
            }
        } else {
            Notification.show(Localizator.getLocalizedMessage(getClass().getCanonicalName(), ".notification.loginFailed", null, VaadinUtils.getLocale()));
        }
    }

    private void onLogin() {
        setLoginFormVisible(true);
    }

    private void onLogout() {
        securityService.logout();
        Notification.show(Localizator.getLocalizedMessage(getClass().getCanonicalName(), ".notification.logoutSuccess", null, VaadinUtils.getLocale()));
        lblUser.setVisible(false);
        butLogout.setVisible(false);
        butLogin.setVisible(true);
        refreshApplication();
    }

    private void setLoginFormVisible(boolean visible) {
        tfUsername.setValue(null);
        tfUsername.focus();
        pfPassword.setValue(null);
        basic.setVisible(!visible);
        loginForm.setVisible(visible);
    }

    private void refreshApplication() {
        if (loginListener != null) {
            loginListener.refresh();
        }
    }

    public interface LoginListener {
        void refresh();
    }
}
