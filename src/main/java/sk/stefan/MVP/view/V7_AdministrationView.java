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
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;
import sk.stefan.MVP.model.entity.dao.A_Role;
import sk.stefan.MVP.model.entity.dao.A_User;
import sk.stefan.MVP.model.entity.dao.A_UserRole;
import sk.stefan.MVP.model.entity.dao.Region;
import sk.stefan.MVP.model.entity.dao.Location;
import sk.stefan.MVP.model.entity.dao.District;
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
import sk.stefan.utils.ToolsNazvy;

/**
 *
 * @author stefan
 */
public class V7_AdministrationView extends VerticalLayout implements View {

    /**
     *
     */
    private static final long serialVersionUID = 8811699550804144740L;

    private A_User user;

    private Button auserBt, okresBt, krajBt, locBt, pClassBt, pubBodyBt, pubRoleBt, aroleBt,
            subjectBt, tenureBt, themeBt, auserRoleBt, voteBt, vClassBt, vorBt, pubPersonBt;

    private final SecurityService securityService;

    private final GridLayout mainLayout = new GridLayout(2, 2);

    private final VerticalLayout hodnoteniaLy = new VerticalLayout();
    private final VerticalLayout hlasovanieLy = new VerticalLayout();
    private final VerticalLayout miestoLy = new VerticalLayout();
    private final VerticalLayout politikaLy = new VerticalLayout();

    private final Label hodLb = new Label("Hodnotenia");
    private final Label hlasLb = new Label("Hlasovanie");
    private final Label miestLb = new Label("Miesto");
    private final Label polLb = new Label("Politika");

    private static Navigator navigator;

    private static NavigationComponent navComp;

    //0. konstruktor
    /**
     * @param nav
     */
    public V7_AdministrationView(Navigator nav) {

        this.securityService = new SecurityServiceImpl();

        navigator = nav;

        this.setSpacing(true);
        this.setMargin(true);
        this.addComponent(mainLayout);
        
        initButtons();
        initLayout();
    }

    private void initButtons() {
        
        auserBt = new Button(A_User.PRES_NAME, (Button.ClickEvent event) -> {
            getNavigator().navigateTo(ToolsNazvy.decapit(A_User.getTN()));
        });

        okresBt = new Button(District.PRES_NAME, (Button.ClickEvent event) -> {
            getNavigator().navigateTo(ToolsNazvy.decapit(District.TN));
        });
        krajBt = new Button(Region.PRES_NAME, (Button.ClickEvent event) -> {
            getNavigator().navigateTo(ToolsNazvy.decapit(Region.TN));
        });
        locBt = new Button(Location.PRES_NAME, (Button.ClickEvent event) -> {
            getNavigator().navigateTo(ToolsNazvy.decapit(Location.TN));
        });

        pClassBt = new Button(PersonClassification.PRES_NAME, (Button.ClickEvent event) -> {
            getNavigator().navigateTo(ToolsNazvy.decapit(PersonClassification.TN));
        });

        pubBodyBt = new Button(PublicBody.PRES_NAME, (Button.ClickEvent event) -> {
            getNavigator().navigateTo(ToolsNazvy.decapit(PublicBody.TN));
        });

        pubRoleBt = new Button(PublicRole.PRES_NAME, (Button.ClickEvent event) -> {
            getNavigator().navigateTo(ToolsNazvy.decapit(PublicRole.TN));
        });
        aroleBt = new Button(A_Role.PRES_NAME, (Button.ClickEvent event) -> {
            getNavigator().navigateTo(ToolsNazvy.decapit(A_Role.TN));
        });
        subjectBt = new Button(Subject.PRES_NAME, (Button.ClickEvent event) -> {
            getNavigator().navigateTo(ToolsNazvy.decapit(Subject.TN));
        });
        tenureBt = new Button(Tenure.PRES_NAME, (Button.ClickEvent event) -> {
            getNavigator().navigateTo(ToolsNazvy.decapit(Tenure.TN));
        });

        themeBt = new Button(Theme.PRES_NAME, (Button.ClickEvent event) -> {
            getNavigator().navigateTo(ToolsNazvy.decapit(Theme.TN));
        });
        auserRoleBt = new Button(A_UserRole.PRES_NAME, (Button.ClickEvent event) -> {
            getNavigator().navigateTo(ToolsNazvy.decapit(A_UserRole.TN));
        });

        voteBt = new Button(Vote.getPRES_NAME(), (Button.ClickEvent event) -> {
            getNavigator().navigateTo(ToolsNazvy.decapit(Vote.getTN()));
        });

        vClassBt = new Button(VoteClassification.PRES_NAME, (Button.ClickEvent event) -> {
            getNavigator().navigateTo(ToolsNazvy.decapit(VoteClassification.TN));
        });

        vorBt = new Button(VoteOfRole.PRES_NAME, (Button.ClickEvent event) -> {
            getNavigator().navigateTo(ToolsNazvy.decapit(VoteOfRole.TN));
        });
        pubPersonBt = new Button(PublicPerson.PRES_NAME, (Button.ClickEvent event) -> {
            getNavigator().navigateTo(ToolsNazvy.decapit(PublicPerson.TN));
        });

        auserBt.setStyleName(BaseTheme.BUTTON_LINK);
        okresBt.setStyleName(BaseTheme.BUTTON_LINK);
        krajBt.setStyleName(BaseTheme.BUTTON_LINK);
        locBt.setStyleName(BaseTheme.BUTTON_LINK);
        pClassBt.setStyleName(BaseTheme.BUTTON_LINK);
        pubBodyBt.setStyleName(BaseTheme.BUTTON_LINK);
        pubRoleBt.setStyleName(BaseTheme.BUTTON_LINK);
        aroleBt.setStyleName(BaseTheme.BUTTON_LINK);
        subjectBt.setStyleName(BaseTheme.BUTTON_LINK);
        tenureBt.setStyleName(BaseTheme.BUTTON_LINK);
        themeBt.setStyleName(BaseTheme.BUTTON_LINK);
        auserRoleBt.setStyleName(BaseTheme.BUTTON_LINK);
        voteBt.setStyleName(BaseTheme.BUTTON_LINK);
        vClassBt.setStyleName(BaseTheme.BUTTON_LINK);
        vorBt.setStyleName(BaseTheme.BUTTON_LINK);
        pubPersonBt.setStyleName(BaseTheme.BUTTON_LINK);

    }

    private void initLayout() {

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



    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        this.addComponent(NavigationComponent.getNavComp());
        user = securityService.getCurrentUser();
        if (user != null) {
            //do nothing
        } else {
            NavigationComponent.getNavigator().navigateTo("V2_EnterView");
        }

    }

}