package sk.stefan.ui;

import com.google.gwt.user.client.rpc.core.java.util.HashMap_CustomFieldSerializer;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import sk.stefan.annotations.MenuButton;
import sk.stefan.enums.UserType;
import sk.stefan.mvps.model.service.LinkService;
import sk.stefan.mvps.model.service.SecurityService;
import sk.stefan.mvps.model.service.UserService;
import sk.stefan.mvps.view.tabs.TabComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


/**
 * Created by elopin on 02.11.2015.
 */
@SpringComponent
@VaadinSessionScope
public class NavigationMenu extends CssLayout {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private LinkService linkService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    public NavigationMenu() {
        setSizeFull();
        setWidth(200, Unit.PIXELS);
        setPrimaryStyleName(ValoTheme.MENU_ROOT);
    }

    public void createMenu() {
        removeAllComponents();
        VerticalLayout buttonLayout = new VerticalLayout();
        buttonLayout.addStyleName(ValoTheme.MENU_PART);
        Label menu = new Label("Menu");
        menu.setSizeUndefined();
        buttonLayout.addComponent(menu);
        buttonLayout.setComponentAlignment(menu, Alignment.TOP_CENTER);
        buttonLayout.setSpacing(true);
        addComponent(buttonLayout);
        TreeMap<Integer, Button> menuButtonsMap = new TreeMap<>((s1, s2) -> s1.compareTo(s2));
        context.getBeansWithAnnotation(MenuButton.class).values().forEach(clazz -> {
            MenuButton annotation = clazz.getClass().getAnnotation(MenuButton.class);
            Button menuButton = new Button(annotation.name(), (event) -> Page.getCurrent()
                    .open(linkService.getUriFragmentForTab((Class<? extends TabComponent>) clazz.getClass()), null));
            menuButton.setIcon(annotation.icon());
            menuButton.setPrimaryStyleName(ValoTheme.MENU_ITEM);
            if (annotation.name().equals("Administrace")) {
                if (securityService.getCurrentUser() != null && userService.getUserType(securityService.getCurrentUser()) != null) {
                    menuButtonsMap.put(annotation.position(), menuButton);
                }
            } else {
                menuButtonsMap.put(annotation.position(), menuButton);
            }
        });
        menuButtonsMap.values().forEach(button -> buttonLayout.addComponent(button));
    }
}
