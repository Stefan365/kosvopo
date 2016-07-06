package sk.stefan.mvps.view.components.hlasovani.subject;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.*;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sk.stefan.enums.UserType;
import sk.stefan.mvps.model.entity.PublicBody;
import sk.stefan.mvps.model.service.PublicBodyService;
import sk.stefan.mvps.model.service.PublicRoleService;
import sk.stefan.mvps.model.service.SecurityService;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.utils.Localizator;

import java.util.function.Consumer;

import sk.stefan.mvps.model.entity.Subject;

/**
 * Panel s detailem tématu hlasování.
 *
 * @author elopin, sveres on 09.12.2015.
 */
@Component
@Scope("prototype")
@DesignRoot
public class SubjectPanel extends CssLayout {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private PublicBodyService publicBodyService;

    @Autowired
    private PublicRoleService publicRoleService;

    @Autowired
    private VoteService voteService;


//    GRAFIKA:
    private Label lblCaption;

//  policka zodpovedajuce properties:
//  non  editable:
    private Label lblNazev;
    private Label lblPopis;
    private Label lblSubmitter;
    private Label lblPubBody;

    private VerticalLayout readLayout;

//    editable:
    private TextField tfNazev;
    private TextArea areaPopis;
    private ComboBox cbSubmitter;
    private ComboBox cbPubBody;

    private Button butEdit;
    private Button butSave;
    private Button butCancel;
    private Button butRemove;

    private VerticalLayout editLayout;

//    data:
    private BeanFieldGroup<Subject> bfg;
    private Consumer<Subject> saveListener;
    private Consumer<Subject> removeListener;


    public SubjectPanel() {
        Design.read(this);
        Localizator.localizeDesign(this);

        bfg = new BeanFieldGroup<>(Subject.class);
        bfg.bind(tfNazev, "brief_description");
        bfg.bind(areaPopis, "description");
        bfg.bind(cbSubmitter, "submitter_name");
        bfg.bind(cbPubBody, "public_body_id");

        butEdit.addClickListener(event -> setReadOnly(false));
        butCancel.addClickListener(event -> onCancel());
        butSave.addClickListener(event -> onSave());
        butRemove.addClickListener(event -> onRemove());

        cbPubBody.addValueChangeListener(event -> updateRoleSelection((Integer) cbPubBody.getValue()));
    }

    public void setSaveListener(Consumer<Subject> saveListener) {
        this.saveListener = saveListener;
    }

    public void setRemoveListener(Consumer<Subject> removeListener) {
        this.removeListener = removeListener;
    }

    public void setSubject(Subject subject) {
        setValidationVisible(false);
        bfg.setItemDataSource(subject);

//        editable:
        cbPubBody.removeAllItems();
        publicBodyService.findAll().forEach(body -> {
            cbPubBody.addItem(body.getId());
            cbPubBody.setItemCaption(body.getId(), body.getPresentationName());
        });

        if (subject.getId() != null) {
            PublicBody body = publicBodyService.findOne(subject.getPublic_body_id());
            cbPubBody.setValue(body != null ? body.getId() : null);
            lblPubBody.setValue(body != null ? body.getPresentationName() : null);
            if (body != null) {
                updateRoleSelection(body.getId());
            }
        }

//        non editable:
        String submitter = subject.getSubmitter_name();
        lblSubmitter.setValue(submitter);
        lblNazev.setValue(subject.getBrief_description());
        lblPopis.setValue(subject.getDescription());
        lblPopis.setVisible(subject.getDescription() != null && !subject.getDescription().isEmpty());

        setValidationVisible(false);
        bfg.setItemDataSource(subject);

        setReadOnly(subject.getId() != null);
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        butEdit.setVisible(readOnly && (securityService.currentUserHasRole(UserType.ADMIN)
                || securityService.currentUserHasRole(UserType.VOLUNTEER)));
        readLayout.setVisible(readOnly);
        editLayout.setVisible(!readOnly);
    }

    private void setValidationVisible(boolean visible) {
        tfNazev.setValidationVisible(visible);
    }

    private void onRemove() {
        if (removeListener != null) {
            removeListener.accept(bfg.getItemDataSource().getBean());
        }
    }

    private void onSave() {
        setValidationVisible(true);
        if (bfg.isValid()) {
            try {
                bfg.commit();
                if (saveListener != null) {
                    saveListener.accept(bfg.getItemDataSource().getBean());
                }
            } catch (FieldGroup.CommitException e) {
                throw new RuntimeException("Nepodarilo sa uložiť predmet!", e);
            }
        }
    }

    private void onCancel() {
        if (bfg.getItemDataSource().getBean().getId() == null) {
            setVisible(false);
        } else {
            setReadOnly(true);
        }
    }

    private void updateRoleSelection(Integer organId) {

        publicRoleService.findAllRolesForPubBody(organId).forEach(role -> {
            cbSubmitter.addItem(role.getPresentationName2());
            cbSubmitter.setItemCaption(role.getPresentationName2(), role.getPresentationName2());
        });
    }
}
