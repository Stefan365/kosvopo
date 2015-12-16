/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.administrace;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import com.vaadin.ui.themes.BaseTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import sk.stefan.annotations.MenuButton;
import sk.stefan.annotations.ViewTab;
import sk.stefan.enums.UserType;
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
import sk.stefan.mvps.model.service.LinkService;
import sk.stefan.mvps.model.service.SecurityService;
import sk.stefan.mvps.model.service.UserService;
import sk.stefan.mvps.model.serviceImpl.SecurityServiceImpl;
import sk.stefan.mvps.model.serviceImpl.UserServiceImpl;
import sk.stefan.mvps.view.components.administrace.UsersTab;
import sk.stefan.mvps.view.components.layouts.MyViewLayout;
import sk.stefan.mvps.view.tabs.TabComponent;
import sk.stefan.utils.ToolsNames;

/**
 * @author stefan
 */
@MenuButton(name = "Administrace", position = 4, icon = FontAwesome.GEARS)
@ViewTab("administraceTab")
@SpringComponent
@Scope("prototype")
@DesignRoot
public class V7_AdministrationView extends VerticalLayout implements TabComponent {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @Autowired
    private LinkService linkService;

////    komponenty:
//    private final GridLayout mainLayout = new GridLayout(2, 3);
////    tlacitka na editaciu jednotlivych entit:
//    private Button auserBt, okresBt, krajBt, locBt, pClassBt, pubBodyBt, pubRoleBt,
//            subjectBt, tenureBt, themeBt, voteBt, vClassBt, vorBt, pubPersonBt;
////    layouty na rozlozenie tychto tlacitiek do skupin:
//    private final VerticalLayout hodnoteniaLy = new VerticalLayout();
//    private final VerticalLayout hlasovanieLy = new VerticalLayout();
//    private final VerticalLayout miestoLy = new VerticalLayout();
//    private final VerticalLayout politikaLy = new VerticalLayout();
//    private final VerticalLayout adminLy = new VerticalLayout();
////    nazvy jednotlivych skupin:
//    private final Label hodLb = new Label("Hodnotenia");
//    private final Label hlasLb = new Label("Hlasovanie");
//    private final Label miestLb = new Label("Miesto");
//    private final Label polLb = new Label("Politika");
//    private final Label adminLb = new Label("Administrácia");

    private Button butUsers;
    private Button butNewUser;
    private Button butKraj;
    private Button butOkres;
    private Button butMiesto;
    private Button butTema;
    private Button butPredmet;
    private Button butObdobi;


    public V7_AdministrationView() {
        Design.read(this);

        butUsers.addClickListener(event -> Page.getCurrent()
                .open(linkService.getUriFragmentForTab(UsersTab.class), null));
        butNewUser.addClickListener(event -> Page.getCurrent().open(linkService.getUriFragmentForTab(NewUserForm.class), null));
        butKraj.addClickListener(event -> Page.getCurrent().open(linkService.getUriFragmentForTab(KrajeTab.class), null));
        butOkres.addClickListener(event -> Page.getCurrent().open(linkService.getUriFragmentForTab(DistrictTab.class), null));
        butMiesto.addClickListener(event -> Page.getCurrent().open(linkService.getUriFragmentForTab(LocationTab.class), null));
        butTema.addClickListener(event -> Page.getCurrent().open(linkService.getUriFragmentForTab(TemataTab.class), null));
        butPredmet.addClickListener(event -> Page.getCurrent().open(linkService.getUriFragmentForTab(SubjectsTab.class), null));
        butObdobi.addClickListener(event -> Page.getCurrent().open(linkService.getUriFragmentForTab(TenuresTab.class), null));
    }

    @Override
    public String getTabCaption() {
        return "Administrácia";
    }

    @Override
    public void show() {

    }

    @Override
    public String getTabId() {
        return "administraceTab";
    }

//    private void initButtons() {
//
//        auserBt = new Button(A_User.PRES_NAME);
//        auserBt.addClickListener(new Button.ClickListener() {
//            @Override
//            public void buttonClick(Button.ClickEvent event) {
//                nav.navigateTo(ToolsNames.decapit(A_User.TN));
//            }
//        });

//        auserRoleBt = new Button(A_UserRole.PRES_NAME, (Button.ClickEvent event) -> {
//            nav.navigateTo(ToolsNames.decapit(A_UserRole.TN));
//        });

//        okresBt = new Button(District.PRES_NAME, (Button.ClickEvent event) -> {
//            nav.navigateTo(ToolsNames.decapit(District.TN));
//        });
//        krajBt = new Button(Region.PRES_NAME, (Button.ClickEvent event) -> {
//            nav.navigateTo(ToolsNames.decapit(Region.TN));
//        });
//        locBt = new Button(Location.PRES_NAME, (Button.ClickEvent event) -> {
//            Notification.show("VŠECHEN SPĚCH JEST OD ĎÁBLA!");
//            nav.navigateTo(ToolsNames.decapit(Location.TN));
//        });
//
//        pClassBt = new Button(PersonClassification.PRES_NAME, (Button.ClickEvent event) -> {
//            nav.navigateTo(ToolsNames.decapit(PersonClassification.TN));
//        });
//
//        pubBodyBt = new Button(PublicBody.PRES_NAME, (Button.ClickEvent event) -> {
//            nav.navigateTo(ToolsNames.decapit(PublicBody.TN));
//        });
//
//        pubRoleBt = new Button(PublicRole.PRES_NAME, (Button.ClickEvent event) -> {
//            nav.navigateTo(ToolsNames.decapit(PublicRole.TN));
//        });
//        subjectBt = new Button(Subject.PRES_NAME, (Button.ClickEvent event) -> {
//            nav.navigateTo(ToolsNames.decapit(Subject.TN));
//        });
//        tenureBt = new Button(Tenure.PRES_NAME, (Button.ClickEvent event) -> {
//            nav.navigateTo(ToolsNames.decapit(Tenure.TN));
//        });
//
//        themeBt = new Button(Theme.PRES_NAME, (Button.ClickEvent event) -> {
//            nav.navigateTo(ToolsNames.decapit(Theme.TN));
//        });
//
//        voteBt = new Button(Vote.getPRES_NAME(), (Button.ClickEvent event) -> {
//            nav.navigateTo(ToolsNames.decapit(Vote.getTN()));
//        });
//
//        vClassBt = new Button(VoteClassification.PRES_NAME, (Button.ClickEvent event) -> {
//            nav.navigateTo(ToolsNames.decapit(VoteClassification.TN));
//        });
//
//        vorBt = new Button(VoteOfRole.PRES_NAME, (Button.ClickEvent event) -> {
//            nav.navigateTo(ToolsNames.decapit(VoteOfRole.TN));
//        });
//        pubPersonBt = new Button(PublicPerson.PRES_NAME, (Button.ClickEvent event) -> {
//            nav.navigateTo(ToolsNames.decapit(PublicPerson.TN));
//        });
//
//        auserBt.setStyleName(BaseTheme.BUTTON_LINK);
////        auserRoleBt.setStyleName(BaseTheme.BUTTON_LINK);
//
//        okresBt.setStyleName(BaseTheme.BUTTON_LINK);
//        krajBt.setStyleName(BaseTheme.BUTTON_LINK);
//        locBt.setStyleName(BaseTheme.BUTTON_LINK);
//        pClassBt.setStyleName(BaseTheme.BUTTON_LINK);
//        pubBodyBt.setStyleName(BaseTheme.BUTTON_LINK);
//        pubRoleBt.setStyleName(BaseTheme.BUTTON_LINK);
//        subjectBt.setStyleName(BaseTheme.BUTTON_LINK);
//        tenureBt.setStyleName(BaseTheme.BUTTON_LINK);
//        themeBt.setStyleName(BaseTheme.BUTTON_LINK);
//        voteBt.setStyleName(BaseTheme.BUTTON_LINK);
//        vClassBt.setStyleName(BaseTheme.BUTTON_LINK);
//        vorBt.setStyleName(BaseTheme.BUTTON_LINK);
//        pubPersonBt.setStyleName(BaseTheme.BUTTON_LINK);
//
//    }

//    /**
//     */
//    private void initAllBasic(Boolean isAdmin) {
//
//        this.removeAllComponents();
//        mainLayout.removeAllComponents();
//
//        this.initLayout(isAdmin);
//
//        this.addComponents(mainLayout);
//
//    }

//    private void initLayout(Boolean isAdmin) {
//
//        mainLayout.setMargin(true);
//        mainLayout.setSpacing(true);
//
//        mainLayout.addComponent(miestoLy, 0, 0);
//        mainLayout.addComponent(politikaLy, 1, 0);
//        mainLayout.addComponent(hlasovanieLy, 0, 1);
//        mainLayout.addComponent(hodnoteniaLy, 1, 1);
//
//        miestoLy.setMargin(true);
//        miestoLy.setSpacing(true);
//        miestoLy.addComponent(miestLb);
//        miestoLy.addComponent(krajBt);
//        miestoLy.addComponent(okresBt);
//        miestoLy.addComponent(locBt);
//
//        politikaLy.setMargin(true);
//        politikaLy.setSpacing(true);
//        politikaLy.addComponent(polLb);
//        politikaLy.addComponent(pubPersonBt);
//        politikaLy.addComponent(tenureBt);
//        politikaLy.addComponent(pubBodyBt);
//        politikaLy.addComponent(pubRoleBt);
//
//        hlasovanieLy.setMargin(true);
//        hlasovanieLy.setSpacing(true);
//        hlasovanieLy.addComponent(hlasLb);
//        hlasovanieLy.addComponent(themeBt);
//        hlasovanieLy.addComponent(subjectBt);
//        hlasovanieLy.addComponent(voteBt);
//        hlasovanieLy.addComponent(vorBt);
//
//        hodnoteniaLy.setMargin(true);
//        hodnoteniaLy.setSpacing(true);
//        hodnoteniaLy.addComponent(hodLb);
//        hodnoteniaLy.addComponent(pClassBt);
//        hodnoteniaLy.addComponent(vClassBt);
//
////        if (isAdmin) {
//        mainLayout.addComponent(adminLy, 1, 2);
//        adminLy.setMargin(true);
//        adminLy.setSpacing(true);
//        adminLy.addComponent(adminLb);
//        adminLy.addComponent(auserBt);
////        adminLy.addComponent(auserRoleBt);
//
//    }


//    public void enter(ViewChangeListener.ViewChangeEvent event) {
//
//        A_User usr = securityService.getCurrentUser();
//
//        Boolean isAdmin = Boolean.FALSE;
//
//        if (usr != null) {
//
//            UserType utype = userService.getUserType(usr);
//
//            switch (utype) {
//                case ADMIN:
//                    isAdmin = Boolean.TRUE;
//                    break;
//                default:
//            }
//
//            initAllBasic(isAdmin);
//
//        } else {
//            UI.getCurrent().getNavigator().navigateTo("V2_EnterView");
//        }
//
//    }

}