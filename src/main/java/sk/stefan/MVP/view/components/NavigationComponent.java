package sk.stefan.MVP.view.components;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.BaseTheme;
import sk.stefan.MVP.model.entity.dao.Kraj;
import sk.stefan.MVP.model.entity.dao.Location;
import sk.stefan.MVP.model.entity.dao.Okres;
import sk.stefan.MVP.model.entity.dao.PersonClassification;
import sk.stefan.MVP.model.entity.dao.PublicBody;
import sk.stefan.MVP.model.entity.dao.PublicPerson;
import sk.stefan.MVP.model.entity.dao.PublicRole;
import sk.stefan.MVP.model.entity.dao.Role;
import sk.stefan.MVP.model.entity.dao.Subject;
import sk.stefan.MVP.model.entity.dao.Tenure;
import sk.stefan.MVP.model.entity.dao.Theme;
import sk.stefan.MVP.model.entity.dao.User;
import sk.stefan.MVP.model.entity.dao.UserRole;
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.MVP.model.entity.dao.VoteClassification;
import sk.stefan.MVP.model.entity.dao.VoteOfRole;
import sk.stefan.MVP.model.service.SecurityService;
import sk.stefan.MVP.view.UniEditableTableView;
import sk.stefan.enums.NonEditableFields;

public class NavigationComponent extends HorizontalLayout {

    /**
     *
     */
    private static final long serialVersionUID = 8811699550804144740L;

    private final Button b1, b1a, b2, b3, b4, b4a, b4b, b4c, b4d, b4e, b4f, b4g, b4h, 
            b4i, b4j, b4k, b4l, b4m, b4n, b4o, b4p, 
            b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17;
    private HorizontalLayout hl;
    private SecurityService securityService;

    private Navigator navigator;

    private static NavigationComponent navComp;

    private NavigationComponent(Navigator nav) {

        this.navigator = nav;

        b1 = new Button("login");
        b1.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 7834517499543650204L;

            @Override
            public void buttonClick(ClickEvent event) {
                String butN = event.getButton().getCaption();
                switch (butN) {
                    case "login":
                        navigator.navigateTo("login");
                        break;

                    case "logout":
                        securityService.logout();
                        ochudobniNavigator();
                        event.getButton().setCaption("login");
                        Notification.show("odhlásenie prebehlo úspešne!");
                        nav.navigateTo("vstupny");

                        break;
                    default:
                }
            }
        });

        b1a = new Button("A_inputAll", (ClickEvent event) -> {
            navigator.navigateTo("A_inputAll");
        });

        b2 = new Button("vstupny", (ClickEvent event) -> {
            navigator.navigateTo("vstupny");
        });

        b3 = new Button("druhy", (ClickEvent event) -> {
            navigator.navigateTo("druhy");
        });

        b4 = new Button("homo", (ClickEvent event) -> {
            navigator.navigateTo("homo");
        });
        b4a = new Button("user", (ClickEvent event) -> {
            navigator.navigateTo("user");
        });
        b4b = new Button("okres", (ClickEvent event) -> {
            navigator.navigateTo("okres");
        });
        b4c = new Button("kraj", (ClickEvent event) -> {
            navigator.navigateTo("kraj");
        });
        b4d = new Button("location", (ClickEvent event) -> {
            navigator.navigateTo("location");
        });
        b4e = new Button("per_class", (ClickEvent event) -> {
            navigator.navigateTo("per_class");
        });
        b4f = new Button("pub_body", (ClickEvent event) -> {
            navigator.navigateTo("pub_body");
        });
        b4g = new Button("pub_role", (ClickEvent event) -> {
            navigator.navigateTo("pub_role");
        });
        b4h = new Button("role", (ClickEvent event) -> {
            navigator.navigateTo("role");
        });
        b4i = new Button("subject", (ClickEvent event) -> {
            navigator.navigateTo("subject");
        });
        b4j = new Button("tenure", (ClickEvent event) -> {
            navigator.navigateTo("tenure");
        });
        
        
        b4k = new Button("theme", (ClickEvent event) -> {
            navigator.navigateTo("theme");
        });
        b4l = new Button("user_role", (ClickEvent event) -> {
            navigator.navigateTo("user_role");
        });
        
        b4m = new Button("vote", (ClickEvent event) -> {
            navigator.navigateTo("vote");
        });
        b4n = new Button("vote_class", (ClickEvent event) -> {
            navigator.navigateTo("vote_class");
        });
        b4o = new Button("vote_of_role", (ClickEvent event) -> {
            navigator.navigateTo("vote_of_role");
        });
        b4p = new Button("pub_person", (ClickEvent event) -> {
            navigator.navigateTo("pub_person");
        });

        
        b5 = new Button("abook",
                new Button.ClickListener() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public void buttonClick(ClickEvent event) {
                        navigator.navigateTo("addressbook");
                    }
                });

        b6 = new Button("fila", (ClickEvent event) -> {
            navigator.navigateTo("filamanager");
        });

        b7 = new Button("kos1",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        navigator.navigateTo("kos1");
                    }
                });

        b8 = new Button("kos2",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        navigator.navigateTo("kos2");
                    }
                });

        b9 = new Button("kos3",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        navigator.navigateTo("kos3");
                    }
                });

        b10 = new Button("A_kos4",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        navigator.navigateTo("A_kos4");
                    }
                });

        b11 = new Button("A_kos5",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        navigator.navigateTo("A_kos5");
                    }
                });

        b12 = new Button("A_kos6",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        navigator.navigateTo("A_kos6");
                    }
                });

        b13 = new Button("download",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        navigator.navigateTo("download");
                    }
                });

        b14 = new Button("kos8",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        navigator.navigateTo("kos8");
                    }
                });

        b15 = new Button("page1",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        navigator.navigateTo("page1");
                    }
                });

        b16 = new Button("page2",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        navigator.navigateTo("page2");
                    }
                });

        b17 = new Button("welcome",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        navigator.navigateTo("welcome");
                    }
                });

        b1.setStyleName(BaseTheme.BUTTON_LINK);
        b1a.setStyleName(BaseTheme.BUTTON_LINK);
        b2.setStyleName(BaseTheme.BUTTON_LINK);
        b3.setStyleName(BaseTheme.BUTTON_LINK);
        b4.setStyleName(BaseTheme.BUTTON_LINK);
        b4a.setStyleName(BaseTheme.BUTTON_LINK);
        b4b.setStyleName(BaseTheme.BUTTON_LINK);
        b4c.setStyleName(BaseTheme.BUTTON_LINK);
        b4d.setStyleName(BaseTheme.BUTTON_LINK);
        b4e.setStyleName(BaseTheme.BUTTON_LINK);
        b4f.setStyleName(BaseTheme.BUTTON_LINK);
        b4g.setStyleName(BaseTheme.BUTTON_LINK);
        b4h.setStyleName(BaseTheme.BUTTON_LINK);
        b4i.setStyleName(BaseTheme.BUTTON_LINK);
        b4j.setStyleName(BaseTheme.BUTTON_LINK);
        b4k.setStyleName(BaseTheme.BUTTON_LINK);
        b4l.setStyleName(BaseTheme.BUTTON_LINK);
        b4m.setStyleName(BaseTheme.BUTTON_LINK);
        b4n.setStyleName(BaseTheme.BUTTON_LINK);
        b4o.setStyleName(BaseTheme.BUTTON_LINK);
        b4p.setStyleName(BaseTheme.BUTTON_LINK);
        

        b5.setStyleName(BaseTheme.BUTTON_LINK);
        b6.setStyleName(BaseTheme.BUTTON_LINK);
        b7.setStyleName(BaseTheme.BUTTON_LINK);
        b8.setStyleName(BaseTheme.BUTTON_LINK);
        b9.setStyleName(BaseTheme.BUTTON_LINK);
        b10.setStyleName(BaseTheme.BUTTON_LINK);
        b11.setStyleName(BaseTheme.BUTTON_LINK);
        b12.setStyleName(BaseTheme.BUTTON_LINK);
        b13.setStyleName(BaseTheme.BUTTON_LINK);
        b14.setStyleName(BaseTheme.BUTTON_LINK);
        b15.setStyleName(BaseTheme.BUTTON_LINK);
        b16.setStyleName(BaseTheme.BUTTON_LINK);
        b17.setStyleName(BaseTheme.BUTTON_LINK);

        //hl = new HorizontalLayout();
        this.setSpacing(true);
        this.setMargin(true);

        this.addComponent(b1);
        this.addComponent(b2);
//        this.addComponent(b3);
        this.addComponent(b4);
        this.addComponent(b4a);
        this.addComponent(b4b);
        this.addComponent(b4c);
        this.addComponent(b4d);
        this.addComponent(b4e);
        this.addComponent(b4f);
        this.addComponent(b4g);
        this.addComponent(b4h);
        this.addComponent(b4i);
        this.addComponent(b4j);
        this.addComponent(b4k);
        this.addComponent(b4l);
        this.addComponent(b4m);
        this.addComponent(b4n);
        this.addComponent(b4o);
        this.addComponent(b4p);
        
        
        this.addComponent(b5);
        this.addComponent(b6);
//        this.addComponent(b7);
        this.addComponent(b8);
        this.addComponent(b9);
//        this.addComponent(b13);
//        this.addComponent(b14);
//        this.addComponent(b15);
//        this.addComponent(b16);
//        this.addComponent(b17);

    }

    /**
     *
     * @param nav
     */
    public static void createNavComp(Navigator nav) {
        navComp = new NavigationComponent(nav);
    }

    public static Button getLoginBut() {
        return navComp.b1;
    }

    /**
     * provides the singleton. //later to be done by Spring.
     *
     * @return
     */
    public static NavigationComponent getNavComp() {
        return navComp;
    }

    public void addAdminButtons() {
        navComp.addComponent(b1a);
        navComp.addComponent(b10);
        navComp.addComponent(b11);
        navComp.addComponent(b12);
    }

//    public void removeAdminButtons() {
//        navComp.removeComponent(b10);
//        navComp.removeComponent(b11);
//        navComp.removeComponent(b12);
//
//    }
    /**
     * Ochudobni navigator o views, ktore nepatria do administracie.
     */
    private void ochudobniNavigator() {
        navigator.removeView("A_inputAll");
        navigator.removeView("A_kos5");
        navigator.removeView("A_kos4");
        navigator.removeView("A_kos6");

        navComp.removeComponent(b1a);
        navComp.removeComponent(b10);
        navComp.removeComponent(b11);
        navComp.removeComponent(b12);

    }

}
