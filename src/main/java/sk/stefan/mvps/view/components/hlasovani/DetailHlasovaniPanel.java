package sk.stefan.mvps.view.components.hlasovani;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import sk.stefan.enums.PublicUsefulness;
import sk.stefan.enums.UserType;
import sk.stefan.enums.VoteResult;
import sk.stefan.interfaces.TabEntity;
import sk.stefan.listeners.SaveListener;
import sk.stefan.mvps.model.entity.Theme;
import sk.stefan.mvps.model.entity.Vote;
import sk.stefan.mvps.model.entity.VoteClassification;
import sk.stefan.mvps.model.service.ClassificationService;
import sk.stefan.mvps.model.service.LinkService;
import sk.stefan.mvps.model.service.PublicBodyService;
import sk.stefan.mvps.model.service.SecurityService;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.utils.Localizator;

import java.text.SimpleDateFormat;

/**
 * Created by elopin on 22.11.2015.
 */
@SpringComponent
@Scope("prototype")
@DesignRoot
public class DetailHlasovaniPanel extends CssLayout {

    @Autowired
    private VoteService voteService;

    @Autowired
    private PublicBodyService publicBodyService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private LinkService linkService;

    // Design
    private Label lblCaption;
    private Button butEdit;
    private VerticalLayout readLayout;
    private Label lblOrgan;
    private Label lblPredmet;
    private Link linkTema;
    private Label lblDatum;
    private Label lblCislo;
    private Label lblVysledek;
    private VerticalLayout editLayout;
    private ComboBox cbPredmet;
    private PopupDateField dfDatum;
    private TextField tfCislo;
    private ComboBox cbVysledek;
    private Button butSave;
    private Button butRemove;
    private Button butCancel;

    // data
    private Vote vote;
    private BeanFieldGroup<Vote> bfgVote;
    private SaveListener<? extends TabEntity> saveListener;

    private SimpleDateFormat format;

    public DetailHlasovaniPanel() {
        Design.read(this);
        Localizator.localizeDesign(this);

        bfgVote = new BeanFieldGroup<>(Vote.class);
        bfgVote.bind(cbPredmet, "subject_id");
        bfgVote.bind(dfDatum, "vote_date");
        bfgVote.bind(tfCislo, "internal_nr");
        bfgVote.bind(cbVysledek, "result_vote");

        String dateFormat = "d. MMMMM yyyy";
        format = new SimpleDateFormat(dateFormat);
        dfDatum.setDateFormat(dateFormat);

        butEdit.addClickListener(event -> setReadOnly(false));
        butCancel.addClickListener(event -> setReadOnly(true));
        butSave.addClickListener(event -> onSave());

        for (VoteResult voteResult : VoteResult.values()) {
            cbVysledek.addItem(voteResult);
            cbVysledek.setItemCaption(voteResult, voteResult.getName());
        }
    }

    public void setVote(Vote vote) {
        this.vote = vote;

        // aktualizace comboboxu s předměty
        cbPredmet.removeAllItems();
        voteService.findAllSubjectsForPublicBody(voteService.getVotePublicBody(vote))
                .forEach(subject -> {
                    cbPredmet.addItem(subject.getId());
                    cbPredmet.setItemCaption(subject.getId(), subject.getPresentationName());
                });

        lblOrgan.setValue(voteService.getVotePublicBodyName(vote));
        lblPredmet.setValue(voteService.getVoteSubjectName(vote));
        Theme theme = voteService.findThemeBySubjectId(vote.getSubject_id());
        if (theme != null) {
            linkTema.setCaption(theme.getPresentationName());
            linkTema.setResource(new ExternalResource(linkService.getUriFragmentForEntity(theme)));
        }
        lblDatum.setValue(format.format(vote.getVote_date()));
        lblCislo.setValue(vote.getInternal_nr());
        lblVysledek.setValue(vote.getResult_vote() != null ? vote.getResult_vote().getName() : "dosud nezadáno");

        bfgVote.setItemDataSource(vote);

        setReadOnly(true);
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        butEdit.setVisible(readOnly && (securityService.currentUserHasRole(UserType.ADMIN)
        || securityService.currentUserHasRole(UserType.VOLUNTEER)));
        readLayout.setVisible(readOnly);
        editLayout.setVisible(!readOnly);
    }

    public void setSaveListener(SaveListener<? extends TabEntity> saveListener) {
        this.saveListener = saveListener;
    }

    public boolean isValid() {
        return bfgVote.isValid();
    }


    private void onSave() {
        if (isValid()) {
            try {
                bfgVote.commit();
                if (saveListener != null) {
                    saveListener.accept(vote);
                }
            } catch (FieldGroup.CommitException e) {
                throw new RuntimeException("Nepodařilo se uložit detail hlasování!", e);
            }

        }
    }
}
