/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.verejnyOrgan;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.spring.annotation.SpringComponent;

import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import sk.stefan.annotations.ViewTab;
import sk.stefan.listeners.RemoveListener;
import sk.stefan.listeners.SaveListener;
import sk.stefan.mvps.model.entity.PublicBody;
import sk.stefan.interfaces.TabEntity;
import sk.stefan.mvps.model.service.PublicBodyService;
import sk.stefan.mvps.model.service.PublicRoleService;
import sk.stefan.mvps.model.service.UserService;
import sk.stefan.mvps.model.service.VoteService;
import sk.stefan.mvps.model.serviceImpl.PublicRoleServiceImpl;
import sk.stefan.mvps.model.serviceImpl.UserServiceImpl;
import sk.stefan.mvps.model.serviceImpl.VoteServiceImpl;
import sk.stefan.mvps.view.components.DokumentyPanel;
import sk.stefan.mvps.view.components.TimelinePanel;
import sk.stefan.mvps.view.tabs.TabComponent;

/**
 *
 * @author stefan
 */
@ViewTab("verejnyOrgan")
@SpringComponent
@Scope("prototype")
@DesignRoot
public final class V3_PublicBodyView extends VerticalLayout implements TabComponent {

    private static final Logger log = Logger.getLogger(V3_PublicBodyView.class);

    private final PublicRoleService publicRoleService;
    private final VoteService voteService;
    private final UserService userService;

    @Autowired
    private PublicBodyService publicBodyService;

    // Design
    private DetailOrganuPanel detailPanel;
    private VerejneFunkceOrganuPanel funkcePanel;
    private DokumentyPanel dokumentyPanel;
    private TimelinePanel timeline;

    // data
    private PublicBody publicBody;

    /**
     * Konstruktor.
     */
    public V3_PublicBodyView() {
        Design.read(this);

        this.publicRoleService = new PublicRoleServiceImpl();
        this.voteService = new VoteServiceImpl();
        this.userService = new UserServiceImpl();

    }

    public void setSaveListener(SaveListener<TabEntity> saveListener) {
        detailPanel.setSaveListener(l -> saveListener.save(publicBody));
    }

    public void setRemoveListener(RemoveListener<TabEntity> removeListener) {
        detailPanel.setRemoveListener(l -> removeListener.remove(publicBody));
    }

    @Override
    public String getTabCaption() {
        return publicBody.getName();
    }

    @Override
    public void show() {
        publicBody = publicBodyService.findOne(publicBody.getId());
        setEntity(publicBody);
    }

    @Override
    public String getTabId() {
        return "verejnyOrgan" + publicBody.getId();
    }

    @Override
    public void setEntity(TabEntity tabEntity) {
        this.publicBody = (PublicBody) tabEntity;
        detailPanel.setPublicBody(publicBody);
        funkcePanel.setPublicBody(publicBody);
        timeline.setRelatedEntity(publicBody);
        timeline.setVotes(voteService.getAllVotesForPublicBody(publicBody));
        dokumentyPanel.setEntity(publicBody);
    }

    @Override
    public TabEntity getEntity() { return publicBody; }


   //TODO dodělat role podle tohodle
//        if (isVolunteer) {
//
//            this.initNewPublicRoleButton();
//            this.initEditPublicBodyButton();
//            this.initUploadLayout();
//
//        } else {
//            this.initDownloadLayout();
//        }


//TODO vypadá to, že to má ukazovat poslední hlasování
//    private void initVoteLayout() {
//
//        List<Integer> votIds = voteService.findVoteIdsByPubBodyId(publicBody.getId());
//
//        List<Vote> votes = voteService.findNewVotes(votIds);
//
////        this.votesBriefLy = new VOTs_briefLayout(votes, voteService);
//
//    }

    /**
     * 
     */

//   TODO tohle pak integrovat do komponenty pro dokumenty
//
//    /**
//     * Inicializuje editovatelny layout s dokumentami prisluchajucimi entite
//     * PublicBody
//     */
//    private void initUploadLayout() {
//
////        this.uploaderLayout = new DownAndUploaderBriefLayout<>(PublicBody.class, this.publicBody);
//
////        this.addComponent(uploaderLayout);
//
//    }

//    /**
//     * Komponenta na zobrazovanie dokumentov prisluchajucich entite PublicBody.
//     */
//    private void initDownloadLayout() {
//
////        this.downoaderLayout = new DownloaderBriefLayout<>(PublicBody.class, this.publicBody);
//
////        this.addComponent(downoaderLayout);
//
//    }



//    @Override
//    public void enter(ViewChangeListener.ViewChangeEvent event) {

//        PublicBody pb = VaadinSession.getCurrent().getAttribute(PublicBody.class);
//
//        A_User user = VaadinSession.getCurrent().getAttribute(A_User.class);
//
//        Boolean isVolunteer = Boolean.FALSE;
//        if (user != null) {
////            navComp.getLoginBut().setCaption("logout");
//            UserType utype = userService.getUserType(user);
//
//            //moze byt dobrovolnik, alebo admin.
//            isVolunteer = ((UserType.VOLUNTEER).equals(utype) || (UserType.ADMIN).equals(utype));
//        }
//
//        if (pb != null) {
//            setPublicBodyValue(pb);
//            initAllBasic(isVolunteer);
//        } else {
//            UI.getCurrent().getNavigator().navigateTo("V3s_PublicBodiesView");
//        }

//    }

}
