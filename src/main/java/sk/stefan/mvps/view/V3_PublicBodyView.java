/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import java.util.List;
import org.apache.log4j.Logger;
import sk.stefan.enums.UserType;
import sk.stefan.factories.EditEntityButtonFactory;
import sk.stefan.factories.InputNewEntityButtonFactory;
import sk.stefan.mvps.model.entity.A_User;
import sk.stefan.mvps.model.entity.PublicBody;
import sk.stefan.mvps.model.entity.PublicRole;
import sk.stefan.mvps.model.entity.Vote;
import sk.stefan.mvps.model.service.PublicRoleService;
import sk.stefan.mvps.model.service.UserService;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.mvps.model.serviceImpl.PublicRoleServiceImpl;
import sk.stefan.mvps.model.serviceImpl.UserServiceImpl;
import sk.stefan.mvps.model.serviceImpl.VoteServiceImpl;
import sk.stefan.mvps.view.components.MyTimeline;
import sk.stefan.mvps.view.components.layouts.DownAndUploaderBriefLayout;
import sk.stefan.mvps.view.components.layouts.DownloaderBriefLayout;
import sk.stefan.mvps.view.components.layouts.PURs_briefLayout;
import sk.stefan.mvps.view.components.layouts.VOTs_briefLayout;
import sk.stefan.mvps.view.components.layouts.ViewLayout;
import sk.stefan.wrappers.FunctionalEditWrapper;

/**
 *
 * @author stefan
 */
public final class V3_PublicBodyView extends ViewLayout implements View {

    private static final long serialVersionUID = 121322L;
    private static final Logger log = Logger.getLogger(V3_PublicBodyView.class);

    private final PublicRoleService publicRoleService;
    private final VoteService voteService;
    private final UserService userService;

    //hlavna entita tohoto VIew
    private PublicBody publicBody;

    private PURs_briefLayout publicRolesLayout;
    private VOTs_briefLayout votesLayout;

    //componenty pre TimeLine:
    private MyTimeline timeline;

    //tlacitko na pridavanie novej entity:
    private Button addNewPublicRoleBt;
    private DownloaderBriefLayout<PublicBody> downoaderLayout;
    private DownAndUploaderBriefLayout<PublicBody> uploaderLayout;

    //konstruktor:
    /**
     * Konstruktor.
     */
    public V3_PublicBodyView() {

        super("Verejný Orgán");
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
        this.publicRolesLayout = new PURs_briefLayout(publicRoles, publicRoleService);

    }

    private void initVoteLayout() {

        List<Integer> votIds = voteService.findVoteIdsByPubBodyId(publicBody.getId());

        List<Vote> votes = voteService.findNewVotes(votIds);

        this.votesLayout = new VOTs_briefLayout(votes, voteService);

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

    public PURs_briefLayout getPublicRolesLayout() {
        return publicRolesLayout;
    }

    public void setPublicRolesLayout(PURs_briefLayout publicRolesLy) {
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

        this.uploaderLayout = new DownAndUploaderBriefLayout<>(PublicBody.class, this.publicBody);

        this.addComponent(uploaderLayout);

    }

    /**
     * Komponenta na zobrazovanie dokumentov prisluchajucich entite PublicBody.
     */
    private void initDownloadLayout() {

        this.downoaderLayout = new DownloaderBriefLayout<>(PublicBody.class, this.publicBody);

        this.addComponent(downoaderLayout);

    }

    private void initEditPublicBodyButton() {

        Button editPubBodyBt;
        FunctionalEditWrapper<PublicBody> ew = new FunctionalEditWrapper<>(PublicBody.class, publicBody);
        editPubBodyBt = EditEntityButtonFactory.createMyEditButton(ew);
        this.addComponent(editPubBodyBt);

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


}
