package sk.stefan.mvps.view.components.administrace;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import sk.stefan.interfaces.TabEntity;
import sk.stefan.listeners.SaveListener;
import sk.stefan.mvps.model.entity.A_Role;
import sk.stefan.mvps.model.entity.A_User;
import sk.stefan.mvps.model.entity.A_UserRole;
import sk.stefan.mvps.model.service.SecurityService;
import sk.stefan.mvps.model.service.UserService;
import sk.stefan.utils.Localizator;
import sk.stefan.utils.PresentationNameConverter;
import sk.stefan.utils.StringToDateConverter;

import java.util.Date;

/**
 * Panel pro editaci rolí uživatele.
 * @author elopin on 01.12.2015.
 */
@SpringComponent
@Scope("prototype")
@DesignRoot
public class RoleUzivatelePanel extends CssLayout {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    private Label lblCaption;
    private Button butEdit;
    private VerticalLayout readLayout;
    private Label aktualniRole;
    private Button butShowHistory;
    private Button butHideHistory;
    private Grid historyGrid;
    private VerticalLayout editLayout;
    private ComboBox role;
    private Button butSave;
    private Button butCancel;

    // data
    private A_User user;
    private BeanItemContainer<A_UserRole> container;
    private SaveListener<TabEntity> saveListener;


    public RoleUzivatelePanel()  {
        Design.read(this);
        Localizator.localizeDesign(this);

        container = new BeanItemContainer<>(A_UserRole.class);
        historyGrid.setContainerDataSource(container);
        historyGrid.setHeightMode(HeightMode.ROW);
        historyGrid.getColumn("role_id").setConverter(new PresentationNameConverter<A_Role>(A_Role.class));
        historyGrid.getColumn("since").setConverter(new StringToDateConverter());
        historyGrid.getColumn("till").setConverter(new StringToDateConverter());

        butEdit.addClickListener(event -> setReadOnly(false));
        butCancel.addClickListener(event -> setReadOnly(true));
        butShowHistory.addClickListener(event -> setHistoryVisible(true));
        butHideHistory.addClickListener(event -> setHistoryVisible(false));
        butSave.addClickListener(event -> onSave());
    }

    public void setUzivatel(A_User user) {
        this.user = user;
        role.removeAllItems();
        for (A_Role r : securityService.getAvailableRoles()) {
            role.addItem(r.getId());
            role.setItemCaption(r.getId(), r.getPresentationName());

        }

        A_UserRole userRole = userService.getCurrentUserRoleForUser(user.getId());
        aktualniRole.setValue(userRole != null ? userService.getRoleNameFromUserRole(userRole) : "Žiadna role");

        container.removeAllItems();
        container.addAll(userService.findUserRolesForUser(user));
        historyGrid.setHeightByRows(container.size() > 6 ? 6 : container.size() == 0 ? 1 : container.size());

        setReadOnly(true);
        setHistoryVisible(false);
    }

    public void setSaveListener(SaveListener<TabEntity> saveListener) {
        this.saveListener = saveListener;
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        butEdit.setVisible(readOnly);
        readLayout.setVisible(readOnly);
        editLayout.setVisible(!readOnly);
    }

    private void onSave() {
        if (role.getValue() != null) {
            A_UserRole userRole = new A_UserRole();
            userRole.setVisible(true);
            userRole.setRole_id((Integer) role.getValue());
            userRole.setUser_id(user.getId());
            userRole.setActual(true);
            userRole.setSince(new Date());
            userService.saveUserRole(userRole, false);
            if (saveListener != null) {
                saveListener.save(user);
            }
        }
    }

    private void setHistoryVisible(boolean visible) {
        butShowHistory.setVisible(!visible);
        butHideHistory.setVisible(visible);
        historyGrid.setVisible(visible);
    }
}
