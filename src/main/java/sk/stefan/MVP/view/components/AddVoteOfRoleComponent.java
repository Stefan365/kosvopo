package sk.stefan.MVP.view.components;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.util.List;
import sk.stefan.MVP.model.entity.dao.PublicPerson;
import sk.stefan.MVP.model.entity.dao.PublicRole;
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.MVP.model.entity.dao.VoteOfRole;
import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.MVP.model.service.PublicRoleService;
import sk.stefan.MVP.model.service.VoteService;
import sk.stefan.enums.VoteAction;
import sk.stefan.listeners.GeneralComponentListener;
import sk.stefan.utils.PomT;
import sk.stefan.utils.PomVaadin;

public final class AddVoteOfRoleComponent extends VerticalLayout implements
        GeneralComponentListener {

    /**
     * KUHOT JA SOM sTALE DOBUA
     */
    private static final long serialVersionUID = 3659971359822685378L;

    // repositories:
    private UniRepo<VoteOfRole> vorRepo;

    // services:
    private VoteService voteService;
    private PublicRoleService publicRoleService;

    // zoznamy, na ktore su napojene vyberove komboboxy:
    private List<PublicRole> lpr;
    private List<Vote> lvot;
    private List<String> ldec;

    // entity:
    private PublicPerson pp;
    private PublicRole pr;
    private Vote vo;
    private VoteOfRole vor;
    private VoteAction dec;

    // komponenty:
    private ComboBox publicRoleCB, voteCB, decisionCB;
    private Button saveBT, editBT, doneBT;
    private HorizontalLayout buttonLY;
    private CheckBox historyCHB;

    /**
     * Konstruktor
     *
     *
     * @param pp
     */
    public AddVoteOfRoleComponent(PublicPerson pp) {
        this.pp = pp;
        vor = new VoteOfRole();

        this.initComponent();
        this.initButtonsListeners();
        this.initCombosListeners();

        this.setCombosDataSouces();
        this.initCheckBox();

        this.setMargin(true);
        this.setSpacing(true);

    }

    public void setPoslanec(PublicPerson pp) {
        this.pp = pp;
        this.setComboPublicRole(pp);
        this.setComboVote(null);
    }

    public void initComponent() {

        vorRepo = new UniRepo<>(VoteOfRole.class);
        voteService = new VoteService();
        publicRoleService = new PublicRoleService();

        publicRoleCB = new ComboBox("Verejná rola");
        voteCB = new ComboBox("Hlasovania");
        decisionCB = new ComboBox("Rozhodnutie");

        historyCHB = new CheckBox("História", false);

        PomT.doladSelectList(publicRoleCB);
        PomT.doladSelectList(voteCB);
        PomT.doladSelectList(decisionCB);

        // lista s tlacitkami:
        buttonLY = new HorizontalLayout();
        // buttonLY.setMargin(true);
        buttonLY.setSpacing(true);

        saveBT = new Button("save");
        editBT = new Button("edit");
        doneBT = new Button("done");

        editBT.setEnabled(false);

        this.setMargin(true);
        this.setSpacing(true);

        // skladanie dohromady:
        this.addComponent(publicRoleCB);
        this.addComponent(voteCB);
        this.addComponent(decisionCB);
        this.addComponent(historyCHB);

        buttonLY.addComponent(saveBT);
        buttonLY.addComponent(editBT);
        buttonLY.addComponent(doneBT);
        this.addComponent(buttonLY);

    }

    // 1.
    /**
     *
     * Initializes buttons properties.
     *
     */
    private void initButtonsListeners() {

        saveBT.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void buttonClick(ClickEvent event) {
                doSave();
            }
        });

        editBT.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 1L;
            @Override
            public void buttonClick(ClickEvent event) {
                enableSave(true);
            }
        });

        doneBT.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event) {
                if (vor == null || vor.getId() != null && editBT.isEnabled()) {
                    enableSave(true);
                    setVisible(false);
                } else {
                    final TBD_YesNoWindow window = new TBD_YesNoWindow("Upozornenie",
                            "Chcete uložiť zmeny?",
                            (GeneralComponentListener) event.getButton()
                            .getParent().getParent());
                    UI.getCurrent().addWindow(window);
                }
            }
        });
    }

    // 1.
    /**
     *
     * zaktualizuje entitu VoteOfRole.
     *
     */
    protected void sestavVor() {

        pr = (PublicRole) publicRoleCB.getValue();
        vo = (Vote) voteCB.getValue();
        dec = (VoteAction) decisionCB.getValue();

        //Notification.show("vor:" + (vor==null));
        //Notification.show("pr:" + (pr==null));
        vor = new VoteOfRole();

        vor.setPublic_role_id(pr.getId());
        vor.setVote_id(vo.getId());
        vor.setDecision(dec);
        vor.setVisible(Boolean.FALSE);

    }

    // 2.
    /**
     *
     * enable/ disable komponenty vzhladom na tlacitko save.
     *
     */
    private void enableSave(boolean enab) {

        publicRoleCB.setEnabled(enab);
        voteCB.setEnabled(enab);
        decisionCB.setEnabled(enab);
        saveBT.setEnabled(enab);

        editBT.setEnabled(!enab);

        this.decisionCB.setEnabled(enab);
        this.voteCB.setEnabled(enab);
        this.publicRoleCB.setEnabled(enab);

    }

    // 4.
    /**
     *
     * ukladanie s omaslenim.
     *
     */
    public void doSave() {

        sestavVor();
        vor = vorRepo.save(vor);
        
        enableSave(false);
        if (vor != null) {
            Notification.show("Hlasovani Verejneho cinitele Uspesne ulozeno!");
        } else {
            Notification.show("Ukládanie neprebehlo!");
        }

    }

    // 3.
    /**
     *
     * Inicializuje listenery na vsetchych combo boxoch.
     *
     */
    private void initCombosListeners() {

        publicRoleCB.addValueChangeListener(new ValueChangeListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void valueChange(ValueChangeEvent event) {

                pr = (PublicRole) event.getProperty().getValue();
                //Notification.show(pr.getPresentationName());
                setComboVote(pr);
            }
        });

        voteCB.addValueChangeListener(new ValueChangeListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void valueChange(ValueChangeEvent event) {
                vo = (Vote) event.getProperty().getValue();
            }
        });

        decisionCB.addValueChangeListener(new ValueChangeListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void valueChange(ValueChangeEvent event) {
                dec = (VoteAction) event.getProperty().getValue();
            }
        });

    }

    // 5.
    /**
     *
     * I itializes datasource of comboxoes.
     *
     */
    public void setCombosDataSouces() {

        this.setComboPublicRole(pp);
        this.setComboVote(pr);

        // 3. combo: rozhodnutia
//        ldec = NamesOfVoteDecisions.getDecisions();
        ldec = VoteAction.getNames();
        PomVaadin.initComboStr(decisionCB, ldec);

    }

    /**
     *
     */
    private void setComboPublicRole(PublicPerson pp) {
        // 1. combo: PublicRole.
        // if (pp != null) {
        if (historyCHB.getValue()) {
            lpr = publicRoleService.getAllPublicRolesOfPublicPerson(pp);
        } else {
            lpr = publicRoleService.getActualPublicRolesOfPublicPerson(pp);
        }
        PomVaadin.initCombo(publicRoleCB, lpr);
    }

    /**
     *
     */
    private void setComboVote(PublicRole pr) {
        // 2. combo: Vote.
        // if (pr != null) {
        lvot = voteService.getAllVotesForPublicRole(pr);
        //Notification.show("VELKOST:" + lvot.size());
        // }
        PomVaadin.initCombo(voteCB, lvot);
    }

    // 7.
    /**
     *
     * Initialised checkbox listener.
     *
     */
    public void initCheckBox() {

        historyCHB.addValueChangeListener(new ValueChangeListener() {

            @Override
            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());

                setComboPublicRole(pp);
                setComboVote(null);
                // Notification.show("Value changed:", valueString,
                // Type.TRAY_NOTIFICATION);
            }
        });
    }

    @Override
    public void doYesAction() {
        this.doSave();
        this.setVisible(false);

    }

    @Override
    public void doNoAction() {
        this.setVisible(false);
    }
}
