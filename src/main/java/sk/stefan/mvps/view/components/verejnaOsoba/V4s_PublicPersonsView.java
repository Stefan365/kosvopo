/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.verejnaOsoba;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import sk.stefan.annotations.MenuButton;
import sk.stefan.annotations.ViewTab;
import sk.stefan.enums.UserType;
import sk.stefan.interfaces.TabEntity;
import sk.stefan.mvps.model.entity.PublicPerson;
import sk.stefan.mvps.model.service.LinkService;
import sk.stefan.mvps.model.service.PublicPersonService;
import sk.stefan.mvps.model.service.SecurityService;
import sk.stefan.mvps.view.tabs.TabComponent;
import sk.stefan.utils.Localizator;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Záložka se seznamem veřejných osob.
 * @author stefan
 */
@MenuButton(name = "personsTab", position = 2, icon = FontAwesome.USERS)
@ViewTab("osobyTab")
@SpringComponent
@Scope("prototype")
@DesignRoot
public class V4s_PublicPersonsView extends VerticalLayout implements TabComponent {

    @Autowired
    private PublicPersonService publicPersonService;

    @Autowired
    private LinkService linkService;

    @Autowired
    private SecurityService securityService;

    // Design
    private Panel panel;
    private TextField searchFd;
    private Grid grid;
    private Button addNewPublicPersonBt;

    //data
    private BeanItemContainer<PublicPerson> container;

    public V4s_PublicPersonsView() {
        Design.read(this);
        Localizator.localizeDesign(this);

        container = new BeanItemContainer<>(PublicPerson.class);
        grid.setContainerDataSource(container);
        grid.setHeightMode(HeightMode.ROW);
        grid.addSelectionListener(event -> Page.getCurrent().open(linkService.getUriFragmentForEntity((TabEntity) grid.getSelectedRow()), null));

        searchFd.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.LAZY);

        addNewPublicPersonBt.addClickListener(event -> Page.getCurrent().open(linkService.getUriFragmentForTab(NewPublicPersonForm.class), null));
    }

    @PostConstruct
    public void init() {
        searchFd.addTextChangeListener((FieldEvents.TextChangeListener) event -> {

            List<Integer> pbIds = publicPersonService.findPublicPersonsIdsByFilter(event.getText());
            container.removeAllItems();
            container.addAll(publicPersonService.findPublicPersons(pbIds));

            grid.setHeightByRows(container.size() >= 7 ? 7 : container.size() + 1);
        });
    }

    @Override
    public void show() {
        container.removeAllItems();
        container.addAll(publicPersonService.findAll());
        grid.setHeightByRows(container.size() >= 7 ? 7 : container.size() + 1);

        addNewPublicPersonBt.setVisible(securityService.currentUserHasRole(UserType.ADMIN) || securityService.currentUserHasRole(UserType.VOLUNTEER));
    }

    @Override
    public String getTabId() {
        return "osobyTab";
    }
}
