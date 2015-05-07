/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.util.List;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.A_User;
import sk.stefan.MVP.model.entity.PublicBody;
import sk.stefan.MVP.model.entity.PublicRole;
import sk.stefan.MVP.model.entity.Subject;
import sk.stefan.MVP.model.entity.Vote;
import sk.stefan.MVP.model.service.PublicRoleService;
import sk.stefan.MVP.model.service.UserService;
import sk.stefan.MVP.model.service.VoteService;
import sk.stefan.MVP.model.serviceImpl.PublicRoleServiceImpl;
import sk.stefan.MVP.model.serviceImpl.UserServiceImpl;
import sk.stefan.MVP.model.serviceImpl.VoteServiceImpl;
import sk.stefan.MVP.view.components.MyTimeline;
import sk.stefan.MVP.view.components.NavigationComponent;
import sk.stefan.MVP.view.components.PublicRolesLayout;
import sk.stefan.MVP.view.components.VotesLayout;
import sk.stefan.MVP.view.components.documents.DownloaderLayout;
import sk.stefan.MVP.view.components.documents.UploaderLayout;
import sk.stefan.enums.UserType;
import sk.stefan.factories.EditEntityButtonFactory;
import sk.stefan.factories.InputNewEntityButtonFactory;
import sk.stefan.wrappers.FunctionalEditWrapper;

/**
 *
 * @author stefan
 */
public final class V3_PublicBodyView extends VerticalLayout implements View {

    private static final long serialVersionUID = 121322L;
    private static final Logger log = Logger.getLogger(V3_PublicBodyView.class);

    private final PublicRoleService publicRoleService;
    private final VoteService voteService;
    private final UserService userService;

    //hlavna entita tohoto VIew
    private PublicBody publicBody;

    private PublicRolesLayout publicRolesLayout;
    private VotesLayout votesLayout;

    //componenty pre TimeLine:
    private MyTimeline timeline;

    //tlacitko na pridavanie novej entity:
    private Button addNewPublicRoleBt;
    private DownloaderLayout<PublicBody> downoaderLayout;
    private UploaderLayout<PublicBody> uploaderLayout;

    //konstruktor:
    /**
     * Konstruktor.
     */
    public V3_PublicBodyView() {

        this.setMargin(true);
        this.setSpacing(true);

        this.publicRoleService = new PublicRoleServiceImpl();
        this.voteService = new VoteServiceImpl();
        this.userService = new UserServiceImpl();

    }

    /**
     */
    private void initAllBasic(Boolean isVolunteer) {

        this.removeAllComponents();

        this.initPublicRolesLayout();
        this.initVoteLayout();
        this.initTimeline();

        this.addComponents(publicRolesLayout, votesLayout, timeline);

        if (isVolunteer) {

            this.initNewPublicRoleButton();
            this.initEditPublicBodyButton();
            this.initUploadLayout();

        } else {
            this.initDownloadLayout();
        }
    }

    private void setPublicBodyValue(PublicBody pb) {
        this.publicBody = pb;
    }

    private void initPublicRolesLayout() {

        List<Integer> prIds = publicRoleService.findPublicRoleIdsByPubBodyId(publicBody.getId());
        log.info("KAROLKO1: " + prIds.size());

        List<PublicRole> publicRoles = publicRoleService.getPublicRoles(prIds);
        log.info("KAROLKO2: " + publicRoles.size());
        this.publicRolesLayout = new PublicRolesLayout(publicRoles, publicRoleService);

    }

    private void initVoteLayout() {

        List<Integer> votIds = voteService.findVoteIdsByPubBodyId(publicBody.getId());

        List<Vote> votes = voteService.findNewVotes(votIds);

        this.votesLayout = new VotesLayout(votes, voteService);

    }

    public void initTimeline() {

        List<Integer> ids = voteService.findVoteIdsByPubBodyId(this.publicBody.getId());
        timeline = new MyTimeline(ids);

    }

    public PublicBody getPublicBody() {
        return publicBody;
    }

    public void setPublicBody(PublicBody publicBody) {
        this.publicBody = publicBody;
    }

    public PublicRolesLayout getPublicRolesLayout() {
        return publicRolesLayout;
    }

    public void setPublicRolesLayout(PublicRolesLayout publicRolesLy) {
        this.publicRolesLayout = publicRolesLy;
    }

    /**
     * Prida tlacitko na pridavanie novej entity PublicBody.
     */
    private void initNewPublicRoleButton() {

        this.addNewPublicRoleBt = InputNewEntityButtonFactory.createMyInputButton(PublicRole.class);

        this.addComponent(addNewPublicRoleBt);

    }

    /**
     * Inicializuje editovatelny layout s dokumentami prisluchajucimi entite
     * PublicBody
     */
    private void initUploadLayout() {

        this.uploaderLayout = new UploaderLayout<>(PublicBody.class, this.publicBody);

        this.addComponent(uploaderLayout);

    }

    /**
     * Komponenta na zobrazovanie dokumentov prisluchajucich entite PublicBody.
     */
    private void initDownloadLayout() {

        this.downoaderLayout = new DownloaderLayout<>(PublicBody.class, this.publicBody);

        this.addComponent(downoaderLayout);

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        PublicBody pb = VaadinSession.getCurrent().getAttribute(PublicBody.class);

        A_User user = VaadinSession.getCurrent().getAttribute(A_User.class);

        Boolean isVolunteer = Boolean.FALSE;
        if (user != null) {
//            navComp.getLoginBut().setCaption("logout");            
            UserType utype = userService.getUserType(user);

            //moze byt dobrovolnik, alebo admin.
            isVolunteer = ((UserType.VOLUNTEER).equals(utype) || (UserType.ADMIN).equals(utype));
        }

        if (pb != null) {
            setPublicBodyValue(pb);
            initAllBasic(isVolunteer);
        } else {
            UI.getCurrent().getNavigator().navigateTo("V3s_PublicBodiesView");
        }

    }

    private void initEditPublicBodyButton() {

        Button editPubBodyBt;
        FunctionalEditWrapper<PublicBody> ew = new FunctionalEditWrapper<>(PublicBody.class, publicBody);
        editPubBodyBt = EditEntityButtonFactory.createMyEditButton(ew);
        this.addComponent(editPubBodyBt);

    }

}
