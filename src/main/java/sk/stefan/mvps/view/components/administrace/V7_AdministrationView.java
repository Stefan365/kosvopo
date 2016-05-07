/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.administrace;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import sk.stefan.annotations.MenuButton;
import sk.stefan.annotations.ViewTab;
import sk.stefan.enums.UserType;
import sk.stefan.mvps.model.entity.A_User;
import sk.stefan.mvps.model.service.LinkService;
import sk.stefan.mvps.model.service.SecurityService;
import sk.stefan.mvps.model.service.UserService;
import sk.stefan.mvps.view.tabs.TabComponent;
import sk.stefan.utils.Localizator;

/**
 * Záložka s rozcestníkem administrace.
 *
 * @author stefan
 */
@MenuButton(name = "adminTab", position = 4, icon = FontAwesome.GEARS)
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

    //Design
    private Button butUsers;
    private Button butNewUser;
    private Button butKraj;
    private Button butOkres;
    private Button butMiesto;
    private Button butTema;
    private Button butPredmet;
    private Button butObdobi;
    private Panel adminPanel;
    private Panel politikaPanel;
    private Panel lokacePanel;


    public V7_AdministrationView() {
        Design.read(this);
        Localizator.localizeDesign(this);

        butUsers.addClickListener(event -> Page.getCurrent()
                .open(linkService.getUriFragmentForTab(UsersTab.class), null));
        butNewUser.addClickListener(event -> Page.getCurrent().open(linkService.getUriFragmentForTab(NewUserForm.class), null));
        butKraj.addClickListener(event -> Page.getCurrent().open(linkService.getUriFragmentForTab(KrajeTab.class), null));
        butOkres.addClickListener(event -> Page.getCurrent().open(linkService.getUriFragmentForTab(DistrictTab.class), null));
        butMiesto.addClickListener(event -> Page.getCurrent().open(linkService.getUriFragmentForTab(LocationTab.class), null));
//        butTema.addClickListener(event -> Page.getCurrent().open(linkService.getUriFragmentForTab(TemataTab.class), null));
        butPredmet.addClickListener(event -> Page.getCurrent().open(linkService.getUriFragmentForTab(ZBD_SubjectsTab.class), null));
        butObdobi.addClickListener(event -> Page.getCurrent().open(linkService.getUriFragmentForTab(TenuresTab.class), null));
    }

    @Override
    public void show() {
        if (securityService.currentUserHasRole(UserType.ADMIN)) {
            adminPanel.setVisible(true);
        }
    }

    @Override
    public String getTabId() {
        return "administraceTab";
    }

    @Override
    public boolean isUserAccessGranted() {
        return securityService.currentUserHasRole(UserType.ADMIN) || securityService.currentUserHasRole(UserType.VOLUNTEER);

    }
}
