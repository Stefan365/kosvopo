package sk.stefan.mvps.view.components.hlasovani;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sk.stefan.annotations.ViewTab;
import sk.stefan.enums.UserType;
import sk.stefan.interfaces.TabEntity;
import sk.stefan.listeners.SaveListener;
import sk.stefan.mvps.model.entity.PublicBody;
import sk.stefan.mvps.model.entity.Subject;
import sk.stefan.mvps.model.entity.Vote;
import sk.stefan.mvps.model.service.PublicBodyService;
import sk.stefan.mvps.model.service.SecurityService;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.mvps.view.tabs.TabComponent;
import sk.stefan.utils.Localizator;

/**
 * Formulář pro vytvoření nového hlasování.
 * @author elopin on 25.11.2015.
 */
@Component
@Scope("prototype")
@ViewTab("noveHlasovani")
@DesignRoot
public class NewVoteForm extends VerticalLayout implements TabComponent {

    @Autowired
    private VoteService voteService;

    @Autowired
    private PublicBodyService publicBodyService;

    @Autowired
    private SecurityService securityService;

    // Design
    private Panel panel;
    private Label lblBody;
    private ComboBox cbPublicBody;
    private ComboBox cbSubject;
    private DateField dfDatum;
    private TextField tfCislo;
    private Button butSave;

    //data
    private PublicBody publicBody;
    private BeanFieldGroup<Vote> bfg;
    private SaveListener<TabEntity> saveListener;

    public NewVoteForm() {
        Design.read(this);
        Localizator.localizeDesign(this);

        bfg = new BeanFieldGroup<>(Vote.class);
        bfg.bind(cbSubject, "subject_id");
        bfg.bind(dfDatum, "vote_date");
        bfg.bind(tfCislo, "internal_nr");

        butSave.addClickListener(event -> onSave());
        cbPublicBody.addValueChangeListener(event -> {
            PublicBody body = publicBodyService.findOne((Integer) cbPublicBody.getValue());
            updateCbSubject(body);
        });
    }

    public void setSaveListener(SaveListener<TabEntity> saveListener) {
        this.saveListener = saveListener;
    }

    @Override
    public boolean isUserAccessGranted() {
        return securityService.currentUserHasRole(UserType.ADMIN) || securityService.currentUserHasRole(UserType.VOLUNTEER);
    }

    @Override
    public void setEntity(TabEntity tabEntity) {
        this.publicBody = (PublicBody) tabEntity;
        cbPublicBody.setVisible(false);
        lblBody.setValue(publicBody.getPresentationName());
        lblBody.setVisible(true);
    }

    @Override
    public void show() {
        if (bfg.getItemDataSource() == null) {
            Vote newVote = new Vote();
            newVote.setVisible(true);
            bfg.setItemDataSource(newVote);
        }

        if (publicBody == null) {
            lblBody.setVisible(false);
            cbPublicBody.setVisible(true);
            cbPublicBody.removeAllItems();
            publicBodyService.findAll().forEach(body -> {
                cbPublicBody.addItem(body.getId());
                cbPublicBody.setItemCaption(body.getId(), body.getPresentationName());
            });
        }
        setValidationVisible(false);
    }

    @Override
    public String getTabId() {
        return "noveHlasovani";
    }

    private void onSave() {
        setValidationVisible(true);
        if (bfg.isValid()) {
            try {
                bfg.commit();
                if (saveListener != null) {
                    saveListener.save(bfg.getItemDataSource().getBean());
                }
            } catch (FieldGroup.CommitException e) {
                throw new RuntimeException("Nepodarilo sa uložiť hlasovanie!");
            }
        }
    }

    public void setValidationVisible(boolean validationVisible) {
        tfCislo.setValidationVisible(validationVisible);
        cbSubject.setValidationVisible(validationVisible);
        dfDatum.setValidationVisible(validationVisible);
    }

    private void updateCbSubject(PublicBody publicBody) {
        cbSubject.removeAllItems();
        if (publicBody != null) {
            for (Subject subject : voteService.findAllSubjectsForPublicBody(publicBody)) {
                cbSubject.addItem(subject.getId());
                cbSubject.setItemCaption(subject.getId(), subject.getPresentationName());
            }
        }
    }
}
