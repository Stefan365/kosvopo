/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;
import sk.stefan.mvps.model.entity.A_User;
import sk.stefan.mvps.model.entity.A_UserRole;
import sk.stefan.mvps.model.entity.District;
import sk.stefan.mvps.model.entity.Location;
import sk.stefan.mvps.model.entity.PersonClassification;
import sk.stefan.mvps.model.entity.PublicBody;
import sk.stefan.mvps.model.entity.PublicPerson;
import sk.stefan.mvps.model.entity.PublicRole;
import sk.stefan.mvps.model.entity.Region;
import sk.stefan.mvps.model.entity.Subject;
import sk.stefan.mvps.model.entity.Tenure;
import sk.stefan.mvps.model.entity.Theme;
import sk.stefan.mvps.model.entity.Vote;
import sk.stefan.mvps.model.entity.VoteClassification;
import sk.stefan.mvps.model.entity.VoteOfRole;
import sk.stefan.mvps.model.service.SecurityService;
import sk.stefan.mvps.model.service.UserService;
import sk.stefan.mvps.model.serviceImpl.SecurityServiceImpl;
import sk.stefan.mvps.model.serviceImpl.UserServiceImpl;
import sk.stefan.mvps.view.components.layouts.MyViewLayout;
import sk.stefan.enums.UserType;
import sk.stefan.utils.ToolsNames;

/**
 *
 * @author stefan
 */
public class V7_AdministrationView extends MyViewLayout implements View {

    /**
     *
     */
    private static final long serialVersionUID = 8811699550804144740L;

    private A_User user;

    private Button auserBt, okresBt, krajBt, locBt, pClassBt, pubBodyBt, pubRoleBt,
            subjectBt, tenureBt, themeBt, auserRoleBt, voteBt, vClassBt, vorBt, pubPersonBt;

    private final SecurityService securityService;
    private final UserService userService;

    private final GridLayout mainLayout = new GridLayout(2, 3);

    private final VerticalLayout hodnoteniaLy = new VerticalLayout();
    private final VerticalLayout hlasovanieLy = new VerticalLayout();
    private final VerticalLayout miestoLy = new VerticalLayout();
    private final VerticalLayout politikaLy = new VerticalLayout();
    private final VerticalLayout adminLy = new VerticalLayout();

    private final Label hodLb = new Label("Hodnotenia");
    private final Label hlasLb = new Label("Hlasovanie");
    private final Label miestLb = new Label("Miesto");
    private final Label polLb = new Label("Politika");
    private final Label adminLb = new Label("Administrácia");

    private final Navigator nav;

    //0. konstruktor
    /**
     */
    public V7_AdministrationView() {

        super("Administračná Stránka");
        this.nav = UI.getCurrent().getNavigator();

        this.securityService = new SecurityServiceImpl();
        this.userService = new UserServiceImpl();

        this.addComponent(mainLayout);

        initButtons();

    }

    private void initButtons() {

        auserBt = new Button(A_User.PRES_NAME);
        auserBt.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                nav.navigateTo(ToolsNames.decapit(A_User.TN));
            }
        });

        auserRoleBt = new Button(A_UserRole.PRES_NAME, (Button.ClickEvent event) -> {
            nav.navigateTo(ToolsNames.decapit(A_UserRole.TN));
        });

        okresBt = new Button(District.PRES_NAME, (Button.ClickEvent event) -> {
            nav.navigateTo(ToolsNames.decapit(District.TN));
        });
        krajBt = new Button(Region.PRES_NAME, (Button.ClickEvent event) -> {
            nav.navigateTo(ToolsNames.decapit(Region.TN));
        });
        locBt = new Button(Location.PRES_NAME, (Button.ClickEvent event) -> {
            Notification.show("VŠECHEN SPĚCH JEST OD ĎÁBLA!");
            nav.navigateTo(ToolsNames.decapit(Location.TN));
        });

        pClassBt = new Button(PersonClassification.PRES_NAME, (Button.ClickEvent event) -> {
            nav.navigateTo(ToolsNames.decapit(PersonClassification.TN));
        });

        pubBodyBt = new Button(PublicBody.PRES_NAME, (Button.ClickEvent event) -> {
            nav.navigateTo(ToolsNames.decapit(PublicBody.TN));
        });

        pubRoleBt = new Button(PublicRole.PRES_NAME, (Button.ClickEvent event) -> {
            nav.navigateTo(ToolsNames.decapit(PublicRole.TN));
        });
        subjectBt = new Button(Subject.PRES_NAME, (Button.ClickEvent event) -> {
            nav.navigateTo(ToolsNames.decapit(Subject.TN));
        });
        tenureBt = new Button(Tenure.PRES_NAME, (Button.ClickEvent event) -> {
            nav.navigateTo(ToolsNames.decapit(Tenure.TN));
        });

        themeBt = new Button(Theme.PRES_NAME, (Button.ClickEvent event) -> {
            nav.navigateTo(ToolsNames.decapit(Theme.TN));
        });

        voteBt = new Button(Vote.getPRES_NAME(), (Button.ClickEvent event) -> {
            nav.navigateTo(ToolsNames.decapit(Vote.getTN()));
        });

        vClassBt = new Button(VoteClassification.PRES_NAME, (Button.ClickEvent event) -> {
            nav.navigateTo(ToolsNames.decapit(VoteClassification.TN));
        });

        vorBt = new Button(VoteOfRole.PRES_NAME, (Button.ClickEvent event) -> {
            nav.navigateTo(ToolsNames.decapit(VoteOfRole.TN));
        });
        pubPersonBt = new Button(PublicPerson.PRES_NAME, (Button.ClickEvent event) -> {
            nav.navigateTo(ToolsNames.decapit(PublicPerson.TN));
        });

        auserBt.setStyleName(BaseTheme.BUTTON_LINK);
//        auserRoleBt.setStyleName(BaseTheme.BUTTON_LINK);

        okresBt.setStyleName(BaseTheme.BUTTON_LINK);
        krajBt.setStyleName(BaseTheme.BUTTON_LINK);
        locBt.setStyleName(BaseTheme.BUTTON_LINK);
        pClassBt.setStyleName(BaseTheme.BUTTON_LINK);
        pubBodyBt.setStyleName(BaseTheme.BUTTON_LINK);
        pubRoleBt.setStyleName(BaseTheme.BUTTON_LINK);
        subjectBt.setStyleName(BaseTheme.BUTTON_LINK);
        tenureBt.setStyleName(BaseTheme.BUTTON_LINK);
        themeBt.setStyleName(BaseTheme.BUTTON_LINK);
        voteBt.setStyleName(BaseTheme.BUTTON_LINK);
        vClassBt.setStyleName(BaseTheme.BUTTON_LINK);
        vorBt.setStyleName(BaseTheme.BUTTON_LINK);
        pubPersonBt.setStyleName(BaseTheme.BUTTON_LINK);

    }

    /**
     */
    private void initAllBasic(Boolean isAdmin) {

        this.removeAllComponents();
        mainLayout.removeAllComponents();

        this.initLayout(isAdmin);

        this.addComponents(mainLayout);

    }

    private void initLayout(Boolean isAdmin) {

        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        mainLayout.addComponent(miestoLy, 0, 0);
        mainLayout.addComponent(politikaLy, 1, 0);
        mainLayout.addComponent(hlasovanieLy, 0, 1);
        mainLayout.addComponent(hodnoteniaLy, 1, 1);

        miestoLy.setMargin(true);
        miestoLy.setSpacing(true);
        miestoLy.addComponent(miestLb);
        miestoLy.addComponent(krajBt);
        miestoLy.addComponent(okresBt);
        miestoLy.addComponent(locBt);

        politikaLy.setMargin(true);
        politikaLy.setSpacing(true);
        politikaLy.addComponent(polLb);
        politikaLy.addComponent(pubPersonBt);
        politikaLy.addComponent(tenureBt);
        politikaLy.addComponent(pubBodyBt);
        politikaLy.addComponent(pubRoleBt);

        hlasovanieLy.setMargin(true);
        hlasovanieLy.setSpacing(true);
        hlasovanieLy.addComponent(hlasLb);
        hlasovanieLy.addComponent(themeBt);
        hlasovanieLy.addComponent(subjectBt);
        hlasovanieLy.addComponent(voteBt);
        hlasovanieLy.addComponent(vorBt);

        hodnoteniaLy.setMargin(true);
        hodnoteniaLy.setSpacing(true);
        hodnoteniaLy.addComponent(hodLb);
        hodnoteniaLy.addComponent(pClassBt);
        hodnoteniaLy.addComponent(vClassBt);

//        if (isAdmin) {
        mainLayout.addComponent(adminLy, 1, 2);
        adminLy.setMargin(true);
        adminLy.setSpacing(true);
        adminLy.addComponent(adminLb);
        adminLy.addComponent(auserBt);
//            adminLy.addComponent(auserRoleBt);
//        }
    }

    private void setUserValue(A_User usr) {

        this.user = usr;

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        A_User usr = securityService.getCurrentUser();

        Boolean isAdmin = Boolean.FALSE;

        if (usr != null) {

            UserType utype = userService.getUserType(usr);

            switch (utype) {
                case ADMIN:
                    isAdmin = Boolean.TRUE;
                    break;
                default:
            }

            setUserValue(usr);
            initAllBasic(isAdmin);

        } else {
            UI.getCurrent().getNavigator().navigateTo("V2_EnterView");
        }

    }

}
