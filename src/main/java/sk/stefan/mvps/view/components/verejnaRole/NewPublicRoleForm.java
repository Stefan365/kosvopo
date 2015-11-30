package sk.stefan.mvps.view.components.verejnaRole;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import sk.stefan.annotations.ViewTab;
import sk.stefan.enums.PublicRoleType;
import sk.stefan.listeners.SaveListener;
import sk.stefan.mvps.model.entity.PublicBody;
import sk.stefan.mvps.model.entity.PublicPerson;
import sk.stefan.mvps.model.entity.PublicRole;
import sk.stefan.interfaces.TabEntity;
import sk.stefan.mvps.model.service.PublicBodyService;
import sk.stefan.mvps.model.service.PublicPersonService;
import sk.stefan.mvps.model.service.TenureService;
import sk.stefan.mvps.view.tabs.TabComponent;

/**
 * Created by elopin on 14.11.2015.
 */
@SpringComponent
@Scope("prototype")
@ViewTab("novaVerejnaRole")
@DesignRoot
public class NewPublicRoleForm extends VerticalLayout implements TabComponent {

    @Autowired
    private PublicPersonService publicPersonService;

    @Autowired
    private PublicBodyService publicBodyService;

    @Autowired
    private TenureService tenureService;

    // Design
    private Label lblBody;
    private Label lblPerson;
    private ComboBox cbPublicBody;
    private ComboBox cbPublicPerson;
    private ComboBox cbName;
    private ComboBox cbTenure;
    private Button butSave;

    //data
    private PublicBody publicBody;
    private PublicRole publicRole;
    private PublicPerson publicPerson;
    private BeanFieldGroup<PublicRole> bfg;
    private SaveListener<TabEntity> saveListener;

    public NewPublicRoleForm() {
        Design.read(this);
        bfg = new BeanFieldGroup<>(PublicRole.class);

        bfg.bind(cbName, "name");
        bfg.bind(cbTenure, "tenure_id");
        bfg.bind(cbPublicPerson, "public_person_id");
        bfg.bind(cbPublicBody, "public_body_id");

        butSave.addClickListener(event -> onSave());
    }

    @Override
    public void setEntity(TabEntity tabEntity) {

        publicRole = new PublicRole();
        publicRole.setVisible(true);

        if (PublicBody.class.isAssignableFrom(tabEntity.getClass())) {
            this.publicBody = (PublicBody) tabEntity;
            lblBody.setValue(publicBody.getPresentationName());
            publicRole.setPublic_body_id(publicBody.getId());
        } else if (PublicPerson.class.isAssignableFrom(tabEntity.getClass())) {
            this.publicPerson = (PublicPerson) tabEntity;
            lblPerson.setValue(publicPerson.getPresentationName());
            publicRole.setPublic_person_id(publicPerson.getId());
        }

        lblPerson.setVisible(publicPerson != null);
        cbPublicPerson.setVisible(publicPerson == null);
        lblBody.setVisible(publicBody != null);
        cbPublicBody.setVisible(publicBody == null);

        //update comboboxů
        cbName.removeAllItems();
        for (PublicRoleType roleName : PublicRoleType.values()) {
            cbName.addItem(roleName);
            cbName.setItemCaption(roleName, roleName.getName());
        }

        cbTenure.removeAllItems();
        tenureService.findAllTenures().forEach(tenure -> {
            cbTenure.addItem(tenure.getId());
            cbTenure.setItemCaption(tenure.getId(), tenure.getPresentationName());
        });

        cbPublicPerson.removeAllItems();
        publicPersonService.findAll().forEach(person -> {
            cbPublicPerson.addItem(person.getId());
            cbPublicPerson.setItemCaption(person.getId(), person.getPresentationName());
        });

        cbPublicBody.removeAllItems();
        publicBodyService.findAll().forEach(body -> {
            cbPublicBody.addItem(body.getId());
            cbPublicBody.setItemCaption(body.getId(), body.getPresentationName());
        });

        bfg.setItemDataSource(publicRole);
    }

    public void setSaveListener(SaveListener<TabEntity> saveListener) {
        this.saveListener = saveListener;
    }


    private void onSave() {
        if (bfg.isValid()) {
            try {
                bfg.commit();
                if (saveListener != null) {
                    saveListener.save(publicRole);
                }
            } catch (FieldGroup.CommitException e) {
                throw new RuntimeException("Nepodařilo se uložit novou veřejnou roli: " + publicRole, e);
            }
        }
    }

    @Override
    public String getTabCaption() {
        return "Nová verejná role";
    }

    @Override
    public void show() {

    }

    @Override
    public String getTabId() {
        return "novaVerejnaRole";
    }
}
