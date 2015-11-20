package sk.stefan.ui;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import sk.stefan.mvps.model.entity.A_User;
import sk.stefan.mvps.model.service.SecurityService;

import javax.annotation.PostConstruct;

/**
 * Created by elopin on 02.11.2015.
 */
@SpringComponent
@VaadinSessionScope
public class TopPanel extends HorizontalLayout {

    @Autowired
    private SecurityService securityService;

    private Button butLog;

    public TopPanel() {
        setWidth(100, Unit.PERCENTAGE);
        setMargin(true);
    }

    @PostConstruct
    public void init() {
        butLog = new Button("login", event -> {

            if (securityService.getCurrentUser() == null) {
                UI.getCurrent().getNavigator().navigateTo("loginView");
            } else {
                securityService.logout();
                Notification.show("Odhlásenie prebehlo úspešne!");
                UI.getCurrent().getNavigator().navigateTo("enterView");
            }
        });
        Label caption = new Label("Budovanie demokracie");
        caption.addStyleName(ValoTheme.LABEL_H3);
        caption.setSizeUndefined();
        addComponent(caption);
        addComponent(butLog);

        setComponentAlignment(caption, Alignment.MIDDLE_CENTER);
        setComponentAlignment(butLog, Alignment.MIDDLE_RIGHT);
    }

    public void updateLogButtonCaption(String caption) {
        butLog.setCaption(caption);
    }
}
