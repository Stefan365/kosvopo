/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.verejnaOsoba;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import java.util.List;

import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import sk.stefan.annotations.MenuButton;
import sk.stefan.annotations.ViewTab;
import sk.stefan.enums.UserType;
import sk.stefan.factories.InputNewEntityButtonFactory;
import sk.stefan.mvps.model.entity.A_User;
import sk.stefan.mvps.model.entity.PublicPerson;
import sk.stefan.mvps.model.entity.TabEntity;
import sk.stefan.mvps.model.service.LinkService;
import sk.stefan.mvps.model.service.PublicPersonService;
import sk.stefan.mvps.model.service.UserService;
import sk.stefan.mvps.model.serviceImpl.PublicPersonServiceImpl;
import sk.stefan.mvps.model.serviceImpl.UserServiceImpl;
import sk.stefan.mvps.view.components.layouts.MyViewLayout;
import sk.stefan.mvps.view.components.layouts.PUPs_briefLayout;
import sk.stefan.mvps.view.components.verejnyOrgan.NewPublicBodyForm;
import sk.stefan.mvps.view.tabs.TabComponent;

import javax.annotation.PostConstruct;

/**
 *
 * @author stefan
 */
@MenuButton(name = "Verejné osoby", position = 2, icon = FontAwesome.USERS)
@ViewTab("osobyTab")
@SpringComponent
@Scope("prototype")
@DesignRoot
public class V4s_PublicPersonsView extends VerticalLayout implements TabComponent {

    @Autowired
    private PublicPersonService publicPersonService;

    @Autowired
    private LinkService linkService;

    // Design
    private TextField searchFd;
    private Grid grid;
    private Button addNewPublicPersonBt;

    //data
    private BeanItemContainer<PublicPerson> container;

    public V4s_PublicPersonsView() {
        Design.read(this);

        container = new BeanItemContainer<>(PublicPerson.class);
        grid.setContainerDataSource(container);
        grid.getColumn("presentationName").setHeaderCaption("Meno verejné osoby");
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
    public String getTabCaption() {
        return "Verejné osoby";
    }

    @Override
    public void show() {
        container.removeAllItems();
        container.addAll(publicPersonService.findAll());
        grid.setHeightByRows(container.size() >= 7 ? 7 : container.size() + 1);
    }

    @Override
    public String getTabId() {
        return "osobyTab";
    }


//    @Override
//    public void enter(ViewChangeListener.ViewChangeEvent event) {
//
//        A_User user = VaadinSession.getCurrent().getAttribute(A_User.class);
//
//        Boolean isVolunteer = Boolean.FALSE;
//        if (user != null) {
//            UserType utype = userService.getUserType(user);
//            //moze byt dobrovolnik, alebo admin.
//            isVolunteer = ((UserType.VOLUNTEER).equals(utype) || (UserType.ADMIN).equals(utype));
//        }
//
//        initAllBasic(isVolunteer);
//
//    }

}
