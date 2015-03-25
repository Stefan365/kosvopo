/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components.hlasovanie;

import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.dao.PublicBody;
import sk.stefan.MVP.model.entity.dao.PublicRole;
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.MVP.model.entity.dao.VoteOfRole;
import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.listeners.OkCancelListener;
import sk.stefan.utils.ToolsDao;
import sk.stefan.utils.ToolsNazvy;

/**
 *
 * @author stefan
 */
public class PritomniLayout extends VerticalLayout implements OkCancelListener {

    private static final Logger log = Logger.getLogger(PritomniLayout.class);

    private static final long serialVersionUID = 1L;

    private List<PritomnyComponent> pritomniComponents;

    //hlavne entity
    private PublicBody pubBody;
    private Integer voteId;
    private List<PublicRole> prActual;
    private List<VoteOfRole> votesOfRoles;

    //Komponnety:
    private final VerticalLayout refreshingLy;

    private final Label titleLb;

    private Button saveBt;

    private Button cancelBt;

    private final Boolean withButtons;

    private HorizontalLayout buttonsLayout;

    private final UniRepo<VoteOfRole> vorRepo;

    private final UniRepo<Vote> voteRepo;

//    private Boolean isNew;
    private final VotingLayout listener;

    //0. konstruktor.
    /**
     *
     * @param pb
     * @param vot
     * @param wb
     * @param lisnr
     */
    public PritomniLayout(PublicBody pb, Vote vot, Boolean wb, VotingLayout lisnr) {

        this.pubBody = pb;
        if (vot != null) {
            this.voteId = vot.getId();
        } else {
            this.voteId = null;
        }

        this.withButtons = wb;
        this.listener = lisnr;

        //dostat to z Vote by bolo zdlhave.
        this.titleLb = new Label(":");
        titleLb.setStyleName(ValoTheme.LABEL_BOLD);
        this.refreshingLy = new VerticalLayout();

        this.addComponents(titleLb, refreshingLy);

        this.vorRepo = new UniRepo<>(VoteOfRole.class);
        this.voteRepo = new UniRepo<>(Vote.class);

//        if (voteId != null && pubBody != null) {
        if (pubBody != null) {
            this.refreshLayout();
        }

    }

    /**
     * Vytvori layout a naplni ho prislusnymi komponentami.
     */
    private void refreshLayout() {

        refreshingLy.removeAllComponents();

        prActual = ToolsDao.getActualPublicRoles(pubBody);
        if (prActual.isEmpty()) {
            this.listener.getVoteInputFormLy().setEnabled(false);
            Notification.show("Tento verejný orgán nemá žiadnych aktuálnych poslancov!");
            return;
        } else {
            this.listener.getVoteInputFormLy().setEnabled(true);
        }

        pritomniComponents = new ArrayList<>();
        votesOfRoles = new ArrayList<>();

        PritomnyComponent prComp;

        log.info("INITLAYOUT PRITOMNI:" + prActual.size());

        VoteOfRole vor;
        List<VoteOfRole> vors;// = null;
//        isNew = false;

        for (PublicRole pr : prActual) {
//            vors = vorRepo.findByParam("public_role_id", "" + pr.getId());
            if (voteId != null) {
                vors = vorRepo.findByParam("vote_id", "" + voteId);
                if (vors != null && !vors.isEmpty()) {
                    vor = vors.get(0);
                } else {
                    vor = new VoteOfRole();
                    vor.setPublic_role_id(pr.getId());
                    vor.setVote_id(voteId);
                    vor.setVisible(Boolean.TRUE);
                }
            } //is novy:
            else {
//                isNew = true;
                vor = new VoteOfRole();
                vor.setPublic_role_id(pr.getId());
                vor.setVisible(Boolean.TRUE);
            }
            this.votesOfRoles.add(vor);

//            pri ukladani najprv ulozit entitu Vote.
//            potom z nej vyextrahovat id a pridat ho do Vote of role a ulozit.
            prComp = new PritomnyComponent(vor, this);
            this.pritomniComponents.add(prComp);
            refreshingLy.addComponent(prComp);
        }

        if (withButtons) {
            this.initButtons();
            buttonsLayout.setMargin(true);
            buttonsLayout.setSpacing(true);
            refreshingLy.addComponent(buttonsLayout);
        }

    }

//    ukladat sa to bude v ramci rodicovskej komponety, tj. nieje potreba davat tlacitka Ok/Cancel    
    private void initButtons() {

        buttonsLayout = new HorizontalLayout();

        cancelBt = new Button("zrušiť");
        saveBt = new Button("uložiť", (Button.ClickEvent event) -> {
            doOkAction();
        });

        cancelBt = new Button("zrušiť", (Button.ClickEvent event) -> {
            doCancelAction();
        });

        buttonsLayout.addComponents(saveBt, cancelBt);
    }

    @Override
    public void doOkAction() {

//        this.updateResult(); //nema zmyslel, updatuje sa priebezne, a tu je to neskoro, 
//        inak by sa opat musel zavola sqlcontainer.commit().
//        try {
//            netreba commitnut, uz je commitnute
//           SQLContainer sc = this.listener.getVoteInputFormLy().getSqlContainer();
//            sc.commit();
//            isNew = false;
//            saveAllVoteOfRoles();
        if (voteId == null) {
            voteId = listener.getVoteInputFormLy().getVoteId();
            for (VoteOfRole vor : votesOfRoles) {
                vor.setVote_id(voteId);
                vorRepo.save(vor);
            }
        } else {
            this.saveAllVoteOfRoles();
        }

//        for (VoteOfRole vor : votesOfRoles) {
//            vor.setVote_id(voteId);
//            vorRepo.save(vor);
//        }
        
        Notification.show("Uloženie prebehlo v poriadku!");
//        } catch (UnsupportedOperationException | SQLException ex) {
//            log.error(ex.getMessage(), ex);
//        }

    }

    @Override
    public void doCancelAction() {
        //do nothing

    }

    /**
     *
     * @param pb
     * @param vid
     */
    public void setEntities(PublicBody pb, Integer vid) {

        this.pubBody = pb;
        this.voteId = vid;
//        log.info("SOMTU: " + (pb==null)+":"+(vot==null));
        if (pb != null) {
            this.refreshLayout();
        }

    }

    private void saveAllVoteOfRoles() {
        for (VoteOfRole vor : votesOfRoles) {
            vor = vorRepo.save(vor);
        }
    }

    /**
     * Vypocita hodnoty hlasovania.
     */
    private List<Integer> calculateResult() {

        List<Integer> results = new ArrayList<>();
        results.add(0);
        results.add(0);
        results.add(0);
        results.add(0);

        Integer i, j;
        for (VoteOfRole vor : votesOfRoles) {
            i = (Integer) vor.getDecision().ordinal();
            j = results.get(i);
            j++;
            results.set(i, j);
        }

        return results;
    }

    /**
     *
     */
    public void updateResult() {

        List<Integer> res = this.calculateResult();
        this.listener.getVoteInputFormLy().setResults(res);
    }

}
