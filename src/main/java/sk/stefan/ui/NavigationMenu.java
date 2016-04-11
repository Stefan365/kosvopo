package sk.stefan.ui;

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
import sk.stefan.mvps.model.service.LinkService;
import sk.stefan.mvps.model.service.SecurityService;
import sk.stefan.mvps.model.service.UserService;
import sk.stefan.mvps.view.tabs.TabComponent;
import sk.stefan.utils.Localizator;
import sk.stefan.utils.VaadinUtils;

import java.util.TreeMap;


/**
 * Menu aplikace.
 * @author elopin on 02.11.2015.
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

    private Label menu;
    private TreeMap<Integer, Button> menuButtonsMap = new TreeMap<>((s1, s2) -> s1.compareTo(s2));

    public NavigationMenu() {
        setSizeFull();
        setWidth(200, Unit.PIXELS);
        setPrimaryStyleName(ValoTheme.MENU_ROOT);
    }

    public void createMenu() {
        removeAllComponents();
        VerticalLayout buttonLayout = new VerticalLayout();
        buttonLayout.addStyleName(ValoTheme.MENU_PART);
        menu = new Label(Localizator.getLocalizedMessage(getClass().getCanonicalName(), ".cap", null, VaadinUtils.getLocale()));
        menu.setSizeUndefined();
        buttonLayout.addComponent(menu);
        buttonLayout.setComponentAlignment(menu, Alignment.TOP_CENTER);
        buttonLayout.setSpacing(true);
        addComponent(buttonLayout);
        menuButtonsMap.clear();
        context.getBeansWithAnnotation(MenuButton.class).values().forEach(clazz -> {
            MenuButton annotation = clazz.getClass().getAnnotation(MenuButton.class);
            Button menuButton = new Button("", (event) -> Page.getCurrent()
                    .open(linkService.getUriFragmentForTab((Class<? extends TabComponent>) clazz.getClass()), null));
            menuButton.setData(clazz.getClass().getCanonicalName());
            menuButton.setIcon(annotation.icon());
            menuButton.setPrimaryStyleName(ValoTheme.MENU_ITEM);
            if (annotation.name().equals("adminTab")) {
                if (securityService.getCurrentUser() != null && userService.getUserType(securityService.getCurrentUser()) != null) {
                    menuButtonsMap.put(annotation.position(), menuButton);
                }
            } else {
                menuButtonsMap.put(annotation.position(), menuButton);
            }
        });
        menuButtonsMap.values().forEach(button -> buttonLayout.addComponent(button));
        updateLocalization();
    }

    public void updateLocalization() {
        menu.setValue(Localizator.getLocalizedMessage(getClass().getCanonicalName(), ".cap", null, VaadinUtils.getLocale()));
        menuButtonsMap.values().forEach(menuButton -> {
            String menuItemCaption = Localizator.getLocalizedMessage((String) menuButton.getData(), ".cap", null, VaadinUtils.getLocale());
            menuButton.setCaption(menuItemCaption);
        });
    }
}
