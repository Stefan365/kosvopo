package sk.stefan.mvps.view.components.administrace;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sk.stefan.mvps.model.entity.PublicBody;
import sk.stefan.mvps.model.entity.PublicRole;
import sk.stefan.mvps.model.entity.Subject;
import sk.stefan.mvps.model.service.PublicBodyService;
import sk.stefan.mvps.model.service.PublicRoleService;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.utils.Localizator;

import java.util.function.Consumer;

/**
 * Panel s detailem předmětu hlasování.
 * @author elopin on 09.12.2015.
 */
@Component
@Scope("prototype")
@DesignRoot
public class ZBD_SubjectPanel extends CssLayout {

    @Autowired
    private PublicBodyService publicBodyService;

    @Autowired
    private PublicRoleService publicRoleService;

    @Autowired
    private VoteService voteService;

    //Design
    private Label lblCaption;
    private Button butEdit;
    private VerticalLayout readLayout;
    private Label lblSubmitter;
    private Label lblPubBody;
    private Label lblNazev;
    private Label lblPopis;
    private VerticalLayout editLayout;
    
    private ComboBox cbSubmitter;
    private ComboBox cbPubBody;
    private TextField tfNazev;
    private TextArea areaPopis;
    private Button butSave;
    private Button butCancel;
    private Button butRemove;

    //data
    private BeanFieldGroup<Subject> bfg;
    private Consumer<Subject> saveListener;
    private Consumer<Subject> removeListener;

    public ZBD_SubjectPanel() {
        Design.read(this);
        Localizator.localizeDesign(this);

        bfg = new BeanFieldGroup<>(Subject.class);
        bfg.bind(cbSubmitter, "submitter_name");
        bfg.bind(cbPubBody, "public_body_id");
        bfg.bind(tfNazev, "brief_description");
        bfg.bind(areaPopis, "description");

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
//            Theme tema = voteService.getThemeById(subject.getPublic_body_id());
//            lblPubBody.setValue(tema != null ? tema.getPresentationName() : null);
        }

        cbPubBody.removeAllItems();
        publicBodyService.findAll().forEach(pb -> {
            cbPubBody.addItem(pb.getId());
            cbPubBody.setItemCaption(pb.getId(), pb.getPresentationName());
        });

        String submitter = subject.getSubmitter_name();
        lblSubmitter.setValue(submitter);
        lblNazev.setValue(subject.getBrief_description());
        lblPopis.setValue(subject.getDescription());
        lblPopis.setVisible(subject.getDescription() != null && !subject.getDescription().isEmpty());

        setValidationVisible(false);
        bfg.setItemDataSource(subject);

        setReadOnly(subject.getId() != null);

    }

    private void onCancel() {
        if (bfg.getItemDataSource().getBean().getId() == null) {
            setVisible(false);
        } else {
            setReadOnly(true);
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
                throw new RuntimeException("Nepodařilo se uložit predmet!", e);
            }
        }
    }

    private void onRemove() {
        if (removeListener != null) {
            removeListener.accept(bfg.getItemDataSource().getBean());
        }
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        butEdit.setVisible(readOnly);
        readLayout.setVisible(readOnly);
        editLayout.setVisible(!readOnly);
    }

    public void setValidationVisible(boolean visible) {
        cbSubmitter.setValidationVisible(visible);
        tfNazev.setValidationVisible(visible);
    }


    private void updateRoleSelection(Integer organId) {
        cbSubmitter.removeAllItems();
        publicRoleService.findAllPublicRoleByPublicBodyId(organId).forEach(role -> {
            cbSubmitter.addItem(role.getPresentationName());
            cbSubmitter.setItemCaption(role.getPresentationName(), role.getPresentationName());
        });
    }
}
