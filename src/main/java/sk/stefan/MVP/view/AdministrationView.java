/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;
import sk.stefan.MVP.model.entity.dao.A_Role;
import sk.stefan.MVP.model.entity.dao.A_User;
import sk.stefan.MVP.model.entity.dao.A_UserRole;
import sk.stefan.MVP.model.entity.dao.Kraj;
import sk.stefan.MVP.model.entity.dao.Location;
import sk.stefan.MVP.model.entity.dao.Okres;
import sk.stefan.MVP.model.entity.dao.PersonClassification;
import sk.stefan.MVP.model.entity.dao.PublicBody;
import sk.stefan.MVP.model.entity.dao.PublicPerson;
import sk.stefan.MVP.model.entity.dao.PublicRole;
import sk.stefan.MVP.model.entity.dao.Subject;
import sk.stefan.MVP.model.entity.dao.Tenure;
import sk.stefan.MVP.model.entity.dao.Theme;
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.MVP.model.entity.dao.VoteClassification;
import sk.stefan.MVP.model.entity.dao.VoteOfRole;
import sk.stefan.MVP.model.service.SecurityService;
import sk.stefan.MVP.model.service.SecurityServiceImpl;
import sk.stefan.MVP.view.components.NavigationComponent;
import static sk.stefan.MVP.view.components.NavigationComponent.getNavigator;
import sk.stefan.utils.Tools;

/**
 *
 * @author stefan
 */
public class AdministrationView extends VerticalLayout implements View {

    /**
     *
     */
    private static final long serialVersionUID = 8811699550804144740L;

    private A_User user;

    private final Button b4a, b4b, b4c, b4d, b4e, b4f, b4g, b4h,
            b4i, b4j, b4k, b4l, b4m, b4n, b4o, b4p;

    private final SecurityService securityService;

    private static Navigator navigator;

    private static NavigationComponent navComp;

    public AdministrationView(Navigator nav) {
        


        this.securityService = new SecurityServiceImpl();

        navigator = nav;

        b4a = new Button(A_User.PRES_NAME, (Button.ClickEvent event) -> {
            getNavigator().navigateTo(Tools.decapit(A_User.getTN()));
        });

        b4b = new Button(Okres.PRES_NAME, (Button.ClickEvent event) -> {
            getNavigator().navigateTo(Tools.decapit(Okres.TN));
        });
        b4c = new Button(Kraj.PRES_NAME, (Button.ClickEvent event) -> {
            getNavigator().navigateTo(Tools.decapit(Kraj.TN));
        });
        b4d = new Button(Location.PRES_NAME, (Button.ClickEvent event) -> {
            getNavigator().navigateTo(Tools.decapit(Location.TN));
        });

        b4e = new Button(PersonClassification.PRES_NAME, (Button.ClickEvent event) -> {
            getNavigator().navigateTo(Tools.decapit(PersonClassification.TN));
        });

        b4f = new Button(PublicBody.PRES_NAME, (Button.ClickEvent event) -> {
            getNavigator().navigateTo(Tools.decapit(PublicBody.TN));
        });

        b4g = new Button(PublicRole.PRES_NAME, (Button.ClickEvent event) -> {
            getNavigator().navigateTo(Tools.decapit(PublicRole.TN));
        });
        b4h = new Button(A_Role.PRES_NAME, (Button.ClickEvent event) -> {
            getNavigator().navigateTo(Tools.decapit(A_Role.TN));
        });
        b4i = new Button(Subject.PRES_NAME, (Button.ClickEvent event) -> {
            getNavigator().navigateTo(Tools.decapit(Subject.TN));
        });
        b4j = new Button(Tenure.PRES_NAME, (Button.ClickEvent event) -> {
            getNavigator().navigateTo(Tools.decapit(Tenure.TN));
        });

        b4k = new Button(Theme.PRES_NAME, (Button.ClickEvent event) -> {
            getNavigator().navigateTo(Tools.decapit(Theme.TN));
        });
        b4l = new Button(A_UserRole.PRES_NAME, (Button.ClickEvent event) -> {
            getNavigator().navigateTo(Tools.decapit(A_UserRole.TN));
        });

        b4m = new Button(Vote.getPRES_NAME(), (Button.ClickEvent event) -> {
            getNavigator().navigateTo(Tools.decapit(Vote.getTN()));
        });

        b4n = new Button(VoteClassification.PRES_NAME, (Button.ClickEvent event) -> {
            getNavigator().navigateTo(Tools.decapit(VoteClassification.TN));
        });

        b4o = new Button(VoteOfRole.PRES_NAME, (Button.ClickEvent event) -> {
            getNavigator().navigateTo(Tools.decapit(VoteOfRole.TN));
        });
        b4p = new Button(PublicPerson.PRES_NAME, (Button.ClickEvent event) -> {
            getNavigator().navigateTo(Tools.decapit(PublicPerson.TN));
        });

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

        this.setSpacing(true);
        this.setMargin(true);

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
        
        this.addComponent(NavigationComponent.getNavComp());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        
        this.addComponent(NavigationComponent.getNavComp());
        user = securityService.getCurrentUser();
        if (user != null) {
            //do nothing
        } else {
            NavigationComponent.getNavigator().navigateTo("vstupny");
        }

    }

}
