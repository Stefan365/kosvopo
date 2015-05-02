/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components.hlasovanie;

import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.lang.reflect.Field;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import sk.stefan.DBconnection.DoDBconn;
import sk.stefan.MVP.model.entity.PublicBody;
import sk.stefan.MVP.model.entity.PublicRole;
import sk.stefan.MVP.model.entity.Tenure;
import sk.stefan.MVP.model.entity.Vote;
import sk.stefan.MVP.model.repo.UniRepo;
import sk.stefan.MVP.view.components.hlasovanie.PritomniLayout;
import sk.stefan.MVP.view.components.filtering.FilterComboBox;
import sk.stefan.MVP.view.components.filtering.FilterVoteListener;
import sk.stefan.listeners.ObnovFilterListener;
import sk.stefan.listeners.OkCancelListener;

/**
 * Layout, ktory zahrnuje vsetky komponenty tykajuce sa vyplnovania noveho
 * hlasovania.
 *
 * @author stefan
 */
public class VotingLayout extends HorizontalLayout implements OkCancelListener, ObnovFilterListener {

    private final GridLayout mainLayout = new GridLayout(3, 2);
    
    private final Label comboLb = new Label("Verejný orgán");
    private final Label voteInputLb = new Label("Hlasovanie");
    private final Label pritomniLb = new Label("Hlasovanie poslancov");

    private static final long serialVersionUID = 1L;

    private static final Logger log = Logger.getLogger(VotingLayout.class);

//    HLAVNE KOMPONENTY:
    private final FilterComboBox<PublicBody> pubBodycCb;
    private final InputVoteFormLayout<Vote> voteInputFormLy;
    private final PritomniLayout pritomniLy;

//    private final VerticalLayout comboLy;
    
    
    
    
    

//    hlavne entity:
    private PublicBody pubBody;
    private Vote vote;
    private SQLContainer sqlCont;
    private String tn;
    private final Object itemId;
    private final Item item;

//    REPA:
//    @Autowired
    private UniRepo<PublicRole> prRepo;
//    @Autowired
    private UniRepo<Tenure> tenureRepo;
//    @Autowired
    private UniRepo<PublicBody> pbRepo;
//    @Autowired
    private UniRepo<Vote> voteRepo;

    private OkCancelListener forWindowListener;

    //0. konstruktor
    /**
     * @param vot
     */
    public VotingLayout(Vote vot) {
        
        vote = vot;

        this.addComponent(mainLayout);
        
        this.initRepos();
        
        
        comboLb.setStyleName(ValoTheme.LABEL_BOLD);
        voteInputLb.setStyleName(ValoTheme.LABEL_BOLD);
        pritomniLb.setStyleName(ValoTheme.LABEL_BOLD);
        

        this.pubBodycCb = new FilterComboBox<>(PublicBody.class);

        
        this.setSpacing(true);
        this.setMargin(true);

        pritomniLy = new PritomniLayout(pubBody, vote, Boolean.FALSE, this);

//        public VoteInputFormLayout(Class<?> clsT, Item item, SQLContainer sqlCont, Component cp, List<String> nEditFn) {
        Class<Vote> cls = Vote.class;
        try {

            Field tnFld = cls.getDeclaredField("TN");
            tn = (String) tnFld.get(null);
            sqlCont = DoDBconn.createSqlContainera(tn);

        } catch (IllegalAccessException | SQLException | NoSuchFieldException | SecurityException ex) {
            log.error(ex.getMessage());
        }

        itemId = sqlCont.addItem();
        item = sqlCont.getItem(itemId);

        this.voteInputFormLy = new InputVoteFormLayout<>(cls, item, sqlCont, this, null);
        
        FilterVoteListener lisnr = new FilterVoteListener(voteInputFormLy, pritomniLy, this);
        this.pubBodycCb.addValueChangeListener(lisnr);
        
//        this.addComponents(comboLy, voteInputFormLy, pritomniLy);

        pritomniLy.setEnabled(false);
        this.voteInputFormLy.setEnabled(false);
        
        this.putAllInLayout();


    }

    @Override
    public void doOkAction() {
        //musi to byt takto, pretoze ok button je v tej druhej komponente, tj.
        //v input form layoute.
        Notification.show("VotingLayout: OkAction");
        this.pritomniLy.doOkAction();
        this.forWindowListener.doOkAction();
        Notification.show("Hlasovanie uložené");
    }

    @Override
    public void doCancelAction() {
        this.pritomniLy.doCancelAction();
        this.forWindowListener.doCancelAction();
        Notification.show("Hlasovanie zrušené");
    }

    private void initRepos() {

        pbRepo = new UniRepo<>(PublicBody.class);
        tenureRepo = new UniRepo<>(Tenure.class);
        voteRepo = new UniRepo<>(Vote.class);
        prRepo = new UniRepo<>(PublicRole.class);

    }

    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }

    @Override
    public void obnovFilter() {
        //do nothing
    }

    public void setWindowOkCancelListener(OkCancelListener aThis) {
        this.forWindowListener = aThis;
    }


    public PublicBody getPubBody() {
        return pubBody;
    }
    public void setPubBody(PublicBody pubBody) {
        this.pubBody = pubBody;
    }
    public InputVoteFormLayout<Vote> getVoteInputFormLy() {
        return voteInputFormLy;
    }

    private void putAllInLayout() {
        
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        
        mainLayout.addComponent(comboLb, 0, 0);
        mainLayout.addComponent(voteInputLb, 1, 0);
        mainLayout.addComponent(pritomniLb, 2, 0);
        
        mainLayout.addComponent(pubBodycCb, 0, 1);
        mainLayout.addComponent(voteInputFormLy, 1, 1);
        mainLayout.addComponent(pritomniLy, 2, 1);
        
        mainLayout.setComponentAlignment(comboLb, Alignment.MIDDLE_LEFT);
        mainLayout.setComponentAlignment(pubBodycCb, Alignment.TOP_LEFT);
        
        mainLayout.setComponentAlignment(voteInputLb, Alignment.MIDDLE_CENTER);
        mainLayout.setComponentAlignment(voteInputFormLy, Alignment.TOP_CENTER);
        
        mainLayout.setComponentAlignment(pritomniLb, Alignment.MIDDLE_LEFT);
        mainLayout.setComponentAlignment(pritomniLy, Alignment.TOP_LEFT);
        
        
    }

}
