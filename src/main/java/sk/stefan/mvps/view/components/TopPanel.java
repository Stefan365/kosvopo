package sk.stefan.mvps.view.components;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.event.ShortcutAction;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import sk.stefan.mvps.model.entity.A_User;
import sk.stefan.mvps.model.service.SecurityService;
import sk.stefan.mvps.model.service.UserService;

/**
 * Horní panel s přihlášením uživatele.
 *
 * @author elopin on 02.11.2015.
 */
@SpringComponent
@VaadinSessionScope
@DesignRoot
public class TopPanel extends HorizontalLayout {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    private Label lblCaption;
    private HorizontalLayout basic;
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
        lblCaption.setSizeUndefined();
        lblCaption.setValue("Budovanie demokracie");

        butLogin.addClickListener(event -> onLogin());
        butLogout.addClickListener(event -> onLogout());
        butSend.addClickListener(event -> onSend());
        butSend.setClickShortcut(ShortcutAction.KeyCode.ENTER);

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
                Notification.show("prihlásenie prebehlo úspešne!");
                lblUser.setValue(user.getPresentationName());
                lblUser.setVisible(true);
                setLoginFormVisible(false);
                butLogin.setVisible(false);
                butLogout.setVisible(true);
                refreshApplication();
            } else {
                Notification.show("Neplatné prihlasovacie údaje!");
            }
        } else {
            Notification.show("Chyba prihlásenia!");
        }
    }

    private void onLogin() {
        setLoginFormVisible(true);
    }

    private void onLogout() {
        securityService.logout();
        Notification.show("Odhlásenie prebehlo úspešne!");
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
