package sk.stefan.mvps.view.components.administrace;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.server.ErrorMessage;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import sk.stefan.enums.UserType;
import sk.stefan.interfaces.TabEntity;
import sk.stefan.listeners.RemoveListener;
import sk.stefan.listeners.SaveListener;
import sk.stefan.mvps.model.entity.A_Role;
import sk.stefan.mvps.model.entity.A_User;
import sk.stefan.mvps.model.entity.A_UserRole;
import sk.stefan.mvps.model.service.SecurityService;
import sk.stefan.mvps.model.service.UserService;
import sk.stefan.mvps.view.components.ImageComponent;
import sk.stefan.utils.Localizator;

/**
 * Panel s informacemi o uživateli.
 * @author elopin on 29.11.2015.
 */
@SpringComponent
@Scope("prototype")
@DesignRoot
public class DetailUzivatelePanel extends CssLayout {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    // Design
    private Label lblCaption;
    private Button butEdit;
    private ImageComponent imageComponent;
    private VerticalLayout readLayout;
    private Label lblName;
    private Label lblEmail;
    private Label lblNick;

    private VerticalLayout editLayout;
    private TextField tfName;
    private TextField tfSurname;
    private TextField tfNick;
    private TextField tfEmail;
    private CheckBox changePass;
    private PasswordField pfPass;
    private PasswordField pfPassConfirm;
    private Button butSave;
    private Button butCancel;
    private Button butRemove;

    // data
    private BeanFieldGroup<A_User> bfg;
    private SaveListener<TabEntity> saveListener;
    private RemoveListener<TabEntity> removeListener;


    public DetailUzivatelePanel() {
        Design.read(this);
        Localizator.localizeDesign(this);

        bfg = new BeanFieldGroup<>(A_User.class);
        bfg.bind(tfName, "first_name");
        bfg.bind(tfSurname, "last_name");
        bfg.bind(tfNick, "login");
        bfg.bind(tfEmail, "e_mail");

        butEdit.addClickListener(event -> setReadOnly(false));
        butCancel.addClickListener(event -> setReadOnly(true));
        butRemove.addClickListener(event -> onRemove());
        butSave.addClickListener(event -> onSave());

        changePass.addValueChangeListener(event -> enablePasswordChangeEnabled(changePass.getValue()));


    }

    private void enablePasswordChangeEnabled(Boolean enabled) {
        pfPass.setEnabled(enabled);
        pfPass.setRequired(enabled);
        pfPassConfirm.setEnabled(enabled);
        pfPassConfirm.setRequired(enabled);
    }

    public void setSaveListener(SaveListener<TabEntity> saveListener) {
        this.saveListener = saveListener;
    }

    public void setRemoveListener(RemoveListener<TabEntity> removeListener) {
        this.removeListener = removeListener;
    }

    public void setUzivatel(A_User user) {
        lblName.setValue(user.getFirst_name() + " " + user.getLast_name());
        lblEmail.setValue(user.getE_mail());
        lblNick.setValue(user.getLogin());
        imageComponent.setImage(user.getImage());

        bfg.setItemDataSource(user);

        setReadOnly(user.getId() != null);
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        butEdit.setVisible(readOnly);
        readLayout.setVisible(readOnly);
        editLayout.setVisible(!readOnly);
        imageComponent.setReadOnly(readOnly);
    }

    private void onSave() {
        if (isValid()) {
            try {
                bfg.commit();
                bfg.getItemDataSource().getBean().setImage(imageComponent.getImage());
                if (changePass.getValue()) {
                    bfg.getItemDataSource().getBean().setPassword(securityService.encryptPassword(pfPass.getValue()));
                }
                if (saveListener != null) {
                    saveListener.save(bfg.getItemDataSource().getBean());
                }
            } catch (FieldGroup.CommitException e) {
                throw new RuntimeException("Nepodařilo se uložit uživatele!", e);
            }

        }
    }

    public boolean isValid() {
        setValidationVisible(true);
        return bfg.isValid() && checkPassword();
    }

    private boolean checkPassword() {
        pfPassConfirm.setComponentError(null);
        if (changePass.getValue()) {
            if (pfPass.isValid() && pfPassConfirm.isValid()) {
                if (!pfPass.getValue().equals(pfPassConfirm.getValue())) {
                    pfPassConfirm.setComponentError(new ErrorMessage() {
                        @Override
                        public ErrorLevel getErrorLevel() {
                            return ErrorLevel.ERROR;
                        }

                        @Override
                        public String getFormattedHtmlMessage() {
                            return "Potvzení hesla musí být stejné jako heslo!";
                        }
                    });
                    return false;
                } else {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    private void onRemove() {
        if (removeListener != null) {
            removeListener.remove(bfg.getItemDataSource().getBean());
        }
    }

    private void setValidationVisible(boolean visible) {
        tfName.setValidationVisible(visible);
        tfSurname.setValidationVisible(visible);
        tfNick.setValidationVisible(visible);
        tfEmail.setValidationVisible(visible);
        pfPass.setValidationVisible(visible);
        pfPassConfirm.setValidationVisible(visible);
    }
}
