/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.hlasovani;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import sk.stefan.annotations.MenuButton;
import sk.stefan.annotations.ViewTab;
import sk.stefan.enums.UserType;
import sk.stefan.interfaces.TabEntity;
import sk.stefan.mvps.model.entity.PublicBody;
import sk.stefan.mvps.model.entity.Vote;
import sk.stefan.mvps.model.service.LinkService;
import sk.stefan.mvps.model.service.SecurityService;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.mvps.view.tabs.TabComponent;

/**
 * Záložka se seznamem hlasování. Může zobrazovat všechna hlasování nebo jen ta, související s danou entitou.
 * @author stefan
 */
@MenuButton(name = "Hlasování", position = 3, icon = FontAwesome.HAND_O_UP)
@ViewTab("hlasovaniTab")
@SpringComponent
@Scope("prototype")
@DesignRoot
public class V6s_VotesView extends VerticalLayout implements TabComponent {

    private static final long serialVersionUID = 12L;

    @Autowired
    private VoteService voteService;

    @Autowired
    private LinkService linkService;

    @Autowired
    private SecurityService securityService;

    //Design
    private Label lblRelatedEntity;
    private TextField searchFd;
    private Grid grid;
    private Button addVoteBt;

    //data
    private TabEntity tabEntity;
    private BeanItemContainer<Vote> container;

    public V6s_VotesView() {
        
        Design.read(this);

        container = new BeanItemContainer<>(Vote.class);

        grid.setContainerDataSource(container);
        grid.getColumn("presentationName").setHeaderCaption("Názov hlasování");
        grid.setHeightMode(HeightMode.ROW);
        grid.addSelectionListener(event -> Page.getCurrent().open(linkService.getUriFragmentForEntity((TabEntity) grid.getSelectedRow()), null));

        addVoteBt.addClickListener(event -> Page.getCurrent().open(
                tabEntity != null ? linkService.getUriFragmentForTabWithParentEntity(NewVoteForm.class, tabEntity.getEntityName(), tabEntity.getId())
                        : linkService.getUriFragmentForTab(NewVoteForm.class), null));

    }

    @Override
    public void setEntity(TabEntity tabEntity) {
        this.tabEntity = tabEntity;
        lblRelatedEntity.setValue(tabEntity.getPresentationName());
        lblRelatedEntity.setVisible(true);

        addVoteBt.setVisible(tabEntity instanceof PublicBody && (securityService.currentUserHasRole(UserType.ADMIN) || securityService.currentUserHasRole(UserType.VOLUNTEER)));
    }

    @Override
    public TabEntity getParentEntity() {
        return tabEntity;
    }

    @Override
    public String getTabCaption() {
        String relatedCaption = tabEntity != null ? tabEntity.getPresentationName() : "vše";
        return "Hlasování - " + relatedCaption;
    }

    @Override
    public void show() {
        container.removeAllItems();
        container.addAll(tabEntity != null ? voteService.findAllVotesForTabEntity(tabEntity) : voteService.findAll());
        grid.setHeightByRows(container.size() > 7 ? 7 : container.size() == 0 ? 1 : container.size());

        if (addVoteBt.isVisible()) {
            addVoteBt.setVisible(securityService.currentUserHasRole(UserType.ADMIN)
                    || securityService.currentUserHasRole(UserType.VOLUNTEER));
        }
    }

    @Override
    public String getTabId() {
        Integer relatedId = tabEntity != null ? tabEntity.getId() : null;
        return "hlasovaniTab" + (relatedId != null ? String.valueOf(relatedId) : "");
    }

//    /**
//     *
//     * @param isVolunteer
//     */
//    private void initAllBasic(Boolean isVolunteer) {
//
//        this.removeAllComponents();
//        this.initLayout();
//        this.addComponents(votesBriefLayout);
//
//        if(isVolunteer){
//            this.initNewPublicBodyButton();
//        }
//    }
//
//    /**
//     *
//     */
//    private void initLayout(){
//
//        this.setMargin(true);
//        this.setSpacing(true);
//        this.votesBriefLayout = new VOTs_briefLayout(voteService.findAll(), voteService);
//
//    }
//    /**
//     * Inicializuje tlacitko na pridavanie novej verejnej osoby.
//     */
//    private void initNewPublicBodyButton() {
//
//        this.addVoteBt = InputNewEntityButtonFactory.createMyInputButton(Vote.class);
//        this.addComponent(addVoteBt);
//    }
//    @Override
//    public void enter(ViewChangeListener.ViewChangeEvent event) {
//
//        A_User user = VaadinSession.getCurrent().getAttribute(A_User.class);
//
//        Boolean isVolunteer = Boolean.FALSE;
//        if (user != null){
//            UserType utype = userService.getUserType(user);
//            isVolunteer = ((UserType.VOLUNTEER).equals(utype) || (UserType.ADMIN).equals(utype));
//        }
//
//        initAllBasic(isVolunteer);
//
//    }
}
