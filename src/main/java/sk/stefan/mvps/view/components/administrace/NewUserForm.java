package sk.stefan.mvps.view.components.administrace;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.server.ErrorMessage;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import sk.stefan.annotations.ViewTab;
import sk.stefan.enums.UserType;
import sk.stefan.interfaces.TabEntity;
import sk.stefan.listeners.SaveListener;
import sk.stefan.mvps.model.entity.A_User;
import sk.stefan.mvps.model.entity.A_UserRole;
import sk.stefan.mvps.model.service.LinkService;
import sk.stefan.mvps.model.service.SecurityService;
import sk.stefan.mvps.model.service.UserService;
import sk.stefan.mvps.view.components.ImageComponent;
import sk.stefan.mvps.view.tabs.TabComponent;
import sk.stefan.utils.Localizator;

import java.util.Date;

/**
 * Formulář pro vytvoření nového uživatele.
 * @author elopin on 29.11.2015.
 */
@ViewTab("novyUzivatel")
@SpringComponent
@Scope("prototype")
@DesignRoot
public class NewUserForm extends VerticalLayout implements TabComponent {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private LinkService linkService;

    // Design
    private Panel panel;
    private ImageComponent imageComponent;
    private TextField tfName;
    private TextField tfSurname;
    private TextField tfNick;
    private TextField tfEmail;
    private ComboBox cbRole;
    private PasswordField pfPass;
    private PasswordField pfPassConfirm;
    private Button butSave;

    // data
    private BeanFieldGroup<A_User> bfg;

    //TODO odstranit, nebo lépe dodržet návrh
    private SaveListener<TabEntity> saveListener;

    public NewUserForm() {
        Design.read(this);
        Localizator.localizeDesign(this);

        bfg = new BeanFieldGroup<>(A_User.class);
        bfg.bind(tfName, "first_name");
        bfg.bind(tfSurname, "last_name");
        bfg.bind(tfNick, "login");
        bfg.bind(tfEmail, "e_mail");

        butSave.addClickListener(event -> onSave());
    }

    @Override
    public void setSaveListener(SaveListener<TabEntity> saveListener) {
        this.saveListener = saveListener;
    }

    @Override
    public boolean isUserAccessGranted() {
        return securityService.currentUserHasRole(UserType.ADMIN);
    }

    @Override
    public void show() {
        setValidationVisible(false);
        if (bfg.getItemDataSource() == null) {
            bfg.setItemDataSource(new A_User());
            imageComponent.setImage(null);
        }

        cbRole.removeAllItems();
        for (UserType type : UserType.values()) {
            cbRole.addItem(type);
            cbRole.setItemCaption(type, type.getName());
        }

    }

    @Override
    public String getTabId() {
        return "novyUzivatel";
    }


    private void onSave() {
        if (isValid()) {
            try {
                bfg.commit();
                A_User user = bfg.getItemDataSource().getBean();
                user.setVisible(true);
                user.setPassword(securityService.encryptPassword(pfPass.getValue()));
                user.setImage(imageComponent.getImage());
                user = userService.saveUser(user);

                A_UserRole role = new A_UserRole();
                role.setVisible(true);
                role.setUser_id(user.getId());
                role.setRole_id(userService.getRoleByRoleType((UserType) cbRole.getValue()).getId());
                role.setSince(new Date());
                role.setActual(true);
                userService.saveUserRole(role, false);

                Page.getCurrent().open(linkService.getUriFragmentForEntity(user), null);
            } catch (FieldGroup.CommitException e) {
                throw new RuntimeException("Nepodařilo se uložit nového uživatele!", e);
            }
        }
    }

    private boolean isValid() {

        boolean isValid = bfg.isValid() && cbRole.isValid() && pfPass.isValid() && pfPassConfirm.isValid();
        if (isValid && !pfPass.getValue().equals(pfPassConfirm.getValue())) {
            pfPass.setComponentError(new ErrorMessage() {
                @Override
                public ErrorLevel getErrorLevel() {
                    return ErrorLevel.WARNING;
                }

                @Override
                public String getFormattedHtmlMessage() {
                    return "Hesla se neshodují!";
                }
            });
            isValid = false;
        }
        return isValid;
    }

    private void setValidationVisible(boolean visible) {
        tfName.setValidationVisible(visible);
        tfSurname.setValidationVisible(visible);
        tfEmail.setValidationVisible(visible);
        tfNick.setValidationVisible(visible);
        cbRole.setValidationVisible(visible);
        pfPass.setValidationVisible(visible);
        pfPassConfirm.setValidationVisible(visible);
    }
}
