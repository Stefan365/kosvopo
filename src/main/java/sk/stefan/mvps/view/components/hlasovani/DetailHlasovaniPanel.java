package sk.stefan.mvps.view.components.hlasovani;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import sk.stefan.enums.PublicUsefulness;
import sk.stefan.enums.VoteResult;
import sk.stefan.mvps.model.entity.Vote;
import sk.stefan.mvps.model.entity.VoteClassification;
import sk.stefan.mvps.model.service.ClassificationService;
import sk.stefan.mvps.model.service.PublicBodyService;
import sk.stefan.mvps.model.service.VoteService;

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
    private ClassificationService classificationService;

    @Autowired
    private PublicBodyService publicBodyService;

    // Design
    private Label lblCaption;
    private Button butEdit;
    private VerticalLayout readLayout;
    private Label lblOrgan;
    private Label lblPredmet;
    private Label lblTema;
    private Label lblDatum;
    private Label lblCislo;
    private Label lblVysledek;
    private Label lblPopis;
    private Label lblHodnoceni;
    private VerticalLayout editLayout;
    private ComboBox cbPredmet;
    private PopupDateField dfDatum;
    private TextField tfCislo;
    private ComboBox cbVysledek;
    private TextArea areaPopis;
    private ComboBox cbHodnoceni;
    private Button butSave;
    private Button butRemove;
    private Button butCancel;

    // data
    private Vote vote;
    private BeanFieldGroup<Vote> bfgVote;
    private BeanFieldGroup<VoteClassification> bfgHodnoceni;

    private SimpleDateFormat format;

    public DetailHlasovaniPanel() {
        Design.read(this);

        bfgVote = new BeanFieldGroup<>(Vote.class);
        bfgVote.bind(cbPredmet, "subject_id");
        bfgVote.bind(dfDatum, "vote_date");
        bfgVote.bind(tfCislo, "internal_nr");
        bfgVote.bind(cbVysledek, "result_vote");

        bfgHodnoceni = new BeanFieldGroup<>(VoteClassification.class);
        bfgHodnoceni.bind(areaPopis, "brief_description");
        bfgHodnoceni.bind(cbHodnoceni, "public_usefulness");

        String dateFormat = "d. MMMMM yyyy";
        format = new SimpleDateFormat(dateFormat);
        dfDatum.setDateFormat(dateFormat);

        butEdit.addClickListener(event -> setReadOnly(false));
        butCancel.addClickListener(event -> setReadOnly(true));

        for (VoteResult voteResult : VoteResult.values()) {
            cbVysledek.addItem(voteResult);
            cbVysledek.setItemCaption(voteResult, voteResult.getName());
        }

        for (PublicUsefulness usefulness : PublicUsefulness.values()) {
            cbHodnoceni.addItem(usefulness);
            cbHodnoceni.setItemCaption(usefulness, usefulness.getName());
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
        lblTema.setValue(voteService.findThemeBySubjectId(vote.getSubject_id()).getPresentationName());
        lblDatum.setValue(format.format(vote.getVote_date()));
        lblCislo.setValue(vote.getInternal_nr());
        lblVysledek.setValue(vote.getResult_vote() != null ? vote.getResult_vote().getName() : "dosud nezadáno");

        VoteClassification classification = classificationService.findVoteClassByVoteId(vote.getId());
        lblPopis.setVisible(classification != null);
        lblHodnoceni.setVisible(classification != null);
        if (classification != null) {
            bfgHodnoceni.setItemDataSource(classification);
            lblPopis.setValue(classification.getBrief_description());
            lblHodnoceni.setValue(classification.getPublic_usefulness().getName());
        } else {
            bfgHodnoceni.setItemDataSource(new VoteClassification());
        }

        bfgVote.setItemDataSource(vote);

        setReadOnly(true);
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        butEdit.setVisible(readOnly);
        readLayout.setVisible(readOnly);
        editLayout.setVisible(!readOnly);
    }
}
