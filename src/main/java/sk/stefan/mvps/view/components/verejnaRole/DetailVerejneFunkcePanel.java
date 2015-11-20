package sk.stefan.mvps.view.components.verejnaRole;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import sk.stefan.enums.PublicRoleType;
import sk.stefan.listeners.RemoveListener;
import sk.stefan.listeners.SaveListener;
import sk.stefan.mvps.model.entity.PublicRole;
import sk.stefan.mvps.model.service.PublicPersonService;
import sk.stefan.mvps.model.service.PublicRoleService;
import sk.stefan.mvps.model.service.TenureService;

/**
 * Created by elopin on 09.11.2015.
 */
@SpringComponent
@Scope("prototype")
@DesignRoot
public class DetailVerejneFunkcePanel extends CssLayout {

    @Autowired
    private PublicRoleService publicRoleService;

    @Autowired
    private PublicPersonService publicPersonService;

    @Autowired
    private TenureService tenureService;

    // Design
    private Label lblCaption;
    private Button butEdit;
    private VerticalLayout readLayout;
    private Label lblVerejnyOrgan;
    private Label lblName;
    private Label lblObdobi;
    private Label lblVerejnaOsoba;
    private VerticalLayout editLayout;
    private ComboBox cbName;
    private ComboBox cbObdobi;
    private ComboBox cbVerejnaOsoba;
    private Button butSave;
    private Button butCancel;
    private Button butRemove;

    // data
    private PublicRole publicRole;
    private BeanFieldGroup<PublicRole> bfg;
    private SaveListener<PublicRole> saveListener;
    private RemoveListener<PublicRole> removeListener;

    public DetailVerejneFunkcePanel() {
        Design.read(this);

        butEdit.addClickListener(event -> setReadOnly(false));
        butCancel.addClickListener(event -> setReadOnly(true));
        butRemove.addClickListener(event -> onRemove());
        butSave.addClickListener(event -> onSave());

        bfg = new BeanFieldGroup<>(PublicRole.class);
        bfg.bind(cbName, "name");
        bfg.bind(cbObdobi, "tenure_id");
        bfg.bind(cbVerejnaOsoba, "public_person_id");
    }

    public void setSaveListener(SaveListener<PublicRole> saveListener) {
        this.saveListener = saveListener;
    }

    public void setRemoveListener(RemoveListener<PublicRole> removeListener) {
        this.removeListener = removeListener;
    }

    public void setPublicRole(PublicRole publicRole) {
        this.publicRole = publicRole;
        lblVerejnyOrgan.setValue(publicRoleService.getPublicBodyName(publicRole));
        lblName.setValue(publicRole.getName().getName());
        lblObdobi.setValue(publicRoleService.getTenureName(publicRole));
        lblVerejnaOsoba.setValue(publicPersonService.findOne(publicRole.getPublic_person_id()).getPresentationName());

        //update comboboxů
        cbName.removeAllItems();
        for (PublicRoleType roleName : PublicRoleType.values()) {
            cbName.addItem(roleName);
            cbName.setItemCaption(roleName, roleName.getName());
        }

        cbObdobi.removeAllItems();
        tenureService.findAllTenures().forEach(tenure -> {
            cbObdobi.addItem(tenure.getId());
            cbObdobi.setItemCaption(tenure.getId(), tenure.getPresentationName());
        });

        cbVerejnaOsoba.removeAllItems();
        publicPersonService.findAll().forEach(publicPerson -> {
            cbVerejnaOsoba.addItem(publicPerson.getId());
            cbVerejnaOsoba.setItemCaption(publicPerson.getId(), publicPerson.getPresentationName());
        });

        bfg.setItemDataSource(publicRole);

        setReadOnly(true);
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        butEdit.setVisible(readOnly);
        readLayout.setVisible(readOnly);
        editLayout.setVisible(!readOnly);
    }


    private void onRemove() {
        if (removeListener != null) {
            removeListener.remove(publicRole);
        }
    }

    private void onSave() {
        if (bfg.isValid()) {
            try {
                bfg.commit();
                if (saveListener != null) {
                    saveListener.save(publicRole);
                }
            } catch (FieldGroup.CommitException e) {
                throw new RuntimeException("Nepodařilo se uložit veřejnou roli: " + publicRole, e);
            }
        }
    }
}
