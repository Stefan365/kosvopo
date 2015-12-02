package sk.stefan.mvps.view.components.administrace;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
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
import sk.stefan.mvps.model.entity.A_Role;
import sk.stefan.mvps.model.entity.A_User;
import sk.stefan.mvps.model.entity.A_UserRole;
import sk.stefan.mvps.model.service.SecurityService;
import sk.stefan.mvps.model.service.UserService;
import sk.stefan.mvps.view.components.ImageComponent;

/**
 * Created by elopin on 29.11.2015.
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
    private Label lblRole;
    private Label lblNick;

    private VerticalLayout editLayout;
    private TextField tfName;
    private TextField tfSurname;
    private TextField tfNick;
    private TextField tfEmail;
    private PasswordField pfPass;
    private PasswordField pfPassConfirm;
    private Button butSave;
    private Button butCancel;
    private Button butRemove;

    // data
    private BeanFieldGroup<A_User> bfg;


    public DetailUzivatelePanel() {
        Design.read(this);

        bfg = new BeanFieldGroup<>(A_User.class);
        bfg.bind(tfName, "first_name");
        bfg.bind(tfSurname, "last_name");
        bfg.bind(tfNick, "login");
        bfg.bind(tfEmail, "e_mail");

        butEdit.addClickListener(event -> setReadOnly(false));
        butCancel.addClickListener(event -> setReadOnly(true));
    }

    public void setUzivatel(A_User user) {
        lblName.setValue(user.getFirst_name() + " " + user.getLast_name());
        lblEmail.setValue(user.getE_mail());
        lblNick.setValue(user.getLogin());
        UserType userType = userService.getUserType(user);
        lblRole.setValue(userType != null ? userType.getName() : "Žiadna aktualná role");
        imageComponent.setImage(user.getImage());

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
