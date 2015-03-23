/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components.hlasovanie;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.dao.PublicBody;
import sk.stefan.MVP.model.entity.dao.PublicRole;
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.MVP.model.entity.dao.VoteOfRole;
import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.MVP.view.components.InputFormLayout;
import sk.stefan.listeners.OkCancelListener;
import sk.stefan.utils.Tools;

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
    private Vote vote;
    private List<PublicRole> prActual;

    private List<VoteOfRole> votesOfRoles;

    private Button saveBt;

    private Button cancelBt;

    private final Boolean withButtons;

    private HorizontalLayout buttonsLayout;

    private final UniRepo<VoteOfRole> vorRepo;

    private final UniRepo<Vote> voteRepo;

    private Boolean isNew;

    //0. konstruktor.
    /**
     *
     * @param pb
     * @param hlas
     * @param wb
     */
    public PritomniLayout(PublicBody pb, Vote hlas, Boolean wb) {

        //dostat to z Vote by bolo zdlhave.
        this.pubBody = pb;
        this.vote = hlas;
        this.withButtons = wb;

        this.vorRepo = new UniRepo<>(VoteOfRole.class);
        this.voteRepo = new UniRepo<>(Vote.class);

        if (vote != null && pubBody != null){
            this.refreshLayout();
        }
        
    }

    /**
     * Vytvori layout a naplni ho prislusnymi komponentami.
     */
    private void refreshLayout() {
        
        this.removeAllComponents();

        prActual = Tools.getActualPublicRoles(pubBody);
        pritomniComponents = new ArrayList<>();
        votesOfRoles = new ArrayList<>();

        PritomnyComponent prComp;

        log.info("INITLAYOUT PRITOMNI:" + prActual.size());

        VoteOfRole vor;
        List<VoteOfRole> vors = null;
        isNew = false;

        for (PublicRole pr : prActual) {
//            vors = vorRepo.findByParam("public_role_id", "" + pr.getId());
            if (vote.getId() != null) {
                vors = vorRepo.findByParam("vote_id", "" + vote.getId());
                if (vors != null && !vors.isEmpty()) {
                    vor = vors.get(0);
                } else {
                    vor = new VoteOfRole();
                    vor.setPublic_role_id(pr.getId());
                    vor.setVote_id(vote.getId());
                    vor.setVisible(Boolean.TRUE);
                }
            } //is novy:
            else {
                isNew = true;
                vor = new VoteOfRole();
                vor.setPublic_role_id(pr.getId());
                vor.setVisible(Boolean.TRUE);
            }
            this.votesOfRoles.add(vor);

//            pri ukladani najprv ulozit entitu Vote.
//            potom z nej vyextrahovat id a pridat ho do Vote of role a ulozit.
            prComp = new PritomnyComponent(vor, vote);
            this.pritomniComponents.add(prComp);
            this.addComponent(prComp);
        }

        if (withButtons) {
            this.initButtons();
            buttonsLayout.setMargin(true);
            buttonsLayout.setSpacing(true);
            this.addComponent(buttonsLayout);
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
        if (isNew) {
            //obohati hlasovanie o nove id, po ulozeni do DB.
            voteRepo.save(vote);
            isNew = false;
            for (VoteOfRole vor : votesOfRoles) {
                vor.setVote_id(vote.getId());
                vorRepo.save(vor);
            }
        } else {
            saveAllVoteOfRoles();
        }
        Notification.show("Uloženie prebehlo v poriadku!");
    }

    @Override
    public void doCancelAction() {
        //do nothing

    }

    /**
     * 
     * @param pb
     * @param vot
     */
    public void updateEntities(PublicBody pb, Vote vot){
        
        this.pubBody = pb;
        this.vote = vot;
        
        this.refreshLayout();
        
    }
    
    private void saveAllVoteOfRoles() {
        for (VoteOfRole vor : votesOfRoles) {
            vor = vorRepo.save(vor);
        }
    }

}
