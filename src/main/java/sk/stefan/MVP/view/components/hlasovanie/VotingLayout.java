/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components.hlasovanie;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import sk.stefan.MVP.model.entity.dao.PublicBody;
import sk.stefan.MVP.model.entity.dao.PublicRole;
import sk.stefan.MVP.model.entity.dao.Tenure;
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.MVP.view.components.InputComboBox;
import sk.stefan.MVP.view.components.InputFormLayout;
import sk.stefan.MVP.view.components.NavigationComponent;
import sk.stefan.MVP.view.components.UniDlg;
import sk.stefan.MVP.view.components.hlasovanie.PritomniLayout;
import sk.stefan.listeners.ObnovFilterListener;
import sk.stefan.listeners.OkCancelListener;
import sk.stefan.utils.Tools;

/**
 * Layout, ktory zahrnuje vsetky komponenty tykajuce sa vyplnovania noveho hlasovania.
 *
 * @author stefan
 */
public class VotingLayout extends VerticalLayout implements OkCancelListener, ObnovFilterListener {

    private static final long serialVersionUID = 1L;

//    HLAVNE KOMPONENTY:
    private InputComboBox<Integer> pubBodycCb;
    private InputFormLayout<Vote> voteFormLy;
    private final PritomniLayout pritomniLy;

//    hlavne entity:
    private PublicBody pubBody;
    private Vote vote;

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
     * @param nav
     */
    public VotingLayout() {

        this.setSpacing(true);
        this.setMargin(true);


        this.initRepos();
        this.initComponents();

        pubBody = pbRepo.findOne(5);
        Vote hlas = voteRepo.findOne(4);
        pritomniLy = new PritomniLayout(pubBody, hlas, Boolean.FALSE);
        this.addComponent(pritomniLy);

     }

    
    @Override
    public void doOkAction() {
        //musi to byt takto, pretoze ok button je v tej druhej komponente, tj.
        //v input form layoute.
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

    /**
     * @return the vote
     */
    public Vote getVote() {
        return vote;
    }

    /**
     * @param vote the vote to set
     */
    public void setVote(Vote vote) {
        this.vote = vote;
    }

    private void initComponents() {
        
//        this.pritomniLy = new PritomniLayout(pubBody, vote, navigator, Boolean.FALSE);
        
    }

    @Override
    public void obnovFilter() {
        //do nothing
    }

    public void setWindowOkCancelListener(OkCancelListener aThis) {
        this.forWindowListener = aThis;
    }

    

}
