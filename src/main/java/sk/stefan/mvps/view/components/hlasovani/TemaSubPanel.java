package sk.stefan.mvps.view.components.hlasovani;

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
public class TemaSubPanel extends CssLayout {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private PublicBodyService publicBodyService;


    @Autowired
    private VoteService voteService;


    //Grafika:
    private Label lblCaption;

    //policka zodpovedajuce properties:
    //brief_description:
    private Label lblNazev;
    private TextField tfNazev;
    //description:
    private Label lblPopis;
    private TextArea areaPopis;
    //submitter_name:
    private Label lblSubmitter;
    private TextField tfSubmitter;
    private Label lblPubBody;
    private ComboBox cbPubBody;

    //layouty:
    private VerticalLayout readLayout;
    private VerticalLayout editLayout;

    //vseobecne tlacitka:
    private Button butEdit;
    private Button butSave;
    private Button butCancel;
    private Button butRemove;

    //data
    private BeanFieldGroup<Subject> bfg;
    private Consumer<Subject> saveListener;
    private Consumer<Subject> removeListener;

    public TemaSubPanel() {
        Design.read(this);
        Localizator.localizeDesign(this);

        bfg = new BeanFieldGroup<>(Subject.class);
        bfg.bind(tfNazev, "brief_description");
        bfg.bind(areaPopis, "description");
        bfg.bind(tfSubmitter, "submitter_name");
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


    public void setThemeSub(Subject themeSubject) {
        setValidationVisible(false);
        bfg.setItemDataSource(themeSubject);

        lblNazev.setValue(themeSubject.getBrief_description());
        lblPopis.setValue(themeSubject.getDescription());
        lblPopis.setVisible(themeSubject.getDescription() != null && !themeSubject.getDescription().isEmpty());

        setReadOnly(themeSubject.getId() != null);
        butCancel.setVisible(themeSubject.getId() != null);
        butRemove.setVisible(themeSubject.getId() != null);
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
        cbPubBody.removeAllItems();
        PublicBody body = publicBodyService.findOne(organId).g;
        tfSubmitter.setValue();
//      cbSubmitter.setValue(body.getPresentationName());
//      cbSubmitter.setItemCaption(body.getPresentationName(), body.getPresentationName());
    }

}
