package sk.stefan.mvps.view.components.verejnaOsoba;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import sk.stefan.enums.UserType;
import sk.stefan.listeners.RemoveListener;
import sk.stefan.listeners.SaveListener;
import sk.stefan.mvps.model.entity.PublicPerson;
import sk.stefan.mvps.model.service.PublicRoleService;
import sk.stefan.mvps.model.service.SecurityService;
import sk.stefan.mvps.view.components.ImageComponent;
import sk.stefan.utils.DateTimeUtils;

import java.text.SimpleDateFormat;
import java.util.StringJoiner;

/**
 * Created by elopin on 17.11.2015.
 */
@SpringComponent
@Scope("prototype")
@DesignRoot
public class DetailOsobyPanel extends CssLayout {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private PublicRoleService publicRoleService;

    //Design
    private ImageComponent imageComponent;
    private Label lblCaption;
    private Button butEdit;
    private VerticalLayout readLayout;
    private Label lblName;
    private Label lblDatumNarozeni;
    private Label lblAktualniRole;
    private VerticalLayout editLayout;
    private TextField tfJmeno;
    private TextField tfPrijmeni;
    private PopupDateField dfDatumNarozeni;
    private Button butSave;
    private Button butCancel;
    private Button butRemove;

    //data
    private PublicPerson publicPerson;
    private BeanFieldGroup<PublicPerson> bfg;

    private SaveListener<PublicPerson> saveListener;
    private RemoveListener<PublicPerson> removeListener;

    public DetailOsobyPanel() {
        Design.read(this);
        bfg = new BeanFieldGroup<>(PublicPerson.class);
        bfg.bind(tfJmeno, "first_name");
        bfg.bind(tfPrijmeni, "last_name");
        bfg.bind(dfDatumNarozeni, "date_of_birth");

        dfDatumNarozeni.setDateFormat(DateTimeUtils.getDatePattern());

        butEdit.addClickListener(event -> setReadOnly(false));
        butCancel.addClickListener(event -> onCancel());
        butSave.addClickListener(event -> onSave());
        butRemove.addClickListener(event -> onRemove());
    }

    public void setSaveListener(SaveListener<PublicPerson> saveListener) {
        this.saveListener = saveListener;
    }

    public void setRemoveListener(RemoveListener<PublicPerson> removeListener) {
        this.removeListener = removeListener;
    }

    public void setPublicPerson(PublicPerson publicPerson) {
        this.publicPerson = publicPerson;
        bfg.setItemDataSource(publicPerson);

        lblName.setValue(publicPerson.getPresentationName());
        lblDatumNarozeni.setValue(DateTimeUtils.getDateFormat().format(publicPerson.getDate_of_birth()));

        StringJoiner sj = new StringJoiner(System.lineSeparator());
        publicRoleService.getActualPublicRolesOfPublicPerson(publicPerson).forEach(role -> {
            sj.add(role.getPresentationName());
        });
        lblAktualniRole.setValue(sj.toString().isEmpty() ? "Žádné role" : sj.toString());
        imageComponent.setImage(publicPerson.getImage());

        setReadOnly(true);
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        butEdit.setVisible(readOnly && (securityService.currentUserHasRole(UserType.ADMIN)
                || securityService.currentUserHasRole(UserType.VOLUNTEER)));
        imageComponent.setReadOnly(readOnly);
        readLayout.setVisible(readOnly);
        editLayout.setVisible(!readOnly);
    }


    private void onRemove() {
        if (removeListener != null)  {
            removeListener.remove(publicPerson);
        }
    }

    private void onCancel() {
        imageComponent.setImage(publicPerson.getImage());
        setReadOnly(true);
    }

    private void onSave() {
        if (bfg.isValid()) {
            try {
                bfg.commit();
                publicPerson.setImage(imageComponent.getImage());
                if (saveListener != null) {
                    saveListener.save(publicPerson);
                }
            } catch (FieldGroup.CommitException e) {
                throw new RuntimeException("Nepodařilo se uložit veřejnou osobu: " + publicPerson);
            }
        }
    }
}
