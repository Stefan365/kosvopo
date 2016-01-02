/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.verejnyOrgan;

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
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import sk.stefan.annotations.MenuButton;
import sk.stefan.annotations.ViewTab;
import sk.stefan.enums.UserType;
import sk.stefan.mvps.model.entity.PublicBody;
import sk.stefan.interfaces.TabEntity;
import sk.stefan.mvps.model.service.LinkService;
import sk.stefan.mvps.model.service.PublicBodyService;
import sk.stefan.mvps.model.service.SecurityService;
import sk.stefan.mvps.model.service.UserService;
import sk.stefan.mvps.view.tabs.TabComponent;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 *
 * @author stefan
 */
@MenuButton(name = "Verejné orgány", position = 1, icon = FontAwesome.BANK)
@ViewTab("organyTab")
@SpringComponent
@Scope("prototype")
@DesignRoot
public class V3s_PublicBodiesView extends VerticalLayout implements TabComponent {

//    servisy:
    @Autowired
    private PublicBodyService publicBodyService;

    @Autowired
    private UserService userService;

    @Autowired
    private LinkService linkService;

    @Autowired
    private SecurityService securityService;

    // Designové komponenty
    private TextField searchFd;
    private Grid grid;
    private Button addNewPublicBodyBt;

    //data
    private BeanItemContainer<PublicBody> container;

    public V3s_PublicBodiesView (){
        Design.read(this);

        container = new BeanItemContainer<>(PublicBody.class);
        grid.setContainerDataSource(container);
        grid.getColumn("presentationName").setHeaderCaption("Názov verejného orgánu");
        grid.setHeightMode(HeightMode.ROW);
        grid.addSelectionListener(event -> Page.getCurrent().open(linkService.getUriFragmentForEntity((TabEntity) grid.getSelectedRow()), null));

        searchFd.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.LAZY);

        addNewPublicBodyBt.addClickListener(event -> Page.getCurrent().open(linkService.getUriFragmentForTab(NewPublicBodyForm.class), null));
    }

    @PostConstruct
    public void init() {
        searchFd.addTextChangeListener((FieldEvents.TextChangeListener) event -> {
            List<Integer> pbIds = publicBodyService.findPublicBodyIdsByFilter(event.getText());

            container.removeAllItems();
            container.addAll(publicBodyService.findPublicBodies(pbIds));
            grid.setHeightByRows(container.size() > 7 ? 7 : container.size() == 0 ? 1 : container.size());
        });
    }

    @Override
    public String getTabCaption() {
        return "Verejné orgány";
    }

    @Override
    public void show() {
        container.removeAllItems();
        container.addAll(publicBodyService.findAll());
        grid.setHeightByRows(container.size() > 7 ? 7 : container.size() == 0 ? 1 : container.size());

        addNewPublicBodyBt.setVisible(securityService.currentUserHasRole(UserType.ADMIN) || securityService.currentUserHasRole(UserType.VOLUNTEER));
    }

    @Override
    public String getTabId() {
        return "organyTab";
    }
}
