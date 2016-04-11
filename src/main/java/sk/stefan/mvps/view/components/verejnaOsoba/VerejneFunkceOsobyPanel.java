package sk.stefan.mvps.view.components.verejnaOsoba;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sk.stefan.enums.UserType;
import sk.stefan.mvps.model.entity.PublicBody;
import sk.stefan.mvps.model.entity.PublicPerson;
import sk.stefan.mvps.model.entity.PublicRole;
import sk.stefan.interfaces.TabEntity;
import sk.stefan.mvps.model.entity.Tenure;
import sk.stefan.mvps.model.service.LinkService;
import sk.stefan.mvps.model.service.PublicRoleService;
import sk.stefan.mvps.model.service.SecurityService;
import sk.stefan.mvps.view.components.verejnaRole.NewPublicRoleForm;
import sk.stefan.utils.EnumConverter;
import sk.stefan.utils.Localizator;
import sk.stefan.utils.PresentationNameConverter;

/**
 * Panel se seznamem veřejných funkcí veřejné osoby.
 * @author elopin on 17.11.2015.
 */
@Component
@Scope("prototype")
@DesignRoot
public class VerejneFunkceOsobyPanel extends Panel {

    @Autowired
    private LinkService linkService;

    @Autowired
    private PublicRoleService publicRoleService;

    @Autowired
    private SecurityService securityService;

    // Design
    private TextField searchFd;
    private Grid grid;
    private Button addNewPublicRoleBt;

    // data
    private PublicPerson publicPerson;
    private BeanItemContainer<PublicRole> container;

    public VerejneFunkceOsobyPanel() {
        Design.read(this);
        Localizator.localizeDesign(this);

        container = new BeanItemContainer<>(PublicRole.class);

        grid.setContainerDataSource(container);
        grid.getColumn("public_body_id").setConverter(new PresentationNameConverter<>(PublicBody.class));
        grid.getColumn("tenure_id").setConverter(new PresentationNameConverter<Tenure>(Tenure.class));
        grid.getColumn("name").setConverter(new EnumConverter());
        grid.setHeightMode(HeightMode.ROW);

        grid.addSelectionListener(event -> Page.getCurrent().open(linkService.getUriFragmentForEntity((TabEntity) grid.getSelectedRow()), null));
        addNewPublicRoleBt.addClickListener(event -> Page.getCurrent().open(linkService.getUriFragmentForTabWithParentEntity(NewPublicRoleForm.class, publicPerson.getEntityName(), publicPerson.getId()), null));
    }

    public void setPublicPerson(PublicPerson publicPerson) {
        this.publicPerson = publicPerson;
        container.removeAllItems();
        container.addAll(publicRoleService.getAllPublicRolesOfPublicPerson(publicPerson));
        grid.setHeightByRows(container.size() > 6 ? 6 : container.size() == 0 ? 1 : container.size());

        addNewPublicRoleBt.setVisible(securityService.currentUserHasRole(UserType.ADMIN) || securityService.currentUserHasRole(UserType.VOLUNTEER));
    }
}
